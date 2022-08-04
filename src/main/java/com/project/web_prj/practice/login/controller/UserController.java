package com.project.web_prj.practice.login.controller;

import com.project.web_prj.practice.login.DTO.UserLoginDTO;
import com.project.web_prj.practice.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/practice")
public class UserController {

    private final UserService userService;


    @GetMapping("/")
    public String practiceHome() {
        return "practice/prac-index";
    }


    @GetMapping("/login")
    public void login(HttpServletRequest request) {

        // 로그인 화면에 진입하기 전 위치로 되돌려 보내기 위해 로그인 화면 요청시 어디서 왔는지 정보를 알아오자.
        String referer = request.getHeader("Referer");
        log.info("referer -{}", referer);

        request.getSession().setAttribute("redirectURI", referer);
    }


    @PostMapping("/login")
    public String login(UserLoginDTO inputData, HttpSession session, Model model) {
        log.info("/practice/login POST! - {}", inputData);

        String redirectURI = (String) session.getAttribute("redirectURI");

        return "redirect:" + redirectURI;
    }
}
