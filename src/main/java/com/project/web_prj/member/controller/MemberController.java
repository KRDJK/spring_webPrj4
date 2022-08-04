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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        // 비밀번호는 로그에 나온다(서버 킬 때마다 다름). Using generated security password: 6153ab33-e005-466c-b6d6-2790b8a13000
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
    public void signIn(@ModelAttribute("message") String message, HttpServletRequest request) {
        log.info("/member/sign-in GET!! - forwarding to sign-in.jsp");

        // 요청 정보 헤더 안에는 Referer 라는 키가 있는데
        // 여기 안에는 이 페이지로 진입할 때 어디에서 왔는지 URI 정보가 들어있다.
        String referer = request.getHeader("Referer");
        log.info("referer: {}", referer);

        request.getSession().setAttribute("redirectURI", referer);
    }


    // 실질적 로그인 요청 처리
    @PostMapping("/sign-in")
    public String singIn(LoginDTO inputData,
                         Model model,
                         RedirectAttributes ra,
//                         HttpServletRequest request,
                         HttpSession session // 스프링에서 바로 세션 정보를 활용할 수 있게 도와준다. 세션 정보 객체임.
    ) {
        // 체크박스 클릭을 안하면 autoLogin 필드의 기본값인 false로 처리하고, on으로 넘어온다면
        // 자바스크립트의 truthy 개념을 적용해서 엇? 뭐가 값이 있네?? 그럼 true! 그래서 true로 넘어온다.

        log.info("/member/sign-in POST - {}", inputData);
//        log.info("session timeout : {}", session.getMaxInactiveInterval()); // 기본 수명이 30분이다.


        // 로그인 서비스 호출
        LoginFlag flag = memberService.login(inputData, session);


        if (flag == LoginFlag.SUCCESS) {
            log.info("login success");
//            HttpSession session = request.getSession();
//            session.setAttribute("loginUser", );
            String redirectURI = (String) session.getAttribute("redirectURI");
            return "redirect:" + redirectURI;
        }

        // 틀렸을 때 리다이렉팅이 되어버리면 session.referer 정보가 날아가기 때문에 model에 담아 포워딩하는 식으로 바꿨다.
        model.addAttribute("loginMsg", flag); // NO_ACC 또는 NO_PW 상수값이 flag에 들어있을 거임.
        return "/member/sign-in";
    }



    // 로그아웃 요청 처리
    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {

        if (session.getAttribute("loginUser") != null) {
            // 1. 세션에서 정보를 삭제한다.
            session.removeAttribute("loginUser");


            // 2. 세션을 무효화한다. 타임아웃까지 삭제해야 함.
            session.invalidate();
            return "redirect:/";
        }

        // 로그인도 안해놓고 이런 요청 보내면 로그인 화면으로 보낼 것.
        return "redirect:/member/sign-in";
    }

} // end class
