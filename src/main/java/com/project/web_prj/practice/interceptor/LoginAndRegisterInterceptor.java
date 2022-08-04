package com.project.web_prj.practice.interceptor;

import com.project.web_prj.member.service.LoginFlag;
import com.project.web_prj.practice.login.domain.User;
import com.project.web_prj.util.LoginUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class LoginAndRegisterInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(LoginUtils.LOGIN_FLAG);

        if (user != null) {
            response.sendRedirect("/practice/");
            return false;
        }

        return true;
    }
}
