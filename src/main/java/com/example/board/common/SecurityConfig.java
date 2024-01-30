package com.example.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity   // 시큐리티 커스텀
// WebSecurityConfigurerAdapter를 상속하는 방식은 deprecated(지원종료) 되었다.

@EnableGlobalMethodSecurity(prePostEnabled = true)
// pre : 사전 , post : 사후,    사전 사후에 인증/권한 검사를 할수 있도록 해줌.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myfilter(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
//                csrf 보안공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()


//                특정 url대해서는 인증처리 하지 않고, 특정 url대해서는 인증처리 하겠다라는 설정.
                .authorizeRequests()
//                  인증 미적용 url 패턴
                    .antMatchers("/","/author/create", "/author/login-page" )
                        .permitAll()
                    .anyRequest().authenticated()
                .and()



//                .sessionManagement()                  // 만약에 세션방식을 사용하지 않으면 설정
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page")    // 이거 지우면 기본 스프링 폼이 나옴.
                    .loginProcessingUrl("/doLogin")      // 스프링 내장 매서드를 사용하기위해 /doLogin url 사용
                        .usernameParameter("email")
                        .passwordParameter("pw")
//                    .defaultSuccessUrl("/author/list", true)
                .successHandler(new LoginSuccessHandler())
                .and()


                .logout()
//                spring security의 doLogout기능 그대로 사용
                    .logoutUrl("/doLogout")
                .and()

                .build();

    }
}
