package com.ssafy.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	final private UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "/user/loginform";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		User tmp = userService.login(user.getId(), user.getPassword());
		if(tmp == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 아이디 입니다.");
		}
		session.setAttribute("loginUser", tmp.getName());
		return ResponseEntity.ok().build();
	}
	
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
