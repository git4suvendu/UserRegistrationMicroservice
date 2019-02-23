package com.microservice.user.model;

public class UserRecord {
	private String UserId;
	private String UserFirstName;
	private String UserLastName;
	private String UserPassword;

	public String getUserId() {
		return UserId;
	}
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public String getUserFirstName() {
		return UserFirstName;
	}
	public void setUserFirstName(String UserFirstName) {
		this.UserFirstName = UserFirstName;
	}

	public String getUserLastName() {
		return UserLastName;
	}
	public void setUserLastName(String UserLastName) {
		this.UserLastName = UserLastName;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
}


