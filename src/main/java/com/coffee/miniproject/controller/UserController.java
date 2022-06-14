package com.coffee.miniproject.controller;

import com.coffee.miniproject.common.exception.UserException;
<<<<<<< Updated upstream
import com.coffee.miniproject.dto.UserInfo;
import com.coffee.miniproject.dto.SignupDto;
=======
import com.coffee.miniproject.dto.*;
>>>>>>> Stashed changes
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    //Mock Html
<<<<<<< Updated upstream
    @GetMapping("/user/login")
    public String loginView(){
        return "login";
=======
    @PostMapping("/user/login")
    @ResponseBody
    public TokenDto loginView(@RequestBody UserDto userDto){
       return memberService.login(userDto);
>>>>>>> Stashed changes
    }
    @PostMapping("/reissue")
    @ResponseBody
    public TokenDto reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return memberService.reissue(tokenRequestDto);
    }


    //Mock Html
    @GetMapping("/user/signup")
    public String signupView(){
        return "signup";
    }


    @PostMapping("/user/signup")
    @ResponseBody
    public void signup(@RequestBody SignupDto signupDto) throws UserException {
        memberService.signup(signupDto);
    }

    @GetMapping("/user/userinfo")
    @ResponseBody
    public UserInfo Session(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserRole role = userDetails.getUser().getUserRole();
//        boolean isAdmin = (role == UserRole.ADMIN);
        return new UserInfo(userDetails.getUsername());
    }
}
