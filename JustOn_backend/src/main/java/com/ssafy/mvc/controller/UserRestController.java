package com.ssafy.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	
	private final UserService userService;
	private final JwtUtil jwtUtil;

	public UserRestController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "/user/loginform";
	}
	
	// 사용자 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		User tmp = userService.login(user.getUserId(), user.getPassword());
		if(tmp == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 아이디 입니다.");
		}
		session.setAttribute("loginUser", tmp.getName());
		return ResponseEntity.ok().build();
	}
	
	// 사용자 로그인  - JWT 적용
//	@PostMapping("/login")
//	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
//		HttpStatus status = null;
//		Map<String, Object> result = new HashMap<>();
//		User loginUser = userService.login(user.getUserId(), user.getPassword());
//
//		System.out.println(loginUser);
//		if (loginUser != null) {
//			result.put("message", "login 성공");
////			result.put("access-token", jwtUtil.createToken(loginUser.getName()));
//			// id도 같이 넘겨주면 번거로운 작업을 할 필요는 없어
//			status = HttpStatus.ACCEPTED;
//		} else {
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<>(result, status);
//	}
	
	//로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try {
            session.invalidate();
            return ResponseEntity.ok().body("Logout successful");  // 성공 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }	
	}
	
	
	
	
}
