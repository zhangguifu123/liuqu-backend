package com.omate.liuqu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class NewController {
    @RequestMapping("/news")
    public ResponseEntity<Map<String, Object>> getNews() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(
                "https://newsapi.org/v2/everything?q=Autism&apiKey=d6c9e58302c0407fb10db0474ca69c93",
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(response, Map.class);

        return new ResponseEntity<>(jsonMap, HttpStatus.OK);
    }
}
