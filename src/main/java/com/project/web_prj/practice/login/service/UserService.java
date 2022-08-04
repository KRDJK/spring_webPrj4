package com.project.web_prj.practice.login.service;

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


    // 아이디, 이메일 중복확인 중간 메서드
//    public boolean
}
