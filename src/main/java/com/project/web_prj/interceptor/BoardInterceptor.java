package com.project.web_prj.interceptor;

import com.project.web_prj.util.LoginUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// 인터셉터: 컨트롤러에 요청이 들어가기 전 또는 후에 공통처리할
//          일들을 정의해놓는 클래스
@Configuration // 얘도 설정파일이라서 붙여야한다.
@Log4j2
public class BoardInterceptor implements HandlerInterceptor {

    /*
        preHandle : 인터셉터의 전처리 메서드.
                    리턴값이 true일 경우 컨트롤러 진입을 허용하고,
                    false일 경우 컨트롤러 진입을 허용하지 않는다.
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/sign-in.jsp");

        log.info("board interceptor preHandle()");
//        if (session.getAttribute("loginUser") == null) {
        if (!LoginUtils.isLogin(session)) {
            log.info("this request deny!! 집에 가");

//            dispatcher.forward(request, response);

            response.sendRedirect("/member/sign-in");

            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }




    /*
        postHandle : 인터셉터의 후처리 메서드.
                    interceptorConfig 에서 설정된 경로 패턴 하위 요청이 실행된 이후마다 어떤 로직을 실행할지 설정할 수 있다.
                    /board/* 이면 이거 하나로 설정하긴 빡세겠군..
                    /board/modify, /board/write 이런 각각의 요청들이 수행된 이후 하나의 로직이 실행되게 하는거니까..
                    각각 세부 후처리가 다를텐데.. if문을 계속 걸기보단 그냥 컨트롤러에서 바로 후처리를 해주는게 직관적이고 편할듯.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info("board interceptor postHandle() !! ");
    }
}
