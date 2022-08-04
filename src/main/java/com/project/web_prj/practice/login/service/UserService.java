package com.project.web_prj.practice.login.service;

import com.project.web_prj.practice.login.DTO.UserLoginDTO;
import com.project.web_prj.practice.login.DTO.DuplicateChecker;
import com.project.web_prj.practice.login.domain.User;
import com.project.web_prj.practice.login.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder encoder;


    // 회원가입 중간처리 메서드
    public boolean registerService(User user) {
        user.setPassword(encoder.encode(user.getPassword()));

        return userMapper.register(user);
    }


    // 아이디, 이메일 중복확인 중간처리 메서드
    public boolean isDuplicateService(DuplicateChecker checker) {
        return userMapper.isDuplicate(checker) == 1;
    }


    // 개별 회원 조회 중간처리 메서드
    public User findUserService(String id) {
        return userMapper.findUser(id);
    }


    // 로그인 중간처리 메서드
    public LoginResult loginService(UserLoginDTO inputData, HttpSession session, HttpServletResponse response) {

        User user = findUserService(inputData.getId());

        if (user != null) { // 아이디는 맞게 입력해서 누군가 찾아오긴 했다면

            if (encoder.matches(inputData.getPassword(), user.getPassword())) { // 비번도 일치하는지 검증

                // 로그인 성공한 경우

                // 세션에 로그인한 회원 정보 넣어주기 및 수명 설정.
                session.setAttribute("loginUser", user);
                session.setMaxInactiveInterval(60 * 60);

                if (inputData.isAutoLogin()) {
                    log.info("rememberMe Logic Start");
                    rememberMe(inputData, session, response);
                }

                return LoginResult.SUCCESS;
            } else { // 비밀번호 틀린 경우
                return LoginResult.NO_PW;
            }
        }

        return LoginResult.NO_ID;
    }

    private void rememberMe(UserLoginDTO inputData, HttpSession session, HttpServletResponse response) {


        Cookie cookie = new Cookie("autoLoginCookie", session.getId());
        cookie.setPath("/practice/");
        int limitTime = 60 * 60 * 24 * 90;
        cookie.setMaxAge(limitTime);


        // 해당 사용자 로컬에 쿠키를 넣어줘야지.
        response.addCookie(cookie);


        // DB에 쿠키값과 수명 저장
        // 귀찮아..

    }
}
