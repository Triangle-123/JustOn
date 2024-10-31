package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dto.User;

public interface UserService {

	User login(String id, String password);
	
	boolean signUp(User user);
	
	void modifyInfo(User user);
}
