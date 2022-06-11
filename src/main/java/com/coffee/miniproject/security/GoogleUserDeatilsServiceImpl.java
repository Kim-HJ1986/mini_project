package com.coffee.miniproject.security;

import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.UserRole;
import com.coffee.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GoogleUserDeatilsServiceImpl extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password = "카페미니";
        String email = oAuth2User.getAttribute("email");
        UserRole userRole = UserRole.USER;
        String nickname = oAuth2User.getAttribute("name");

        Member member = memberRepository.findByUsername(username)
                .orElse(new Member(username, password, nickname, userRole, provider));
        memberRepository.save(member);

        return new UserDetailsImpl(member, oAuth2User.getAttributes());
    }
}
