package com.project.web_prj.member.service;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.dto.AutoLoginDTO;
import com.project.web_prj.member.dto.LoginDTO;
import com.project.web_prj.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.project.web_prj.member.service.LoginFlag.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;



    // 회원 가입 중간 처리
    public boolean signUp(Member member) {

        // 비밀번호 인코딩
//        member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
        member.setPassword(encoder.encode(member.getPassword()));


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



    // 로그인 처리
    public LoginFlag login(LoginDTO inputData, HttpSession session, HttpServletResponse response) {

        // 회원가입 여부 확인
        Member foundMember = memberMapper.findUser(inputData.getAccount());

        if (foundMember != null) {
            // id를 잘 찾았으면 비번 검증
            if (encoder.matches(inputData.getPassword(), foundMember.getPassword())) {
                // 로그인이 성공한 경우

                // 세션에 사용자 정보기록 저장
                session.setAttribute("loginUser", foundMember);
                // 세션 타임아웃(수명) 설정
                session.setMaxInactiveInterval(60 * 60); // 기본값이 30분이지만, 1시간으로 임의 설정하기
                
                
                // 자동로그인 처리
                if (inputData.isAutoLogin()) { // 자동로그인을 체크한 사람이라면~~
                    log.info("checked auto login user!!");
                    keepLogin(foundMember.getAccount(), session, response); // 해외에선 rememberMe 라는 이름으로 쓴다고 한다.
                }

                return SUCCESS;
            } else {
                // 비번이 틀린 경우
                return NO_PW;
            }
        } else {
            // 아이디가 없는 경우
            return NO_ACC;
        }
    }

    // 자동 로그인 처리
    private void keepLogin(String account, HttpSession session, HttpServletResponse response) {

        // 1. 자동로그인 쿠키 생성 - 쿠키의 값으로 현재 세션의 아이디를 저장한다.
        String sessionId = session.getId();
        Cookie cookie = new Cookie("autoLoginCookie", sessionId);


        // 2. 쿠키 설정 (수명, 사용 경로)
        int limitTime = 60 * 60 * 24 * 90; // 90일에 대한 초단위 표현
        cookie.setMaxAge(limitTime);
        cookie.setPath("/"); // 전체 경로에서 사용할 수 있어야 한다.
                            // 사용자가 어떤 경로에서 접근을 하더라도 자동로그인이 되어야 하기 때문!!
        
        
        // 3. 자동로그인을 체크하고 로그인에 성공한 해당 유저의 로컬에 쿠키 전송
        response.addCookie(cookie);


        // 4. DB에 쿠키값과 수명 저장
        AutoLoginDTO dto = new AutoLoginDTO();
        dto.setSessionId(sessionId);


        // 4-1. 수명은 좀 생각해봐야 한다. 왜?? DB에는 DATE라서 날짜형식으로 넣어야 하는데 위에 limitTime은 초단위이기 때문!!
        // 자동로그인 유지시간(초)을 날짜로 변환
        long nowTime = System.currentTimeMillis();
        Date limitDate = new Date(nowTime + ((long) limitTime * 1000)); // 밀리초는 1000분의 1초구나
        dto.setLimitTime(limitDate);


        dto.setAccount(account);


        memberMapper.saveAutoLoginValue(dto);
    }
}
