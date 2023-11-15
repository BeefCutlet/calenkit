package com.effourt.calenkit.service;

import com.effourt.calenkit.domain.Member;
import com.effourt.calenkit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;

    /**
     * 이메일로 회원가입
     * @param member 회원가입 할 회원 정보
     */
    @Transactional
    public void joinByEmail(Member member) {
        memberRepository.save(member);
    }
}
