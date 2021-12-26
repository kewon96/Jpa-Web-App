package com.we.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /** 암호화에 필요한 PasswordEncoder를 Bean에 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // permitAll로 인해 인증없이 쓸수 있는건 맞지만
        // 그렇다고 해서 안전하지 않은 요청까지 받는것은 아니다
        // 여기서 안전의 유무는 csrf token의 유무로 판단한다.
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .cors()
                .and()

                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/member/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/member/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest()
                .authenticated();
    }

    /**
     * html, ts같은 static 자원에 대한 인증검사를 무시하게 하는 설정
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/", "/**/*.html", "/favicon.ico")
                .antMatchers("/app/**")//not minified
                .antMatchers("/scripts/**", "/styles/**", "/images/**")
                .antMatchers("/style*", "/vendor*", "/app*", "/templates*") //minified
        ;
    }

}
