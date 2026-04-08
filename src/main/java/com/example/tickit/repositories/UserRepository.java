package com.example.tickit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tickit.domains.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameOrEmail(String username, String email);

}
