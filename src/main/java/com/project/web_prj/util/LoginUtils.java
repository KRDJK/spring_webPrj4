package com.project.web_prj.util;

import com.project.web_prj.member.domain.Member;

import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static final String LOGIN_FLAG = "loginUser";


    // 로그인 했는지 알려주기~~
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_FLAG) != null;
    }


    // 로그인한 사용자 계정 가져오기
    public static String getCurrentMemberAccount(HttpSession session) {
        Member member = (Member) session.getAttribute(LOGIN_FLAG);
        return member.getAccount();
    }
}
