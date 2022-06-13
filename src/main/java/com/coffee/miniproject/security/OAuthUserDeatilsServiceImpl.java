package com.coffee.miniproject.security;

import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.security.Provider.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuthUserDeatilsServiceImpl extends DefaultOAuth2UserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);


        OAuth2UserInfo oAuth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FackbookUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            System.out.println(oAuth2User.getName());
            System.out.println(oAuth2User.getAttributes());
        }
        else{
            System.out.println("구글과 페이스북, 네이버 아님!");
        }

        String provider = oAuth2UserInfo.getProvider(); //google,facebook
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = passwordEncoder.encode("카페미니");
        UserRole userRole = UserRole.USER;
        String nickname = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        Member member = memberRepository.findByUsername(username)
                .orElse(new Member(username, password, nickname, userRole, provider));
        memberRepository.save(member);

//        Authentication auth = new UsernamePasswordAuthenticationToken(member, null);
//        SecurityContextHolder.getContext().setAuthentication(auth);

        return new UserDetailsImpl(member, oAuth2User.getAttributes());
    }
}
