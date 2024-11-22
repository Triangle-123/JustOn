package com.ssafy.mvc.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.LoginDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.setFilterProcessesUrl("/api-user/login");
		// !!!!!!!!!!주의 : UsernamePasswordAuthenticationFilter는 /login에 POST 방식으로 들어오는 요청에 작동하는 필터이다.
		// 따라서 다른 경로로 들어오는 로그인 요청을 받으려면 그 경로를 직접 명시해 주어야한다......
		
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}
	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		System.out.println("커스텀 필터가 호출되었습니다.");
//        attemptAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
//	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication");
		LoginDTO loginDTO = new LoginDTO();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            System.out.println(messageBody);
            loginDTO = objectMapper.readValue(messageBody, LoginDTO.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		
        // 클라이언트의 요청에서 userName(id), password 추출
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();
        
//        String userName = obtainUsername(request);
//        String password = obtainPassword(request);
		
		System.out.println("userName : " + userName);
		
		System.out.println("password : " + password);
		
		//스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password, null);
		
		//token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}
	
	// 로그인 성공시 (여기서 JWT 발급할 것)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Success");
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal(); // 인증된 사용자 정보 불러오기
		
		String username = customUserDetails.getUsername(); // 사용자 아이디 가져오기
		
		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities(); // 인증된 사용자의 권한 목록을 불러온다.
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); // 권한 목록 중 첫 번째 권한을 가져오기 위한 반복자
		GrantedAuthority auth = iterator.next(); // 권한 목록에서 첫 번째 권한을 가져온다.
		// GrantedAuthority는 일반적으로 역할(role)을 나타내며, 예를 들어 ROLE_USER, ROLE_ADMIN 등의 값을 가질 수 있습니다.
		
		String role = auth.getAuthority(); // GrantedAuthority 객체에서 권한을 문자열로 반환한다.
		
		String token = jwtUtil.createJwt(username, role, 60 * 60 * 1000L * 12); // 유저 아이디, 권한, 만료시간을 포함하여 JWT 생성
		
		
		// HTTP 인증 방식은 RFC 7235 정의에 따라 아래 인증 헤더 형태를 가져야 한다. (준공식적 약속)
		// Authorization: 타입 인증토큰
		//예시 : Authorization: Bearer 인증토큰string
		response.addHeader("Authorization", "Bearer " + token); // 생성된 JWT를 HTTP 응답의 헤더에 추가
		// JWT는 보통 Authorization 헤더에 Bearer 타입으로 포함됨 
		// => Authorization: Bearer <token> 형식
		
	}
	
	// 로그인 실패시
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Fail");
		response.setStatus(401);
	}
}
