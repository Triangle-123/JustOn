package com.ssafy.mvc.controller;


import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.jwt.JWTUtil;
import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserProfile;
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
	
	
	@GetMapping("/userInfo")
	public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			User user = userService.userInfo(userDetails.getUsername());
			if(user != null) {
				return ResponseEntity.ok(user);
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{userId}")	
	public ResponseEntity<?> checkExisted(@PathVariable("userId") String userId) {
		try {
			if(userService.checkExisted(userId)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
			}
			return ResponseEntity.ok("사용 가능한 아이디입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("서버에 연결할 수 없습니다. 다시 시도해주세요.");
		}
	}
	
//	// 사용자 로그인
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpSession session) {
//		
//		System.out.println(loginDto);
//		
////		Authentication authentication = authenticationManager.authenticate(
////	                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
////		System.out.println(authentication);
//		//		User tmp = userService.login(user.getUserId(), user.getPassword());
////		System.out.println(user);
////		if(tmp == null) {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 아이디 입니다.");
////		}
////		user.setPassword(null);
////		session.setAttribute("loginUser", tmp);
//		return ResponseEntity.ok().build();
//	}
	
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
	
	@PutMapping("/signup")
	public ResponseEntity<String> updateUserInfo(@RequestBody User user, @AuthenticationPrincipal CustomUserDetails userDetails) {
		String userId = userDetails.getUsername();
		try {
			if(userService.modifyInfo(user)) {
				return ResponseEntity.ok("정보 수정 완료");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("수정 실패");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("수정과정에서 문제가 발생하였습니다.");	
		}
	}
	
	
	//====================================================================================
	// 유저 프로필 이미지 관련
	
	// 프로필 이미지 업로드
	@PostMapping("/profile/upload")
    public ResponseEntity<String> uploadProfile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal CustomUserDetails userDetails) {
		String userId = userDetails.getUsername();
		try {
            userService.uploadProfile(file, userId);
            return ResponseEntity.ok("프로필 이미지 업로드 성공!");
        } catch (IOException e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        }
    }

    // 프로필 이미지 조회
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfile profile = userService.getUserProfile(userDetails.getUsername());
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();	
        }
    }
    
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	boolean result = userService.deleteUserProfile(userDetails.getUsername());
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body("Deleted Suscess");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted Failed");
		}
    }
    
	@PutMapping("/color")
	public ResponseEntity<?> changeColor(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map<String, String> map) {
		try {
			String color = map.get("color");
			System.out.println(color);
			if(userService.changeColor(color, userDetails.getUsername())) {
				return ResponseEntity.ok("배경색이 변경되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("배경색 변경에 실패하였습니다.");
		}
	}
	
}
