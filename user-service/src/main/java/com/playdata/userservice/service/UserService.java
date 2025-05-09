package com.playdata.userservice.service;

import com.playdata.userservice.auth.JwtTokenProvider;
import com.playdata.userservice.dto.LoginForm;
import com.playdata.userservice.dto.SignUpForm;
import com.playdata.userservice.entity.User;
import com.playdata.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void signUp(SignUpForm signUpForm) {
        Optional<User> existUser = userRepository.findByEmail(signUpForm.getEmail());

        if (existUser.isPresent()) throw new IllegalArgumentException("duplicated");

        User signUpUser = User.builder()
                .name(signUpForm.getName())
                .email(signUpForm.getEmail())
                .password(encoder.encode(signUpForm.getPassword()))
                .build();

        userRepository.save(signUpUser);
        log.info("user 정보 저장 완료");
    }

    public User login(LoginForm loginForm) {
        User user = userRepository.findByEmail(loginForm.getEmail()).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        if (!encoder.matches(loginForm.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("password does not match");

        return user;
    }
}
