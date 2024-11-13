package com.ssafy.mvc.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dto.User;

@Service
public interface UserService {
	
	boolean signUp(User user);
	
	User login(String id, String password);
}
