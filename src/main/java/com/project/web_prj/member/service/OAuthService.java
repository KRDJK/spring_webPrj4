package com.project.web_prj.member.service;


// SNS 로그인 통신처리 담당 인터페이스
public interface OAuthService {



    /**
     * 액세스 토큰 발급 메서드
     * @param authCode - 인증서버가 발급한 인가코드
     * @return - 액세스 토큰
     * @throws Exception - 통신 에러
     */
    String getAccessToken(String authCode) throws  Exception;
}
