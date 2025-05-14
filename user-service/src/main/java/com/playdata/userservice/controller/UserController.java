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

import java.util.HashMap;
import java.util.Map;
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

        redisTemplate.opsForValue().set("user:refresh:" + user.getId(), refreshToken, 2, TimeUnit.DAYS);

        Map<String, Object> loginInfo = new HashMap<>();

        loginInfo.put("id", user.getId());
        loginInfo.put("email", user.getEmail());
        loginInfo.put("name", user.getName());
        loginInfo.put("token", token);
        loginInfo.put("refreshToken", refreshToken);

        return ResponseEntity.ok(
                new CommonResDto<Map<String, Object>>(HttpStatus.OK.value(), "로그인 완료", loginInfo)
        );
    }

    @GetMapping("/test")
    public TokenUserInfo userTest(@AuthenticationPrincipal TokenUserInfo userInfo) {
        return userInfo;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String email = map.get("email");
        String name = map.get("name");
        log.info("userId: {}, email: {}, name: {}", id, email, name);
        String refreshToken = map.get("refreshToken");
        log.info("refreshToken: {}", refreshToken);

        String obj = redisTemplate.opsForValue().get("user:refresh:" + id);
        log.info("obj: {}", obj);
        if (obj == null || !obj.equals(refreshToken)) {
            return new ResponseEntity<>(new CommonResDto(
                    HttpStatus.UNAUTHORIZED.value(), "EXPIRED_AT", HttpStatus.UNAUTHORIZED),
                    HttpStatus.UNAUTHORIZED
            );
        }

        map.put("token", map.get("token"));

        String accessToken = jwtTokenProvider.createToken(name, email);
        map.replace("token", accessToken);

        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "새 토큰 발급됨", map));
    }

}
