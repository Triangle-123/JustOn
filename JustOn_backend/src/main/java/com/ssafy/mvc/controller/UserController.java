package com.ssafy.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			if(userService.signUp(user)) {
				return ResponseEntity.ok("회원가입이 완료되었습니다.");
			}
			return ResponseEntity.internalServerError().body("회원가입에 실패하였습니다.");			
		} catch(Exception e) {
			return ResponseEntity.internalServerError().body("회원가입에 실패하였습니다.");	
		}
	}
}
