package com.omate.liuqu.service;

import com.omate.liuqu.dto.UserDTO;
import com.omate.liuqu.model.Result;
import com.omate.liuqu.model.User;
import com.omate.liuqu.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * *
     *
     * @param user
     * @return userDTO
     */
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(user.getUid());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setGender(user.getGender());
        userDTO.setPersonalDescription(user.getPersonalDescription());
        return userDTO;
    }

    /**
     * register
     *
     * @param user parameter encapsulation
     * @return Result
     */
    public Result register(User user) {
        Result result = new Result();
        // Define a regex pattern for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(user.getEmail());
        // Check if email format is valid
        if (!matcher.matches()) {
            result.setResultFailed(9);
            return result;
        }
        Optional<User> getUser = userRepository.findByEmail(user.getEmail());
        if (getUser.isPresent()) {
            result.setResultFailed(2);
            return result;
        }
        // encode password by Bcrypt
        String plainPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        UserDTO userDTO = convertToDto(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",userDTO);
        result.setResultSuccess(0, resultMap);
        return result;

    }

    public Result login(String emailOrPhone, String plainPassword) {

        Result result = new Result();
        // Encode login password
        String hashedPassword = passwordEncoder.encode(plainPassword);
        // validate password
        User user = userRepository.findByEmail(emailOrPhone)
                .orElse((User) userRepository.findByPhone(emailOrPhone).orElse(null));
        if (user != null && passwordEncoder.matches(plainPassword, user.getPassword())) { // Encrypted passwords should
                                                                                          // be used in practical
                                                                                          // applications
            UserDTO userDTO = convertToDto(user);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user",userDTO);
            result.setResultSuccess(0, resultMap);

        } else {
            result.setResultFailed(1);
        }

        return result;
    }



    public UserDTO findUserById(Integer uid) {
        User user = userRepository.findById(uid).orElse(null);
        if (user != null) {

            UserDTO userDTO = convertToDto(user);

            return userDTO;
        }else{
            return null;
        }

    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;

    }
    public User updateUser(Integer id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getPersonalDescription() != null && !updatedUser.getPersonalDescription().isEmpty()) {
                        user.setPersonalDescription(updatedUser.getPersonalDescription());
                    }

                    if (updatedUser.getHobby() != null && !updatedUser.getHobby().isEmpty()) {
                        user.setHobby(updatedUser.getHobby());
                    }

                    if (updatedUser.getGender() != null && !updatedUser.getGender().isEmpty()) {
                        user.setGender(updatedUser.getGender());
                    }

                    if (updatedUser.getPhone() != null && !updatedUser.getPhone().isEmpty()) {
                        user.setPhone(updatedUser.getPhone());
                    }

                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                        user.setUsername(updatedUser.getUsername());
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

}
