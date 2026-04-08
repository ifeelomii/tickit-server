package com.example.tickit.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.User;
import com.example.tickit.enums.UserStatuses;
import com.example.tickit.errors.InvalidLoginCredentialsException;
import com.example.tickit.errors.UserNotFoundException;
import com.example.tickit.repositories.UserRepository;
import com.example.tickit.vms.request.LoginVM;
import com.example.tickit.vms.request.UserRequestVM;
import com.example.tickit.vms.response.UserResponseVM;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	private static final String ENTITY_NAME = "User";

	@Transactional
	public User findOne(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found",
				"No User Available", ENTITY_NAME, HttpStatus.BAD_REQUEST));
	}

	@Transactional
	public UserResponseVM createUser(UserRequestVM userVM) {
		User user = userRepository.findByUserNameOrEmail(userVM.getUserName(), userVM.getEmail());
		if (user != null) {
			throw new UserNotFoundException("User Not Found", "No User Available", ENTITY_NAME, HttpStatus.BAD_REQUEST);
		}
		user = new User();
		user.setEmail(userVM.getEmail());
		user.setUserName(userVM.getUserName());
		user.setFirstName(userVM.getFirstName());
		user.setLastName(userVM.getLastName());
		user.setPassword(passwordEncoder.encode(userVM.getPassword()));
		user.setStatus(UserStatuses.ACTIVE);
		userRepository.saveAndFlush(user);
		return user.toUserResponseVM();

	}

	public UserResponseVM userLogin(LoginVM loginVM) {
		User user = userRepository.findByUserNameOrEmail(loginVM.getUserName(), loginVM.getEmail());
		if (user == null) {
			throw new UserNotFoundException("User Not Found", "No User Available", ENTITY_NAME, HttpStatus.BAD_REQUEST);
		}

		if (!passwordEncoder.matches(loginVM.getPassword(), user.getPassword())) {
			throw new InvalidLoginCredentialsException("Invalid login credentials.",
					"Invalid user password. Please try again.", ENTITY_NAME, HttpStatus.BAD_REQUEST);
		}
		return user.toUserResponseVM();
	}

	public User findReporter(Long reporterId) {
		return findOne(reporterId);
	}

	public User findAssignee(Long assigneeId) {
		return findOne(assigneeId);
	}
}
