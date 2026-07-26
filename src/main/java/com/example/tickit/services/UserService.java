package com.example.tickit.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.User;
import com.example.tickit.enums.UserRoles;
import com.example.tickit.enums.UserStatuses;
import com.example.tickit.errors.InvalidRequestException;
import com.example.tickit.errors.UserNotFoundException;
import com.example.tickit.repositories.UserRepository;
import com.example.tickit.security.JWTService;
import com.example.tickit.utils.SecurityUtils;
import com.example.tickit.utils.StringUtils;
import com.example.tickit.vms.request.LoginVM;
import com.example.tickit.vms.request.UserCreationRequestVM;
import com.example.tickit.vms.response.LoginResponseVM;
import com.example.tickit.vms.response.UserCreationResponseVM;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JWTService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	private static final String ENTITY_NAME = "User";

	@Transactional
	public User findOne(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found",
				"No User Available", ENTITY_NAME, HttpStatus.BAD_REQUEST));
	}

	@Transactional
	public UserCreationResponseVM createUser(UserCreationRequestVM userVM) {

		if (userRepository.existsByEmail(userVM.getEmail())) {
			throw new UserNotFoundException("Email Already Exists", "Email already exists for another user",
					ENTITY_NAME, HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByUserName(userVM.getUserName())) {
			throw new UserNotFoundException("Username Already Exists", "Username already exists for another user",
					ENTITY_NAME, HttpStatus.BAD_REQUEST);
		}

		User user = new User();

		user.setEmail(userVM.getEmail());
		user.setUserName(userVM.getUserName());
		user.setFirstName(userVM.getFirstName());
		user.setLastName(userVM.getLastName());
		user.setPassword(passwordEncoder.encode(userVM.getPassword()));
		user.setStatus(UserStatuses.ACTIVE);
		UserRoles userRole;
		try {
			userRole = UserRoles.valueOf(userVM.getUserRole());
			log.info("User role: {}.", userRole);
		} catch (IllegalArgumentException ex) {
			throw new InvalidRequestException("Invalid Role", "Unsupported user role", ENTITY_NAME,
					HttpStatus.BAD_REQUEST);
		}

		if (UserRoles.ADMIN.equals(userRole) && !SecurityUtils.hasRole(UserRoles.ADMIN.toString())) {
			throw new InvalidRequestException("Invalid User Role", "Only Admin user can create admin user", ENTITY_NAME,
					HttpStatus.BAD_REQUEST);
		} else {
			user.setUserRoles(userRole);
		}
		userRepository.saveAndFlush(user);

		return user.toUserCreationResponseVM();

	}

	@Transactional(readOnly = true)
	public LoginResponseVM userLogin(LoginVM loginVM) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginVM.getUsernameOrEmail(), loginVM.getPassword()));

		User user = findUserByEmailOrUsername(loginVM.getUsernameOrEmail())
				.orElseThrow(() -> new UserNotFoundException("User Not Found",
						"User not found for provided credentials", ENTITY_NAME, HttpStatus.BAD_REQUEST));

		String token = jwtService.generateToken(user);
		LoginResponseVM loginResponse = user.toLoginResponseVM();
		loginResponse.setJwtToken(token);
		return loginResponse;
	}

	private Optional<User> findUserByEmailOrUsername(String userNameOrEmail) {
		return (userNameOrEmail.matches(StringUtils.EMAIL_REGEX) && userRepository.existsByEmail(userNameOrEmail))
				? userRepository.findByEmail(userNameOrEmail)
				: userRepository.findByUserName(userNameOrEmail);
	}

	public User findReporter(Long reporterId) {
		return findOne(reporterId);
	}

	public User findAssignee(Long assigneeId) {
		return findOne(assigneeId);
	}
}
