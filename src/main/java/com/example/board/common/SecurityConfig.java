package com.example.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity   // 시큐리티 커스텀
// WebSecurityConfigurerAdapter를 상속하는 방식은 deprecated(지원종료) 되었다.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myfilter(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
//                csrf 보안공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()
////                특정 url대해서는 인증처리 하지 않고, 특정 url대해서는 인증처리 하겠다라는 설정.
                .authorizeRequests()
////                  인증 미적용 url 패턴
                    .antMatchers("/","/author/create", "/author/login" )
                        .permitAll()
                    .anyRequest().authenticated()
                .and()
                .build();

    }
}
