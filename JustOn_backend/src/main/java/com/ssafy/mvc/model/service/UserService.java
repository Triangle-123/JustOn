package com.ssafy.mvc.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserProfile;

public interface UserService {

	User userInfo(String userId);
	
//	User login(String id, String password);
	
	boolean signUp(User user);
	
	boolean modifyInfo(User user);
	
	boolean checkExisted(String userId);

	// 유저 프로필 이미지 관련 
	// 프로필 이미지 업로드
	boolean uploadProfile(MultipartFile file, String userId) throws IOException;
	
	UserProfile getUserProfile(String userId);
	
	boolean deleteUserProfile(String userId);

	boolean changeColor(String color, String userId);
	
}