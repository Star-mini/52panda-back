package com.kcs3.panda.global.config.jwt;

import com.kcs3.panda.global.config.oauth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customUserDetail.getUserId();
        String email = customUserDetail.getEmail();

        String accessToken = jwtUtil.createJwt("access",userId,email,600000L);
        String refreshToken = jwtUtil.createJwt("refresh",userId,email,86400000L);

        response.setHeader("access","Bearer"+accessToken);
        response.setStatus(HttpStatus.OK.value());
        log.info("액세스토큰"+accessToken);
        try {
            response.sendRedirect("http://localhost:3000");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
