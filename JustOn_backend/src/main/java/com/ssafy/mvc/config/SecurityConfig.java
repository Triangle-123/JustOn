package com.ssafy.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// BCrypt 암호화 Bean 등록
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//csrf disable
		http
			.csrf((auth) -> auth.disable()); 
			// jwt 방식은 세션을 무상태(stateless) 방식으로 사용하므로
			// csrf 공격을 방어할 필요가 없음.
		
		// Form 로그인 방식 비활성화
		http
			.formLogin((auth) -> auth.disable());
		
		// http basic 인증방식 비활성화
		http
			.httpBasic((auth) -> auth.disable());
		
		// 경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/login", "/", "/join").permitAll() // 모든 권한에 대해 허용
					.requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 경우만 허용
					.anyRequest().authenticated()); // 그 외 요청은 로그인한 사용자만 허용
		
		// 세션 설정
		// 세션을 무상태 방식으로 만듬 => 일시적인 세션(사용자가 같아도 세션은 한번의 요청만 수행하고 사라짐)
		http
         	.sessionManagement((session) -> session
         			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
		return http.build();
	}
}
