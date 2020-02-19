package com.example.demo.service;

import com.example.demo.common.ServerResponse;
import com.example.demo.domain.User;

public interface UserService {
    /**
     * Register
     * @param user
     */
    ServerResponse<String> register(User user);

    /**
     * Login
     * @param user
     */
    ServerResponse<String> login(User user);

    /**
     * selectQuestion
     * @param username
     */
    ServerResponse selectQuestion(String username);

    /**
     * checkAnswer
     * @param username
     */
    ServerResponse<String> checkAnswer(String username,String question,String answer);

    /**
     * resetPassword
     * @param username
     */
    ServerResponse<String> resetPassword(String username, String password);

}
