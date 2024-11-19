package com.ssafy.mvc.model.dto;

public class User {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String gender;
	private int postCode;
	private String address;
	private String extraAddress;
	private String detailAddress;	
	private String nickname;
	private String birth;
	private String role;
	
	private UserProfile userProfile;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String password, String name, String email, String gender, int postCode, String address,
			String extraAddress, String detailAddress, String nickname, String birth, String role,
			UserProfile userProfile) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.postCode = postCode;
		this.address = address;
		this.extraAddress = extraAddress;
		this.detailAddress = detailAddress;
		this.nickname = nickname;
		this.birth = birth;
		this.role = role;
		this.userProfile = userProfile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExtraAddress() {
		return extraAddress;
	}

	public void setExtraAddress(String extraAddress) {
		this.extraAddress = extraAddress;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + ", gender="
				+ gender + ", postCode=" + postCode + ", address=" + address + ", extraAddress=" + extraAddress
				+ ", detailAddress=" + detailAddress + ", nickname=" + nickname + ", birth=" + birth + ", role=" + role
				+ ", userProfile=" + userProfile + "]";
	}
	
	
}