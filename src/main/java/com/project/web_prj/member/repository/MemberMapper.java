package com.project.web_prj.member.repository;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.dto.AutoLoginDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {

    // 회원가입 기능
    boolean register (Member member);


    // 중복체크 기능
    // 체크타입 : 계정 or 이메일
    // 체크값 : 중복검사대상 값
    int isDuplicate(Map<String, Object> checkMap); // 굳이 클래스 하나 더 만들기 싫을 때 Map을 쓴다고 한다.
                                                    // 필드가 더 많으면 클래스를 하나 더 만드는게 좋다.


    // 회원정보 조회 기능
    Member findUser (String account);


    // 자동로그인 쿠키 정보 저장
    void saveAutoLoginValue(AutoLoginDTO dto);
}
