package com.ssafy.mvc.model.dao;

import java.util.Map;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserProfile;

public interface UserDao {
	// 회원가입
	int insertUser(User user);
	
	// 회원가입 전 id가 이미 있는지 확인
	String existsById(String userId);
	
	// userid로 회원을 조회
	User findByUserId(String userId);
	
	// 로그인 검사
	User selectUser(String userId);
	
	// 회원정보 수정
	int updateUser(User user);
	
	// 프로필 이미지 업로드
	int insertUserProfile(UserProfile userProfile);
	
	// 프로필 이미지 삭제
	int deleteUserProfile(String id);
	
	// 프로필 이미지 조회
	UserProfile getUserProfile(String id);
	
	
	
}
