package com.playdata.userservice.auth;

import com.playdata.userservice.dto.TokenUserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("filter 동작");
        String token = parseBearerToken(request);
        if (token != null) {
            TokenUserInfo userInfo = validateJwt(token);
            log.info("token 검증 성공: {}", userInfo);
            if (userInfo != null) {
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userInfo,
                        "",
                        null
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    private TokenUserInfo validateJwt(String token) {
        try {
            return jwtTokenProvider.validateAndParse(token);
        } catch (Exception e) {
            return null;
        }
    }
}
