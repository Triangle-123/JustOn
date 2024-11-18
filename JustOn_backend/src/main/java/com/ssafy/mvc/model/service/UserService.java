package com.ssafy.mvc.model.service;

import java.util.Map;

import com.ssafy.mvc.model.dto.User;

public interface UserService {

	User userInfo(String userId);
	
//	User login(String id, String password);
	
	boolean signUp(User user);
	
	void modifyInfo(User user);
}