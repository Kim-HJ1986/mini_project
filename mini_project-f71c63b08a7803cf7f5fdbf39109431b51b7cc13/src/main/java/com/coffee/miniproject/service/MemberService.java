package com.coffee.miniproject.service;

import com.coffee.miniproject.common.exception.UserException;
import com.coffee.miniproject.dto.SignupDto;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.validator.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    public void signup(SignupDto signupDto) throws UserException {
        String username = signupDto.getUsername();

        SignupValidator.isValidUsername(username);
        SignupValidator.isValidPassword(signupDto.getPassword());
        SignupValidator.isValidPasswordCheck(signupDto.getPassword(), signupDto.getPasswordCheck());

        String password = passwordEncoder.encode(signupDto.getPassword());
        String nickname = signupDto.getNickname();
        UserRole userRole = UserRole.USER;
        Member member = new Member(username, nickname, password, userRole);

        memberRepository.save(member);
    }
}
