package com.coffee.miniproject.service;

import com.coffee.miniproject.common.exception.UserException;
import com.coffee.miniproject.dto.SignupDto;
import com.coffee.miniproject.dto.UserDto;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.validator.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    public void checkUser(HttpServletRequest req, UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다.")
        );

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(member, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = req.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.
                SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}
