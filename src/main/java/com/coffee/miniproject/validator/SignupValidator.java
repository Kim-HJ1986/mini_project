package com.coffee.miniproject.validator;

import com.coffee.miniproject.common.Constants;
import com.coffee.miniproject.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SignupValidator {
    public static void isValidUsername(String username) throws UserException{
        String regexUsername = "^[a-zA-Z0-9]{3,15}$";
        if(!Pattern.matches(regexUsername, username)){
            throw new UserException(Constants.ExceptionClass.SIGNUP_USERNAME, HttpStatus.BAD_REQUEST, "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.");
        }
    }

    public static void isValidPassword(String password) throws UserException{
        //'숫자', '문자' 무조건 1개 이상, '최소 6자에서 최대 20자' 허용
        String regexPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{6,20}$";
        if(!Pattern.matches(regexPassword, password)){
            throw new UserException(Constants.ExceptionClass.SIGNUP_PASSWORD, HttpStatus.BAD_REQUEST, "비밀번호는 숫자와 문자를 1개 이상 포함해야하며 최소 6자에서 최대 20자까지 허용합니다.");
        }
    }

    public static void isValidPasswordCheck(String password, String passwordCheck) throws UserException {
        if(!password.equals(passwordCheck)){
            throw new UserException(Constants.ExceptionClass.SIGNUP_PASSWORDCHECK, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다");
        }
    }
}
