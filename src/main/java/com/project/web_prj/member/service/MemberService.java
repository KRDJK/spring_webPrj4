package com.project.web_prj.member.service;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;



    // 회원 가입 중간 처리
    public boolean signUp(Member member) {

        // 비밀번호 인코딩
        member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));

        return memberMapper.register(member);
    }


    /**
     * 계정과 이메일의 중복을 확인하는 메서드
     * @param type - 확인할 정보 ( ex: account or email )
     * @param value - 확인할 값
     * @return 중복이라면 true, 중복이 아니라면 false
     */
    // 중복 확인 중간 처리
    public boolean checkSignUpValue(String type, String value) {

        Map<String, Object> checkMap = new HashMap<>();

        checkMap.put("type", type);
        checkMap.put("value", value);

        return memberMapper.isDuplicate(checkMap) == 1;
    }


    // 개별 회원 정보 조회 중간 처리
    public Member getMember(String account) {
        return memberMapper.findUser(account);
    }
}
