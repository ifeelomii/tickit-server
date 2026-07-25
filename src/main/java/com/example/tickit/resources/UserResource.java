package com.example.tickit.resources;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickit.services.UserService;
import com.example.tickit.vms.request.LoginVM;
import com.example.tickit.vms.request.UserCreationRequestVM;
import com.example.tickit.vms.response.LoginResponseVM;
import com.example.tickit.vms.response.UserCreationResponseVM;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping("/user/create-admin-user")
	public ResponseEntity<UserCreationResponseVM> createAdminUser(@RequestBody @Valid UserCreationRequestVM userVM)
			throws URISyntaxException {
		UserCreationResponseVM userResponse = userService.createUser(userVM);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}

	@PostMapping("/user/register-user")
	public ResponseEntity<UserCreationResponseVM> createUser(@RequestBody @Valid UserCreationRequestVM userVM)
			throws URISyntaxException {
		UserCreationResponseVM userResponse = userService.createUser(userVM);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}

	@PostMapping("/user/login")
	public ResponseEntity<LoginResponseVM> login(@RequestBody @Valid LoginVM loginVM) {
		return ResponseEntity.ok(userService.userLogin(loginVM));
	}
}
