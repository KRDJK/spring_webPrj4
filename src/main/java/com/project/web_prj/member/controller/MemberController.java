package com.project.web_prj.member.controller;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.dto.LoginDTO;
import com.project.web_prj.member.service.LoginFlag;
import com.project.web_prj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;



    // 회원가입 양식 화면 띄우기
    @GetMapping("/sign-up")
    public void signUp() {
        log.info("/member/sign-up GET! - forwarding to sign-up.jsp");
        // 스프링 시큐리티를 쓰는 순간 지 멋대로 로그인 화면을 만들어준다.
        // 아이디는 기본값이 user
        // 비밀번호는 로그에 나온다. Using generated security password: 6153ab33-e005-466c-b6d6-2790b8a13000
        // This generated password is for development use only. Your security configuration must be updated before running your application in production.
        // 개발 단계에서만 쓰라는 뜻이다.

        // 설정에서 저 화면 안뜨게 꺼줘야 한다.
//        return "member/sign-up";
//        요청 URL과 포워딩할 페이지 경로가 파일명까지 똑같을 경우 리턴 타입을 void로 잡고 쓸 수 있다.
//        그러면 알아서 매핑 요청 경로로 온 것을 찾아보고 연결해준다. 한글자라도 다르면 안된다.
    }


    // 실질적 회원가입 처리 요청 수행
    @PostMapping("/sign-up")
    public String signUp(Member member, RedirectAttributes ra) {
        log.info("/member/sign-up POST!! - {}", member);


        // 이렇게만 하면 안된다. 왜?? 검증이 하나도 안되고 있음
        boolean flag = memberService.signUp(member);


        // 회원가입이 되었음과 함께 축하 메세지를 띄우고자 한다.
        ra.addFlashAttribute("msg", "reg-success");

        return flag ? "redirect:/member/sign-in" : "redirect:/member/sign-up";
    }



    // 아이디, 이메일 중복확인 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(String type, String value) {
        log.info("/member/check?type={}&value={} GET!! ASYNC", type, value);

        boolean flag = memberService.checkSignUpValue(type, value);

        return new ResponseEntity<>(flag, HttpStatus.OK);
    }



    // 로그인 화면을 열어주는 요청처리
    @GetMapping("/sign-in")
    public void signIn() {
        log.info("/member/sign-in GET!!");
    }


    // 실질적 로그인 요청 처리
    @PostMapping("/sign-in")
    public String singIn(LoginDTO inputData, RedirectAttributes ra) {
        // 체크박스 클릭을 안하면 autoLogin 필드의 기본값인 false로 처리하고, on으로 넘어온다면
        // 자바스크립트의 truthy 개념을 적용해서 엇? 뭐가 값이 있네?? 그럼 true! 그래서 true로 넘어온다.

        log.info("/member/sign-in POST - {}", inputData);

        // 로그인 서비스 호출
        LoginFlag flag = memberService.login(inputData);


        if (flag == LoginFlag.SUCCESS) {
            log.info("login success");
            return "redirect:/";
        }

        ra.addFlashAttribute("loginMsg", flag); // NO_ACC 또는 NO_PW 상수값이 flag에 들어있을 거임.
        return "redirect:/member/sign-in";
    }
}
