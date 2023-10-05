package com.example.api.service;

import com.example.api.model.Result;
import com.example.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.api.repository.UserRepository;

import java.util.Optional;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

/**
 * 注册
 * @param user 参数封装
 * @return Result
 */
    public Result register(User user) {
        Result<User> result = new Result<>();
        Optional<User> getUser = userRepository.findByEmail(user.getEmail());
        if (getUser.isPresent()) {
            result.setResultFailed("该用户名已存在！");
            return result;
        }
        user.setPassword(user.getPassword()); // TODO: Consider encrypting the password
        userRepository.save(user);
        result.setResultSuccess("注册用户成功！", user);
        return result;

    }

    public Result login(String emailOrPhone, String password) {
        Result<String> result = new Result<>();
        User user = userRepository.findByEmail(emailOrPhone)
                .orElse((User) userRepository.findByPhone(emailOrPhone).orElse(null));
        if (user != null && user.getPassword().equals(password)) { // 实际应用中应使用加密密码
            result.setResultSuccess("login successful", user.getToken());

        }else{
            result.setResultFailed("用户名和密码不匹配");
        };
        return result;
    }

    public void changePassword(Integer uid, String token, String newPassword) {
        User user = userRepository.findById(uid).orElse(null);
        if (user != null && user.getToken().equals(token)) {
            user.setPassword(newPassword); // 实际应用中应使用加密密码
            userRepository.save(user);
        }
    }
}
