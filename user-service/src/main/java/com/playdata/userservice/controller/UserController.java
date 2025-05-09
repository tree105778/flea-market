package com.playdata.userservice.controller;

import com.playdata.userservice.dto.LoginForm;
import com.playdata.userservice.dto.SignUpForm;
import com.playdata.userservice.dto.TokenUserInfo;
import com.playdata.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpForm signUpForm) {
        log.info("signUP controller 진입");
        userService.signUp(signUpForm);

        return "OK!";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }

    @GetMapping("/test")
    public TokenUserInfo userTest(@AuthenticationPrincipal TokenUserInfo userInfo) {
        return userInfo;
    }
}
