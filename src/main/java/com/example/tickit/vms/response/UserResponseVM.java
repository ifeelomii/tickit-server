package com.example.tickit.vms.response;

import java.util.UUID;

import com.example.tickit.enums.UserStatuses;

public class UserResponseVM {
	private Long id;
	private UUID publicId = UUID.randomUUID();
	private String email;
	private String userName;
	private String firstName;
	private String lastName;
	private UserStatuses status;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getPublicId() {
		return publicId;
	}

	public void setPublicId(UUID publicId) {
		this.publicId = publicId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatuses getStatus() {
		return status;
	}

	public void setStatus(UserStatuses status) {
		this.status = status;
	}

}
