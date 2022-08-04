package com.project.web_prj.config;

import com.project.web_prj.interceptor.AfterLoginInterceptor;
import com.project.web_prj.interceptor.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 다양한 인터셉터들을 관리하는 설정 클래스
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final BoardInterceptor boardInterceptor;
    private final AfterLoginInterceptor afterLoginInterceptor;


    // 인터셉터 설정 추가 메서드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // 게시판 인터셉터 설정
        registry.addInterceptor(boardInterceptor) // 로그인 검증을 하는 인터셉터를
                .addPathPatterns("/board/*") // 이렇게 시작하는 경로에 이 인터셉터를 적용하겠다.
                .excludePathPatterns("/board/list", "/board/content"); // 이 경로에서는 인터셉터 적용을 제외하겠다.


        // 애프터 로그인 인터셉터 설정
        registry.addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/member/sign-in", "/member/sign-up");
    }
}
