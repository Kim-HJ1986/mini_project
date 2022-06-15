package com.coffee.miniproject.security.auth.Provider;

import com.coffee.miniproject.dto.TokenDto;
import com.coffee.miniproject.model.RefreshToken;
import com.coffee.miniproject.repository.RefreshTokenRepository;
import com.coffee.miniproject.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        // 최초 로그인이라면 회원가입 처리를 한다.

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급

        writeTokenResponse(response, tokenDto);
    }

    private ResponseEntity<TokenDto> writeTokenResponse(HttpServletResponse response, TokenDto tokenDto)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Authorization", tokenDto.getAccessToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();


        writer.println(objectMapper.writeValueAsString(tokenDto.getAccessToken()));
        writer.flush();
        return ResponseEntity.ok(tokenDto);
    }
}