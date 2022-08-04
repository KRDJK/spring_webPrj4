package com.project.web_prj.practice.login.service;

import com.project.web_prj.practice.login.DTO.UserLoginDTO;
import com.project.web_prj.practice.login.domain.DuplicateChecker;
import com.project.web_prj.practice.login.domain.User;
import com.project.web_prj.practice.login.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public LoginResult loginService(UserLoginDTO inputData) {

        User user = findUserService(inputData.getId());

        if (user != null) { // 아이디는 맞게 입력해서 누군가 찾아오긴 했다면

            if (encoder.matches(inputData.getPassword(), user.getPassword())) { // 비번도 일치하는지 검증

                // 로그인 성공한 경우
                return LoginResult.SUCCESS;


            } else { // 비밀번호 틀린 경우
                return LoginResult.NO_PW;
            }
        }

        return LoginResult.NO_ID;
    }
}
