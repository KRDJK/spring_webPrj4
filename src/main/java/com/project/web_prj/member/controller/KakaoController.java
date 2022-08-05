package com.project.web_prj.member.controller;

import com.project.web_prj.member.domain.OAuthValue;
import com.project.web_prj.member.dto.KakaoUserInfoDTO;
import com.project.web_prj.member.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String kakaoLogin(String code) throws Exception { // response를 code라는 이름으로 String 타입으로 준댔으니까!
        log.info("{} GET!! code - {}", OAuthValue.KAKAO_REDIRECT_URI, code);


        // 인가코드를 통해 액세스 토큰 발급받기
        // 우리 서버에서 카카오 서버로 통신을 해야함.
        String accessToken = kakaoService.getAccessToken(code);


        // 액세스 토큰을 통해 사용자가 정보 수집에 동의해서 서버로 요청한 데이터 받아오기 (프로필 사진, 닉네임 등)
        KakaoUserInfoDTO kakaoUserInfo = kakaoService.getKakaoUserInfo(accessToken);


        return "";
    }

}
