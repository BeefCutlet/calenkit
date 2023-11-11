package com.effourt.calenkit.service;

import com.effourt.calenkit.client.KakaoApiClient;
import com.effourt.calenkit.client.KakaoFeignClient;
import com.effourt.calenkit.domain.Member;
import com.effourt.calenkit.domain.type.LoginType;
import com.effourt.calenkit.dto.AccessTokenRequest;
import com.effourt.calenkit.dto.AccessTokenResponse;
import com.effourt.calenkit.dto.AuthUserInfoResponse;
import com.effourt.calenkit.exception.MemberNotFoundException;
import com.effourt.calenkit.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final KakaoFeignClient kakaoFeignClient;
    private final KakaoApiClient kakaoApiClient;

    /**
     * 회원 비밀번호 UPDATE
     * @param memId 비밀번호를 갱신할 회원 아이디
     * @param password 갱신할 비밀번호
     */
    @Transactional
    public void updatePassword(String memId, String password) {
        if (memberRepository.findByMemId(memId) == null) {
            throw new MemberNotFoundException(memId);
        }
        Member member = new Member();
        member.setMemPw(password);
        memberRepository.updatePassword(member);
    }

    /**
     * 회원 정보 UPDATE
     * @param member 갱신할 회원 정보
     */
    @Transactional
    public void update(Member member) {
        if (memberRepository.findByMemId(member.getMemId()) == null) {
            throw new MemberNotFoundException(member.getMemId());
        }
        memberRepository.update(member);
    }

    /**
     * 최근 로그인 시각 갱신
     * @param memId 회원 아이디(이메일 주소)
     */
    @Transactional
    public void updateLastLogin(String memId) {
        if (memberRepository.findByMemId(memId) == null) {
            throw new MemberNotFoundException(memId);
        }
        Member member = new Member();
        String lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        member.setMemId(memId);
        member.setMemLogin(lastLogin);
        log.info("ID={}, LastLogin={}", member.getMemId(), member.getMemLogin());
        memberRepository.update(member);
    }

    /**
     * Member 테이블에서 이메일에 해당하는 회원 정보 조회
     * @param id 회원 아이디(이메일 주소)
     * @return 아이디에 해당하는 회원 정보
     */
    @Transactional
    public Member getMemberById(String id) {
        return memberRepository.findByMemId(id);
    }

    /**
     * [이메일 로그인] 아이디/비밀번호 존재 여부 확인
     * @param memberId
     * @return 로그인 타입
     * PASSWORD_LOGIN : 비밀번호로 로그인, CODE_LOGIN : 로그인 코드로 로그인, JOIN_LOGIN : 회원가입 코드로 회원가입 후 로그인
     */
    @Transactional
    public String checkLoginType(String memberId) {
        Member member = memberRepository.findByMemId(memberId);
        String loginType = "";
        if (member == null || member.getMemStatus() == 0) {
            //아이디 존재 X, 비밀번호 존재 X
            //회원가입 코드 생성 및 메일 전송
            loginType = LoginType.JOIN_LOGIN.toString();
        } else if (member != null && member.getMemStatus() != 0) {
            if (member.getMemPw() != null) {
                //아이디 존재 O, 비밀번호 존재 O
                loginType = LoginType.PASSWORD_LOGIN.toString();
            } else if (member.getMemPw() == null) {
                //아이디 존재 O, 비밀번호 존재 X
                //로그인 코드 생성 및 메일 전송
                loginType = LoginType.CODE_LOGIN.toString();
            }
        }

        return loginType;
    }

    /**
     * 인가 코드로 Access 토큰 발급
     * @param accessTokenRequest 액세스 토큰 요청을 위한 정보를 담은 객체
     * @return 액세스 토큰 정보를 담은 AccessTokenResponse 객체
     */
    public AccessTokenResponse getAccessToken(AccessTokenRequest accessTokenRequest) {
        AccessTokenResponse accessToken = kakaoFeignClient.getAccessToken(
                accessTokenRequest.getClientId(),
                "authorization_code",
                accessTokenRequest.getRedirectUri(),
                accessTokenRequest.getCode());
        log.info("accessToken={}", accessToken.getAccessToken());
        log.info("refreshToken={}", accessToken.getRefreshToken());
        return accessToken;
    }


    /**
     * 인가 코드로 Access 토큰을 받아온 뒤, Access 토큰으로 카카오 리소스 서버에서 유저 정보 가져오기
     * @param accessToken 사용자 정보 조회를 위한 액세스 토큰
     * @return 사용자 정보를 담은 AuthUserInfoResponse 객체
     */
    public AuthUserInfoResponse getAuthUserInfo(String accessToken) {
        String propertyKeys = "[\"id\",\"kakao_account.email\",\"kakao_account.profile.nickname\",\"kakao_account.profile.profile_image_url\"]";
        String userInfoString = kakaoApiClient.getAuthUserInfo("Bearer " + accessToken, propertyKeys);
        AuthUserInfoResponse userInfo = new AuthUserInfoResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(userInfoString);
            userInfo.setId(jsonNode.get("id").asLong());
            userInfo.setEmail(jsonNode.get("kakao_account").get("email").asText());
            userInfo.setNickname(jsonNode.get("kakao_account").get("profile").get("nickname").asText());
            userInfo.setProfileImage(jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("userId={}, userEmail={}, userNickname={}, userProfileImage={}"
                , userInfo.getId(), userInfo.getEmail(), userInfo.getNickname(), userInfo.getProfileImage());
        return userInfo;
    }
}
