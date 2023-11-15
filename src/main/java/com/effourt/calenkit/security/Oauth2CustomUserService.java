package com.effourt.calenkit.security;

import com.effourt.calenkit.domain.Member;
import com.effourt.calenkit.dto.KakaoUserInfo;
import com.effourt.calenkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2CustomUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    /**
     * 유저 정보 요청 메서드
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Oauth2CustomUserService.loadUser");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        String email = saveOrUpdate(kakaoUserInfo);
        session.setAttribute("loginId", email);
        return oAuth2User;
    }

    private String saveOrUpdate(KakaoUserInfo kakaoUserInfo) {
        String email = kakaoUserInfo.getEmail();

        Member findMember = memberRepository.findByMemId(email);
        if (findMember == null) {
            Member member = new Member();
            member.setMemId(email);
            member.setMemName(kakaoUserInfo.getNickname());
            member.setMemImage(kakaoUserInfo.getProfileImage());
            memberRepository.save(member);
            return email;
        } else if (findMember.getMemStatus() == 0) {
            findMember.setMemStatus(1);
        }

        findMember.setMemLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        memberRepository.update(findMember);
        return email;
    }
}
