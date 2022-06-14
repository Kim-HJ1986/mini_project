package com.coffee.miniproject.service;

import com.coffee.miniproject.common.exception.UserException;
import com.coffee.miniproject.dto.SignupDto;
<<<<<<< Updated upstream
=======
import com.coffee.miniproject.dto.TokenDto;
import com.coffee.miniproject.dto.TokenRequestDto;
import com.coffee.miniproject.dto.UserDto;
>>>>>>> Stashed changes
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.RefreshToken;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.RefreshTokenRepository;
import com.coffee.miniproject.security.jwt.TokenProvider;
import com.coffee.miniproject.validator.SignupValidator;
import lombok.RequiredArgsConstructor;
<<<<<<< Updated upstream
=======
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
>>>>>>> Stashed changes
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
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
<<<<<<< Updated upstream
=======

    public TokenDto login(UserDto userDto){
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
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
        System.out.println("session = " + session);
        session.setAttribute(HttpSessionSecurityContextRepository.
                SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
>>>>>>> Stashed changes
}
