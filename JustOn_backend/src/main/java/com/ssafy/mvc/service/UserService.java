package com.ssafy.mvc.service;

import com.ssafy.mvc.model.dto.User;

public interface UserService {

	User login(String id, String password);
}
