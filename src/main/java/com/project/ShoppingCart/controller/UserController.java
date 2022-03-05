package com.project.ShoppingCart.controller;

import com.project.ShoppingCart.dto.ResponseDto;
import com.project.ShoppingCart.dto.SigninDto;
import com.project.ShoppingCart.dto.SigninResponseDto;
import com.project.ShoppingCart.dto.SignupDto;
import com.project.ShoppingCart.exceptions.AuthenticationFailException;
import com.project.ShoppingCart.exceptions.CustomException;
import com.project.ShoppingCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signup(signupDto);
    }

    @PostMapping("/signin")
    public SigninResponseDto signin(@RequestBody SigninDto signinDto) throws AuthenticationFailException {
        return userService.signin(signinDto);
    }
}
