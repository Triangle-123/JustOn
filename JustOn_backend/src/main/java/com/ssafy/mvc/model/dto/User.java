package com.ssafy.mvc.model.dto;

public class User {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String gender;
	private String address;
	private String nickname;
	private String birth;
	
	private UserProfile userProfile;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String id, String password, String name, String email, String gender, String address, String nickname,
			String birth, UserProfile userProfile) {
		super();
		this.userId = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.nickname = nickname;
		this.birth = birth;
		this.userProfile = userProfile;
	}

	public String getId() {
		return userId;
	}

	public void setId(String id) {
		this.userId = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + ", gender="
				+ gender + ", address=" + address + ", nickname=" + nickname + ", birth=" + birth + ", userProfile="
				+ userProfile + "]";
	}
	
	
}