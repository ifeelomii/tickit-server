package com.example.tickit.vms.response;

import java.util.ArrayList;
import java.util.List;

import com.example.tickit.domains.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UserInfoResponsVM {

	private String id;
	private String publicId;
	private String email;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String status;
	private String userRoles;

	public String getId() {
		return id;
	}

	public void setId(String userId) {
		this.id = userId;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public List<UserInfoResponsVM> toUserInfoResponseList(List<User> allUsers) {
		List<UserInfoResponsVM> responseList = new ArrayList<>();
		allUsers.stream().forEach(user -> {
			UserInfoResponsVM res = new UserInfoResponsVM();
			res.setId(user.getId().toString());
			res.setPublicId(user.getPublicId().toString());
			res.setEmail(user.getEmail());
			res.setUserName(user.getUserName());
			res.setFirstName(user.getFirstName());
			res.setLastName(user.getLastName());
			res.setStatus(user.getStatus().toString());
			res.setUserRoles(user.getUserRoles().toString());
			responseList.add(res);
		});
		return responseList;
	}

	public UserInfoResponsVM toUserInfoResponse(User user) {
		UserInfoResponsVM res = new UserInfoResponsVM();
		res.setId(user.getId().toString());
		res.setPublicId(user.getPublicId().toString());
		res.setEmail(user.getEmail());
		res.setUserName(user.getUserName());
		res.setFirstName(user.getFirstName());
		res.setLastName(user.getLastName());
		res.setStatus(user.getStatus().toString());
		res.setUserRoles(user.getUserRoles().toString());
		return res;
	}

}
