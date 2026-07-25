package com.example.tickit.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.tickit.domains.User;
import com.example.tickit.enums.UserRoles;
import com.example.tickit.enums.UserStatuses;
import com.example.tickit.repositories.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

	public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {

		if (userRepository.count() > 0) {
			log.info("Seed data script skipped...");
			return;
		}

		User admin = new User();
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setUserName("admin.user");
		admin.setEmail("admin@tickit.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setUserRoles(UserRoles.ADMIN);
		admin.setStatus(UserStatuses.ACTIVE);
		userRepository.save(admin);

		log.info("Seed data script executed...");
	}
}