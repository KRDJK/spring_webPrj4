package com.project.web_prj.util;

import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static final String LOGIN_FLAG = "loginUser";


    // 로그인 했는지 알려주기~~
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_FLAG) != null;
    }
}
