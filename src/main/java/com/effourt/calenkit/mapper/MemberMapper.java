package com.effourt.calenkit.mapper;

import com.effourt.calenkit.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    Integer save(Member member);
    Integer updatePassword(Member member);
    Integer update(Member member);
    Integer delete(String memId);
    Member findByMemId(String memId);
    List<Member> findAllByMemId(String keyword);
}
