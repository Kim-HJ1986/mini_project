package com.coffee.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupDto {
    private String username;
    private String nickname;
    private String password;
    private String passwordCheck;
}
