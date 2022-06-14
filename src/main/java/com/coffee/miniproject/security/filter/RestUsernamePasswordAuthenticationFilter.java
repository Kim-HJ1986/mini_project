package com.coffee.miniproject.security.filter;

import com.coffee.miniproject.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class RestUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)){
            try{
                UserDto authenticationDto = objectMapper.readValue(request.getReader().lines().collect(Collectors.joining()), UserDto.class);
                authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword());
            } catch (IOException e){
                e.printStackTrace();
                throw new AuthenticationServiceException("Json parsing에 실패했습니다.");
            }
        } else{
            String username = obtainUsername(request);
            String password = obtainPassword(request);
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        }
        this.setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
