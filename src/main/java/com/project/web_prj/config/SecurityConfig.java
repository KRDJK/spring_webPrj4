package com.project.web_prj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 설정을 웹에 적용하는 어노테이션
public class SecurityConfig { // 본격적으로 쓰고자 하면 여기서 설정해야할게 많다고 한다.

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    // 시큐리티 기본 설정을 처리하는 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 초기에 나오는 디폴트 로그인 화면 안뜨게 하기
        http.csrf().disable() // .crsf().disable() : 웹사이트 공격 기법 중에 csrf 공격기법이 있다.
                            // 사용자의 의도와는 다르게 공격자가 의도한 요청을 보내도록 하는 기법.
                            // 토큰 방어 기법으로 방어한다. 스프링 시큐리티가 맘대로 넣어준다. 그걸 끄고 있음
                            // 나중에는 내가 방어를 해야함. 설정 해야겠지??

                .authorizeHttpRequests()
                // authorizeHttpRequests() : 변환요청 범위 설정하겠다

                .antMatchers("/member/**")
                // antMatchers("/member/**") : /member로 시작하는 요청은 따로 변환 검증하지 말아라

                .permitAll(); // permitAll() : 권한에 따라 들어오는 곳이 아니기 때문에 무슨 일이 있어도 누구든 들어오게 하라는 설정임.

//                .antMatchers("/board/**").hasRole("ADMIN"); // /board 요청은 ADMIN 권한이 있는 애만 접속가능하게 한다는 뜻이다.

        return http.build();
    }
}
