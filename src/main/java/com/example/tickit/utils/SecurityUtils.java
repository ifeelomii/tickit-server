package com.example.tickit.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.tickit.domains.User;
import com.example.tickit.errors.UserNotFoundException;
import com.example.tickit.repositories.UserRepository;

public class SecurityUtils {

	@Autowired
	private static UserRepository userRepository;

	public static User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UserNotFoundException("Unauthenticated", "No authenticated user found", "User",
					HttpStatus.UNAUTHORIZED);
		}

		String username = authentication.getName();

		return userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException("User Not Found",
				"Authenticated user no longer exists", "User", HttpStatus.NOT_FOUND));
	}

	public static String getCurrentUserName() {
		return getCurrentUser().getUserName();
	}

	public static Long getCurrentUserId() {
		return getCurrentUser().getId();
	}

	public static boolean hasRole(String role) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
	}
}
