package com.bbcommunity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@EnableWebSecurity //  Spring Security 설정을 활성화
@EnableMethodSecurity(prePostEnabled = true) //메소드 수준에서 보안 설정을 활성화, 메소드 접근 전에 보안 표현식을 평가
@Configuration
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable()) // CSRF 공격 방어를 비활성화. 토큰을 사용하는 방식이기 때문에 CSRF를 비활성화.
				.authorizeHttpRequests(request -> request.
						dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // FORWARD 타입의 모든 요청을 허용
//						.requestMatchers("/user/userManagement").hasAuthority("ROLE_ADMIN") // '/admin/**'으로 시작하는 URL은 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
						.requestMatchers("/", "/index.html", "/index","/user/login", "/user/register", "/post/all",
								"/css/**", "/js/**") // 인증 없이 접근 가능한 URL 패턴을 지정
						.permitAll() // 위에서 지정한 URL 패턴에 대한 모든 요청을 허용
						.anyRequest().authenticated() // 그 외의 모든 요청은 인증이 필요

				)
				
				/* 폼로그인 처리 */
				.formLogin(login -> login
						// 사용자가 인증되지 않은 상태에서 보안된 페이지에 접근하려고 하면 이 URL로 리다이렉트
						.loginPage("/user/login") // 사용자 정의 로그인 페이지 URL을 지정
						.loginProcessingUrl("/login-process") // 로그인 폼 데이터를 처리할 URL을 지정
						.usernameParameter("email") // 로그인 폼에서 사용자 ID를 받을 파라미터의 이름을 지정
						.passwordParameter("password") // 로그인 폼에서 비밀번호를 받을 파라미터의 이름을 지정
						.defaultSuccessUrl("/", true)  // 로그인 성공 후 리다이렉트할 URL을 지정
						.failureForwardUrl("/user/loginView") // 로그인 실패 시 포워드할 URL을 지정
						.permitAll()) // 로그인 과정에서의 모든 요청을 허용

				/* 폼 로그아웃 처리 */
				.logout(logout -> logout.
						logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL을 지정
						.permitAll() // 로그아웃 과정에서의 모든 요청을 허용
						.invalidateHttpSession(true)) // 로그아웃 성공 후 HTTP 세션을 무효화

				

				.exceptionHandling().accessDeniedPage("/"); // 접근이 거부된 경우 리다이렉트할 URL을 지정
		;
		return http.build();
	}
	
	/*
	 * PasswordEncoder를 빈으로 등록합니다.
	 */
	
	@Bean
	PasswordEncoder passwordEncoder() {
		// BCrypt 암호화 알고리즘을 사용하여 비밀번호를 암호화
        // 이 암호화 방식은 단방향 암호화 방식이 아니므로 data.sql에 비밀번호를 저장할 때 미리 암호화하여 저장
		return new BCryptPasswordEncoder();
	}
}





