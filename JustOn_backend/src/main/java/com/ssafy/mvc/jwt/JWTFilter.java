
package com.ssafy.mvc.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//스프링 시큐리티 filter chain에 요청에 담긴 JWT를 검증하기 위한 커스텀 필터를 등록해야 한다.
//
//해당 필터를 통해 요청 헤더 Authorization 키에 JWT가 존재하는 경우 
// JWT를 검증하고 강제로 SecurityContextHolder에 세션을 생성한다. 
// (이 세션은 STATELESS 상태로 관리되기 때문에 해당 요청이 끝나면 소멸 된다.)
public class JWTFilter extends OncePerRequestFilter { // OncePerRequestFilter => 요청에 대해 한번만 동작하는 필터

	private JWTUtil jwtUtil;

	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		// Authorization 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {

			System.out.println("token null");
			filterChain.doFilter(request, response);

			// 조건이 해당되면(토큰이 없다면) 메소드 종료 (필수)
			return;
		}

		System.out.println("authorization now");
		
		// Bearer 부분 제거 후 순수 토큰만 획득
		String token = authorization.split(" ")[1];

		// 토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {

			System.out.println("token expired");
			filterChain.doFilter(request, response);

			// 조건이 해당되면(토큰이 소멸되었으면) 메소드 종료 (필수)
			return;
		}

		// 토큰에서 username과 role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		// user 객체를 생성하여 값 set
		User user = new User();
		user.setUserId(username);
		user.setPassword("tempPassword");
		user.setRole(role);

		// UserDetails에 회원 정보 객체 담기
		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		// 스프링 시큐리티 인증 토큰 생성
		// UsernamePasswordAuthenticationToken(principal => 접근주체(사용자 객체), credentials => 비밀번호, authorities => 사용자의 권한 목록) 
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
				customUserDetails.getAuthorities());
		
		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response); // 다음 필터에 request와 responce를 보내는 것

	}

}
