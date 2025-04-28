	package com.ssafy.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ssafy.mvc.jwt.JWTFilter;
import com.ssafy.mvc.jwt.JWTUtil;
import com.ssafy.mvc.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    private final CorsConfigurationSource corsConfigurationSource;
    
    private final JWTUtil jwtUtil;
    
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, CorsConfigurationSource corsConfigurationSource, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtUtil = jwtUtil;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
	
	// BCrypt 암호화 Bean 등록	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//csrf disable
		
		http.
			cors(cors -> cors.configurationSource(corsConfigurationSource));
		
		http
			.csrf((auth) -> auth.disable()); 
			// jwt 방식은 세션을 무상태(stateless) 방식으로 사용하므로
			// csrf 공격을 방어할 필요가 없음.
		
		// Form 로그인 방식 비활성화
		http	
			.formLogin((auth) -> auth.disable());
		
//		http
//			.formLogin((auth) -> auth
//					.loginPage("/api-user/login")
//					.permitAll());
		
		// http basic 인증방식 비활성화
		http
			.httpBasic((auth) -> auth.disable());
		
		// 경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/assets/**", "/js/**", "/img/**", "/uploads/**").permitAll()
					.requestMatchers("/api-user/login").permitAll()
					.requestMatchers("/api-user/**").permitAll() // 모든 권한에 대해 허용
//					.requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 경우만 허용
					.anyRequest().authenticated()); // 그 외 요청은 로그인한 사용자만 허용
		
		http
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		
		//필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
		http
			.addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class); // At은 필터가 실행될 때 그 필터를 대체, before는 전, after는 후
		
		
		// 세션 설정
		// 세션을 무상태 방식으로 만듬 => 일시적인 세션(사용자가 같아도 세션은 한번의 요청만 수행하고 사라짐)
		http
         	.sessionManagement((session) -> session
         			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
		return http.build();
		
		
	}
	
	
}
