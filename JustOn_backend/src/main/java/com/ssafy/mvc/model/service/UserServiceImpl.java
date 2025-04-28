package com.ssafy.mvc.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserProfile;

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
		String exist = userDao.existsById(user.getUserId());
		System.out.println("isExist : " + exist);
		if(exist != null) {
			System.out.println("id 중복");
			return false;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		
		System.out.println(user);
		return userDao.insertUser(user) == 1;
	}
	
	@Override
	public boolean modifyInfo(User user) {
		return userDao.updateUser(user) >= 1;
	}
	
	@Override
	public boolean checkExisted(String userId) {
		String exist = userDao.existsById(userId);
		if(exist != null) {
			return true;
		}
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// 이미지 프로필 파일 관련
    @Value("${file.upload-dir}")
    private String uploadDir;
    
	@Override
    public boolean uploadProfile(MultipartFile file, String userId) throws IOException {
        // 기존 파일 삭제
    	int result1 = userDao.deleteUserProfile(userId);

        // 파일 저장 경로 생성
        String oriName = file.getOriginalFilename();
        String systemName = UUID.randomUUID().toString() + "_" + oriName;
        String filePath = uploadDir + File.separator + systemName;

        // 파일 저장
        File dest = new File(filePath);
        dest.mkdirs();
        File f = new File(dest, systemName);
        file.transferTo(f);

        // DB 저장
//        UserProfile userProfile = new UserProfile();
//        userProfile.setFilePath(filePath);
//        userProfile.setOriName(oriName);
//        userProfile.setSystemName(systemName);
//        userProfile.setUserId(userId);
        
        // DB 저장
        UserProfile userProfile = new UserProfile();
        userProfile.setFilePath("/uploads/profile_images/" + systemName);  // URL 경로로 설정
        userProfile.setOriName(oriName);
        userProfile.setSystemName(systemName);
        userProfile.setUserId(userId);
        
        
        int result2 = userDao.insertUserProfile(userProfile);
        return result1 >= 1 && result2 >= 1;
    }

	@Override
    public UserProfile getUserProfile(String userId) {
        return userDao.getUserProfile(userId);
    }

	@Override
	public boolean deleteUserProfile(String userId) {
		int result1 = userDao.deleteUserProfile(userId);
		return result1 >= 1;
	}

	@Override
	public boolean changeColor(String color, String userId) {
		return userDao.updateUserColor(color, userId) >= 1;
	}
	
	
	
}