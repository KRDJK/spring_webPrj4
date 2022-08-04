package com.project.web_prj.interceptor;

import com.project.web_prj.util.LoginUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class AfterLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 이미 로그인을 했는데 주소창을 통해 또 로그인 창에 진입하는 것을 막아야 한다.
        HttpSession session = request.getSession();
        if (LoginUtils.isLogin(session)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
