package com.example.demo.service.impl;

import com.example.demo.common.ServerResponse;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.util.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ServerResponse<String> register(User user){
        final String username = user.getUserName();
        if( userRepository.findUserByUserName(username)!=null ) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassWord();
        user.setPassWord(encoder.encode(rawPassword));
        if(userRepository.save(user) == null){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> login(User user){
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassWord());

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);
        if(token == null){
            return ServerResponse.createByErrorMessage("登录失败");
        }
        return ServerResponse.createBySuccessMessage("登录成功");
    }

    public ServerResponse selectQuestion(String username){
//        String username = user.getUserName();
        User u = userRepository.findQuestionByUserName(username);
        if(u.getQuestion() != null){
            return ServerResponse.createBySuccess(u.getQuestion());
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer){
//        String username = user.getUserName();
//        String question = user.getQuestion();
//        String answer = user.getAnswer();
        User u = userRepository.findByUserNameAndQuestionAndAnswer(username, question, answer);
        if(u != null){
            return ServerResponse.createBySuccessMessage("Success");
        }
        return ServerResponse.createByErrorMessage("Wrong answer");
    }

    public ServerResponse<String> resetPassword(String username, String passwordNew){
//        String username = user.getUserName();
//        String passwordNew = user.getPassWord();
        if( userRepository.findUserByUserName(username) != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            int result = userRepository.updatePassWordByUserName(encoder.encode(passwordNew), username);
            if(result > 0){
                return ServerResponse.createBySuccessMessage("password has been updated");
            }
        }else {
            return ServerResponse.createByErrorMessage("username error");
        }
        return ServerResponse.createByErrorMessage("Failed to change password");
    }

}
