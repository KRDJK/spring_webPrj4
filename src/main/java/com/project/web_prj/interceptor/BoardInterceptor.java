package com.project.web_prj.interceptor;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.util.LoginUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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

            response.sendRedirect("/member/sign-in?message=no-login");

            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }




    /*
        postHandle : 인터셉터의 후처리 메서드.
                    오전의 내 생각
                    interceptorConfig 에서 설정된 경로 패턴 하위 요청이 실행된 이후마다 어떤 로직을 실행할지 설정할 수 있다.
                    /board/* 이면 이거 하나로 설정하긴 빡세겠군..
                    /board/modify, /board/write 이런 각각의 요청들이 수행된 이후 하나의 로직이 실행되게 하는거니까..
                    각각 세부 후처리가 다를텐데.. if문을 계속 걸기보단 그냥 컨트롤러에서 바로 후처리를 해주는게 직관적이고 편할듯.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // postHandle이 작동해야 하는 URI 목록
        List<String> uriList = Arrays.asList("/board/modify", "/board/delete");


        // 현재 요청 URI 정보 알아내기
        String requestURI = request.getRequestURI();
        log.info("requestURI - {}", requestURI);

        
        // 현재 요청 메서드 정보 확인 GET, POST (PUT, DELETE??)
        String method = request.getMethod();



        // postHandle은 uriList 목록에 있는 URI에서만 작동하게 함.
        if (uriList.contains(requestURI) && method.equalsIgnoreCase("GET")) {
            log.info("board interceptor postHandle() !! ");

            HttpSession session = request.getSession();


            // 컨트롤러의 메서드를 처리한 후 모델에 담긴 데이터의 맵이다.
            Map<String, Object> modelMap = modelAndView.getModel();
            // 22.08.04 오후에 내 생각 : 각 요청이 수행되고 난 후 모델에 담긴건 요청마다 다를테니 각각 후처리가 가능하긴 하겠구나.


//        log.info("modelMap.size() - {}", modelMap.size());
//        log.info("modelMap -{}", modelMap);

            ValidateMemberDTO dto = (ValidateMemberDTO) modelMap.get("validate");
            String account = dto.getAccount();


            // 수정하려는 게시글의 계정명 정보와 세션에 저장된 계정명 정보가
            // 일치하지 않으면 돌려보내라
//        log.info("게시물의 계정명 - {}", board.getAccount());
//        log.info("로그인한 계정명 - {}", LoginUtils.getCurrentMemberAccount(request.getSession()));


            if (isAdmin(session)) return;

            if (!isMine(session, account)) {
                response.sendRedirect("/board/list");
            }
        }
    }


    // 현재 로그인한 사용자의 권한이 관리자인지 확인하는 메서드
    private boolean isAdmin(HttpSession session) {
        return LoginUtils.getCurrentMemberAuth(session).equals("ADMIN");
    }


    // 게시물을 등록한 게시물의 주인 계정명과 현재 로그인한 사용자가 같은 사람인지 확인하는 메서드
    private boolean isMine(HttpSession session, String account) {
        return account.equals(LoginUtils.getCurrentMemberAccount(session));
    }
}
