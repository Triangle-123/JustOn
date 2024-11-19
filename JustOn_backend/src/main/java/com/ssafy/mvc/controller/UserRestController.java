package com.ssafy.mvc.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.jwt.JWTUtil;
import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api-user")
@CrossOrigin
public class UserRestController {
	
	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	public UserRestController(UserService userService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
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
		user.setPassword(null);
		session.setAttribute("loginUser", tmp);
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
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			System.out.println(user);
			if(userService.signUp(user)) {
				return ResponseEntity.ok("회원가입이 완료되었습니다.");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입에 실패하였습니다.");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("회원가입 과정에서 문제가 발생하였습니다.");	
		}
	}
	
	
	
}
