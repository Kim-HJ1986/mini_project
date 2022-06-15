package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.*;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
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
    public UserInfo Session(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
        );

//        boolean isAdmin = (role == UserRole.ADMIN);
        return new UserInfo(member.getUsername(), member.getNickname());
    }
}
