package com.project.web_prj.practice.login.controller;

import com.project.web_prj.practice.login.DTO.UserLoginDTO;
import com.project.web_prj.practice.login.DTO.DuplicateChecker;
import com.project.web_prj.practice.login.domain.User;
import com.project.web_prj.practice.login.service.LoginResult;
import com.project.web_prj.practice.login.service.UserService;
import com.project.web_prj.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/practice")
public class UserController {

    private final UserService userService;


    // 연습용 메인 화면 요청 처리
    @GetMapping("/")
    public String practiceHome() {
        log.info("/practice Home GET! START!!");
        return "practice/prac-index";
    }


    // 회원가입 화면 요청 처리
    @GetMapping("/register")
    public void register(HttpServletRequest request) {
        log.info("/practice/register GET!!");

        String referer = request.getHeader("Referer");

    }


    // 아이디, 이메일 중복 확인 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(DuplicateChecker checker) {
        log.info("/practice/check Duplicate check ASYNC!!! - {}", checker);

        boolean flag = userService.isDuplicateService(checker);

        return new ResponseEntity<>(flag, HttpStatus.OK);
    }



    // 회원가입 반영 요청 처리
    @PostMapping("/register")
    public String register(User user, RedirectAttributes ra) {
        log.info("/practice/register POST! - {}", user);
        boolean flag = userService.registerService(user);



        return flag ? "redirect:/practice/" : "redirect:/register";
    }


    // 로그인 화면 요청 처리
    @GetMapping("/login")
    public void login(HttpServletRequest request) {

        // 로그인 화면에 진입하기 전 위치로 되돌려 보내기 위해 로그인 화면 요청시 어디서 왔는지 정보를 알아오자.
        String referer = request.getHeader("Referer");
        log.info("/practice/login GET! referer - {}", referer);

        request.getSession().setAttribute("redirectURI", referer);
    }


    // 로그인 확인 요청 처리
    @PostMapping("/login")
    public String login(UserLoginDTO inputData,
                        RedirectAttributes ra,
                        HttpSession session,
                        HttpServletResponse response,
                        Model model) {

        log.info("/practice/login POST! - {}", inputData);

        LoginResult loginResult = userService.loginService(inputData, session, response);

        String redirectURI = (String) session.getAttribute("redirectURI");


        if (loginResult == LoginResult.SUCCESS) {
            log.info("login-success");
            return "redirect:" + redirectURI;
        }

        ra.addFlashAttribute("loginMsg", loginResult);
        return "redirect:/practice/login";
    }



    // 로그아웃 요청 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("/practice/logout GET!!");

        // 로그인된 유저 정보 삭제
        session.removeAttribute(LoginUtils.LOGIN_FLAG);

        // 세션 무효화
        session.invalidate();

        return "redirect:/practice/";
    }
}
