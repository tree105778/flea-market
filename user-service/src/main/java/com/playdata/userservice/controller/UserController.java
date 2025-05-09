package com.playdata.userservice.controller;

import com.playdata.userservice.auth.JwtTokenProvider;
import com.playdata.userservice.common.dto.CommonResDto;
import com.playdata.userservice.dto.LoginForm;
import com.playdata.userservice.dto.SignUpForm;
import com.playdata.userservice.dto.TokenUserInfo;
import com.playdata.userservice.entity.User;
import com.playdata.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/signup")
    public ResponseEntity<CommonResDto> signUp(@RequestBody SignUpForm signUpForm) {
        log.info("signUP controller 진입");
        userService.signUp(signUpForm);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResDto.<String>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .statusMessage("회원가입 성공")
                        .result(signUpForm.getName())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        User user = userService.login(loginForm);

        String token
                = jwtTokenProvider.createToken(user.getEmail(), user.getName());

        String refreshToken
                = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getName());

        redisTemplate.opsForValue().set("user:refresh:" + user.getId(), refreshToken, 2, TimeUnit.MINUTES);

        return ResponseEntity.ok(
                CommonResDto.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusMessage("로그인 완료")
                        .result(token) // JWT 토큰 반환
                        .build()
        );
    }
    
}
