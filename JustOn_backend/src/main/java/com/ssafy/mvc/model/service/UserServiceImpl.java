package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDao = userDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public User userInfo(String userId) {
		return userDao.selectUser(userId);
	}
	
	
//	@Override
//	public User login(String id, String password) {
//		Map<String, String> info = new HashMap<>();
//		info.put("id", id);
//		info.put("password", password);
//		User tmp = userDao.selectUser(info);
//		return tmp;
//	}

	@Override
	public boolean signUp(User user) {
		return userDao.insertUser(user) == 1;
	}

	@Override
	public void modifyInfo(User user) {
		userDao.updateUser(user);
		
	}

	@Override
	public boolean checkExisted(String userId) {
		String exist = userDao.existsById(userId);
		if(exist != null) {
			return true;
		}
		return false;
	}
	
}