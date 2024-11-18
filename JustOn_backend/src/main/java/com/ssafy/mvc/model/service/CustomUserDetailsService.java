package com.ssafy.mvc.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDao userDao;
	
	public CustomUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
//		System.out.println("userId : " + userId);
		// DB에서 조회
        User userData = userDao.findByUserId(userId);
//        System.out.println(userData);
        if (userData != null) {
			
			//UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
	}
	
}
