package com.krsia.blog.config;

import com.krsia.blog.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    // 비밀번호 암호화기
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자 정보 서비스 (DB 조회)
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // 보안 필터 체인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 데모용 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**", "/style.css").permitAll()
                        .requestMatchers("/posts", "/posts/*").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")      // 로그인 폼에서 email 필드 사용
                        .passwordParameter("password")   // 로그인 폼에서 password 필드 사용
                        .defaultSuccessUrl("/", true)    // 로그인 성공 시 홈으로 이동
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
