package com.example.tickit.resources;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickit.services.UserService;
import com.example.tickit.vms.request.LoginVM;
import com.example.tickit.vms.request.UserRequestVM;
import com.example.tickit.vms.response.UserResponseVM;

@RestController
@RequestMapping("/api")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping("/user/create-user")
	public ResponseEntity<UserResponseVM> createUser(@RequestBody UserRequestVM userVM) throws URISyntaxException {
		UserResponseVM userResponse = userService.createUser(userVM);
		return ResponseEntity.created(new URI("/api/user/create-user")).body(userResponse);
	}

	@PostMapping("/user/login")
	public ResponseEntity<UserResponseVM> login(@RequestBody LoginVM loginVM) {
		return ResponseEntity.ok(userService.userLogin(loginVM));
	}
}
