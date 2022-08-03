package com.project.web_prj.member.repository;

import com.project.web_prj.member.domain.Auth;
import com.project.web_prj.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper mapper;


    @Test
    @DisplayName("회원가입에 성공해야 한다.")
    void registerTest() {

        Member member = new Member();
        member.setAccount("apple123");
        member.setPassword("12345"); // 인코딩 작업이 필요하다. 암호화 알고리즘 - 스프링이 제공하는 클래스가 있다.
        member.setName("사과왕");
        member.setEmail("apple@gmail.com");
        member.setAuth(Auth.ADMIN);

        boolean flag = mapper.register(member);

        assertTrue(flag);
    }


    @Test
    @DisplayName("비밀번호가 암호화 인코딩되어야 한다.")
    void encodePasswordTest() {

        // 인코딩 전 비밀번호
        String rawPassword = "ddd5555";

        // 인코딩을 위한 객체 생성
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 스프링에 빈 등록 해놓으면 매번 객체를 생성하지 않아도 된다.


        // 인코딩 후 비밀번호
        String encodePassword = encoder.encode(rawPassword);

        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encodePassword = " + encodePassword);
//        $2a$10$1rDQ49nfwNMNNgeM8tbti.azei/zu4/F.CCCY2/ou9X7ytK0QLyCi   겁나 기네
    }


    @Test
    @DisplayName("회원가입에 비밀번호가 인코딩된 상태로 성공해야 한다.")
    void registerTest2() {

        Member member = new Member();
        member.setAccount("peach");
        member.setPassword(new BCryptPasswordEncoder().encode("1234"));
        member.setName("천도복숭아");
        member.setEmail("peach@gmail.com");
        member.setAuth(Auth.ADMIN);

        boolean flag = mapper.register(member);

        assertTrue(flag);
    }

    
    @Test
    @DisplayName("특정 계정명으로 회원을 조회해야 한다.")
    void findUserTest() {
        //given
        String account = "peach";

        //when
        Member member = mapper.findUser(account);

        //then
        System.out.println("member = " + member);
        assertEquals("천도복숭아", member.getName());
    }


//    실패 테스트
    @Test
    @DisplayName("특정 계정명으로 회원을 조회할 수 없어야 한다.")
    void findUserTest2() {
        //given
        String account = "peach123";

        //when
        Member member = mapper.findUser(account);

        //then
        assertNull(member);
    }


    @Test
    @DisplayName("아이디를 중복확인 할 수 있다.")
    void checkAccountTest() {
        //given
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("type", "account");
        checkMap.put("value", "peach");

        //when
        int flagNumber = mapper.isDuplicate(checkMap);

        //then
        assertEquals(1, flagNumber);
    }


    @Test
    @DisplayName("이메일을 중복확인 할 수 있다.")
    void checkEmailTest() {
        //given
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("type", "email");
        checkMap.put("value", "peach@gmail.com");

        //when
        int flagNumber = mapper.isDuplicate(checkMap);

        //then
        assertEquals(1, flagNumber);
    }
}