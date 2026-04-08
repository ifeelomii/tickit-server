package com.example.tickit.domains;

import java.util.UUID;

import com.example.tickit.enums.UserStatuses;
import com.example.tickit.vms.response.UserResponseVM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "public_id", nullable = false, unique = true, updatable = false)
	private UUID publicId = UUID.randomUUID();

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "user_name", unique = true, nullable = false)
	private String userName;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	private UserStatuses status;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatuses getStatus() {
		return status;
	}

	public void setStatus(UserStatuses status) {
		this.status = status;
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

	public UserResponseVM toUserResponseVM() {
		UserResponseVM response = new UserResponseVM();
		response.setId(this.getId());
		response.setUserName(this.getUserName());
		response.setFirstName(this.getFirstName());
		response.setLastName(this.getLastName());
		response.setEmail(this.getEmail());
		response.setPublicId(this.getPublicId());
		response.setStatus(this.getStatus());
		return response;
	}
}