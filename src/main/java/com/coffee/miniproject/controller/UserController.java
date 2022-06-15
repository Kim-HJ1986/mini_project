package com.coffee.miniproject.controller;

import com.coffee.miniproject.common.exception.UserException;
import com.coffee.miniproject.dto.*;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    //Mock Html
//    @PostMapping("/user/login")
//    @ResponseBody
//    public void loginView(HttpServletRequest req, @RequestBody UserDto userDto){
//        System.out.println("req = " + req.toString());
//        System.out.println("req.getSession() = " + req.getSession().toString());
//        System.out.println("로그인 시도가 들어왔습니다." + userDto.getUsername() + Arrays.toString(req.getCookies()));
//        memberService.checkUser(req, userDto);
//    }

    //Mock Html
//    @GetMapping("/user/signup")
//    public String signupView(){
//        return "signup";
//    }


    @PostMapping("/user/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.signup(memberRequestDto));
    }

    @PostMapping("/user/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.login(memberRequestDto));
    }

    @PostMapping("/user/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }




    @GetMapping("/user/userinfo")
    @ResponseBody
    public UserInfo Session(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserRole role = userDetails.getUser().getUserRole();
//        boolean isAdmin = (role == UserRole.ADMIN);
        return new UserInfo(userDetails.getUsername());
    }
}
