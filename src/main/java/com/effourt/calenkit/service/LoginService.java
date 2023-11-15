package com.effourt.calenkit.service;

import com.effourt.calenkit.domain.Member;
import com.effourt.calenkit.domain.type.LoginType;
import com.effourt.calenkit.exception.MemberNotFoundException;
import com.effourt.calenkit.repository.MemberRepository;
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
}
