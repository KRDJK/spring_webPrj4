package com.project.web_prj.member.controller;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.domain.OAuthValue;
import com.project.web_prj.member.domain.SNSLogin;
import com.project.web_prj.member.dto.KakaoUserInfoDTO;
import com.project.web_prj.member.service.KakaoService;
import com.project.web_prj.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;



    @GetMapping("/kakao-test")
    public void kakaoTest(Model model) {
        log.info("fowarding kakao-test.jsp!!");
        model.addAttribute("appKey", OAuthValue.KAKAO_APP_KEY);
        model.addAttribute("redirectUri", OAuthValue.KAKAO_REDIRECT_URI);
    }



    // 카카오 인증서버가 보내준 인가코드를 받아서 처리할 메서드
    @GetMapping(OAuthValue.KAKAO_REDIRECT_URI)
    public String kakaoLogin(String code, HttpSession session) throws Exception { // response를 code라는 이름으로 String 타입으로 준댔으니까!
        log.info("{} GET!! code - {}", OAuthValue.KAKAO_REDIRECT_URI, code);


        // 인가코드를 통해 액세스 토큰 발급받기
        // 우리 서버에서 카카오 서버로 통신을 해야함.
        String accessToken = kakaoService.getAccessToken(code);


        // 액세스 토큰을 통해 사용자가 정보 수집에 동의해서 서버로 요청한 데이터 받아오기 (프로필 사진, 닉네임 등)
        KakaoUserInfoDTO kakaoUserInfo = kakaoService.getKakaoUserInfo(accessToken);
        


        // 로그인 처리
        if (kakaoUserInfo != null) {
            Member member = new Member();
            member.setAccount(kakaoUserInfo.getEmail()); // 우리 서버에서는 카카오 로그인한 사람의 카카오 이메일을 계정명으로 쓰게 하겠다.
            member.setName(kakaoUserInfo.getNickname());
            member.setEmail(kakaoUserInfo.getEmail());


            // 세션에 로그인한 사람 정보를 담아야지.
            session.setAttribute(LoginUtils.LOGIN_FLAG, member);
            session.setAttribute("profile_path", kakaoUserInfo.getProfileImg());
            session.setAttribute(LoginUtils.LOGIN_FROM, SNSLogin.KAKAO); // 각 SNS 플랫폼 별로 로그아웃 방식이 다르기 때문에 그 때 구분을 위해서!!

            session.setAttribute("accessToken", accessToken);
            return "redirect:/";
        }

        return "redirect:/member/sign-in";
    }



    // 카카오 로그아웃
    @GetMapping("/kakao/logout")
    public String kakaoLogout(HttpSession session) throws Exception {

        // 카카오 로그아웃 처리
        kakaoService.logout((String) session.getAttribute("accessToken"));


        // 우리 서비스에서 로그 아웃
        session.invalidate();

        return "redirect:/kakao-test";
    }

}
