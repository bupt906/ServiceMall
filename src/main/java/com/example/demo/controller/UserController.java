package com.example.demo.controller;

import com.example.demo.common.ServerResponse;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "User Management")
@RestController
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Register
     * @param user
     * @return
     */
    @PostMapping("/user/register")
    @ApiOperation(value = "Register")
    @ResponseBody
    public ServerResponse<String> register(@Valid User user) throws AuthenticationException {
        return userService.register(user);
    }

    /**
     * Login
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    @ApiOperation(value = "Login")
    @ResponseBody
    public ServerResponse<String> login(@Valid User user) throws AuthenticationException {
        return userService.login(user);

    }

//    /**
//     * Logout
//     * @param session
//     * @return
//     */
//    @PostMapping("/user/logout")
//    @ApiOperation(value = "Logout")
//    public String logout(@RequestBody HttpSession session) throws AuthenticationException{
//        session.removeAttribute("success");
//        return "Logout Success";
//    }

    /**
     * get_question
     * @param username
     * @return
     */
    @PostMapping("/user/forget_get_question")
    @ApiOperation(value = "get_question")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(@Valid String username) throws AuthenticationException{
        return userService.selectQuestion(username);

    }

    /**
     * check_answer
     * @param username
     * @return
     */
    @PostMapping("/user/forget_check_answer")
    @ApiOperation(value = "check_answer")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(@Valid String username,String question,String answer) throws AuthenticationException{
        return userService.checkAnswer(username,question,answer);
    }

    /**
     * reset_password
     * @param username
     * @return
     */
    @PostMapping("/user/forget_reset_password")
    @ApiOperation(value = "reset_password")
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(@Valid String username,String passwordNew) throws AuthenticationException{
        return userService.resetPassword(username,passwordNew);
    }

    /**
     * reset_password
     * @param username
     * @return
     */
    @PostMapping("/reset_password")
    @ApiOperation(value = "reset_password")
    @ResponseBody
    public ServerResponse<String> resetPassword(@Valid String username,String passwordNew, HttpServletRequest request) throws AuthenticationException{
        String authorization = request.getHeader("authorization");
        if(authorization == null){
            //返回登陆
            return ServerResponse.createByErrorMessage("Need to login");
        }
        try{
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtTokenUtil.validateToken(authorization, userDetails)){
                return userService.resetPassword(username, passwordNew);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("Token invalid");
        }

        return ServerResponse.createByErrorMessage("reset password failed");
    }
}
