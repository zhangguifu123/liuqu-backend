package com.omate.liuqu.service;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omate.liuqu.model.Activity;
import com.omate.liuqu.model.Order;
import com.omate.liuqu.repository.ActivityRepository;
import com.omate.liuqu.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final String API_URL = "https://www.omipay.com.cn/omipay/api/v2/MakeAPPOrder";
    private static final String SECRET_KEY = "b94dbe95833240198227afca0f13135d";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TicketService ticketService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    private JsonNode jsonNode;

    // ... 其他方法 ...
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public boolean updateOrderStatus(String orderId, Integer newStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
            return true;
        }
        return false;
    }


    private String generateNonceStr() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10 + new Random().nextInt(23);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(new Random().nextInt(candidateChars.length())));
        }
        return sb.toString();
    }

    private String generateSignature(Map<String, String> params) {
        // 将参数按照 key 的字典顺序排序
        String toSign = params.get("m_number") + "&" +
                params.get("timestamp") + "&" +
                params.get("nonce_str") + "&" +
                SECRET_KEY; // 直接使用传入的 secretKey

        // 进行 MD5 加密并转换为大写
        return md5(toSign).toUpperCase();
    }
    private String md5(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(source.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密出错：" + e.toString());
        }
    }

    public Order createOrder(Order order) {
        boolean updateResult = ticketService.updateResidualNum(order.getTicketId(), order.getQuantity());

        if(updateResult) {
            Order createResult = createThirdOrder(order);
            return createResult;
        } else {
            return null; // 余票不足
        }
    }

    public Order createThirdOrder(Order order){
        String generatedId = generateNonceStr();
        order.setOrderId(generatedId);
        Long activityId = order.getActivityId();
        // 查询 Activity 实体
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        order.setActivity(activity);
        // 获取 Activity 名称
        String activityName = activity.getActivityName();

        LocalDateTime now = LocalDateTime.now();
        String startTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String orderName = activityName + " " + startTime; // 组合名称
        BigDecimal finalAmount = order.getFinalAmount(); // 总金额
        String orderId = order.getOrderId(); // 自增ID

        RestTemplate restTemplate = new RestTemplate();
        // JSON 请求体的 Map
        // 查询字符串参数的 Map
        Map<String, String> queryStringParams = new HashMap<>();
        queryStringParams.put("m_number", "20002946064");
        queryStringParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        queryStringParams.put("nonce_str", generateNonceStr());
        queryStringParams.put("order_name", orderName); // 组合后的名称
        queryStringParams.put("currency", "AUD"); // 货币类型
        queryStringParams.put("amount", String.valueOf(finalAmount.intValue())); // Order 实体中的 totalAmount
        queryStringParams.put("notify_url", "www.omatesydney.com"); // 通知地址
        queryStringParams.put("out_order_no", orderId); // Order 实体的自增 ID
        queryStringParams.put("platform", "ALIPAYONLINE"); // 支付平台
//        queryStringParams.put("o_number", String.valueOf(order.getPartnerId())); // 商家id
        queryStringParams.put("o_number", "001"); // 商家id
        queryStringParams.put("app_id", "wx571a1u199z0qc240"); // 商户的AppId
        // 添加签名，假设 generateSignature 已经考虑了所有参数
        queryStringParams.put("sign", generateSignature(queryStringParams));

        // 使用 ObjectMapper 将 JSON 请求体的 Map 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 构建 QueryString
        String queryString = queryStringParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        // 将查询字符串参数附加到 URL
        String urlWithQueryString = API_URL + "?" + queryString;

        // 发送 GET 请求
        ResponseEntity<String> response = restTemplate.getForEntity(urlWithQueryString, String.class);


        // 处理响应

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper resultObjectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = resultObjectMapper.readTree(response.getBody());
                String returnCode = jsonNode.get("return_code").asText();

                if ("SUCCESS".equals(returnCode)) {
                    // 更新订单信息
                    String orderNo = jsonNode.get("order_no").asText();
                    String orderString = jsonNode.get("order_string").asText();
                    order.setOrderOmipayNumber(orderNo);
                    order.setOrderPayUrl(orderString);
                    orderRepository.save(order);
                    return order;
                } else {
                    // 回滚票档余票数量
                    String errorCode = jsonNode.get("error_code").asText();
                    String errorMsg = jsonNode.get("error_msg").asText();
                    ticketService.rollbackResidualNum(order.getTicketId(), order.getQuantity());
                    throw new ExternalApiException(errorCode +"订单创建失败: " + errorMsg);
                }
            } catch (Exception e) {
                // JSON解析错误
                String errorCode = jsonNode.get("error_code").asText();
                String errorMsg = jsonNode.get("error_msg").asText();
                ticketService.rollbackResidualNum(order.getTicketId(), order.getQuantity());
                throw new ExternalApiException(errorCode + "订单创建失败: "+ errorMsg);
            }
        } else {
            // HTTP错误响应
            String errorCode = jsonNode.get("error_code").asText();
            String errorMsg = jsonNode.get("error_msg").asText();
            ticketService.rollbackResidualNum(order.getTicketId(), order.getQuantity());
            throw new ExternalApiException(errorCode + "订单创建失败: " + errorMsg);
        }

        // ...
    }

    public class InsufficientTicketsException extends RuntimeException {
        public InsufficientTicketsException(String message) {
            super(message);
        }
    }

    public class ExternalApiException extends RuntimeException {
        public ExternalApiException(String message) {
            super(message);
        }
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        // Update the order entity here
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    // 你需要定义ApiSuccessResponse和ApiErrorResponse类来匹配API的响应格式
    // 例如:
    public static class ApiSuccessResponse {
        private String return_code;
        private String order_string;
        private String order_no;
        // getter和setter

        public String getReturnCode() {
            return return_code;
        }

        public void setReturnCode(String returnCode) {
            this.return_code = returnCode;
        }

        public String getOrderString() {
            return order_string;
        }

        public void setOrderString(String orderString) {
            this.order_string = orderString;
        }

        public String getOrderNo() {
            return order_no;
        }

        public void setOrderNo(String orderNo) {
            this.order_no = orderNo;
        }

        public ApiSuccessResponse(String return_code, String order_string, String order_no) {
            this.return_code = return_code;
            this.order_string = order_string;
            this.order_no = order_no;
        }
    }

    public static class ApiErrorResponse {
        private String return_code;
        private String error_code;
        private String error_msg;
        // getter和setter

        public String getReturnCode() {
            return return_code;
        }

        public void setReturnCode(String returnCode) {
            this.return_code = returnCode;
        }

        public String getErrorCode() {
            return error_code;
        }

        public void setErrorCode(String errorCode) {
            this.error_code = errorCode;
        }

        public String getErrorMsg() {
            return error_msg;
        }

        public void setErrorMsg(String errorMsg) {
            this.error_msg = errorMsg;
        }

        public ApiErrorResponse(String return_code, String error_code, String error_msg) {
            this.return_code = return_code;
            this.error_code = error_code;
            this.error_msg = error_msg;
        }
    }
}
