package com.kcs3.panda.global.config.jwt;

import com.kcs3.panda.domain.user.dto.UserDTO;
import com.kcs3.panda.global.config.oauth.CustomOAuth2User;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Log4j2
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader("access");

        log.info("액세스토큰확인"+accessToken);
        if(accessToken == null){
            response.sendRedirect("http://localhost:3000/login");
            return;
        }

        String originToken = accessToken.substring(6);

        log.info("오리진토큰확인"+originToken);
        if(jwtUtil.isExpired(originToken)){
            throw new CommonException(ErrorCode.EXPIRED_TOKEN_ERROR);
        }

        Long userId = jwtUtil.getUserId(originToken);
        String email = jwtUtil.getEmail(originToken);

        log.info("정보확인:"+userId+" 이메일:"+email);
        CustomOAuth2User customOAuth2User =new CustomOAuth2User(UserDTO.builder()
                .userId(userId)
                .email(email)
                .build());

        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);

    }
}
