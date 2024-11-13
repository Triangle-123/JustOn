package com.ssafy.mvc.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dto.User;

@Service
public interface UserService {

	User login(String id, String password);
	
	boolean signUp(User user);
	
	void modifyInfo(User user);
}
