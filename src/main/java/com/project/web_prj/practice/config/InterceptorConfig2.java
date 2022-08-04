package com.project.web_prj.practice.config;

import com.project.web_prj.practice.interceptor.LoginAndRegisterInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig2 implements WebMvcConfigurer {

    private final LoginAndRegisterInterceptor loginAndRegisterInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAndRegisterInterceptor)
                .addPathPatterns("/practice/login", "/practice/register");
    }
}
