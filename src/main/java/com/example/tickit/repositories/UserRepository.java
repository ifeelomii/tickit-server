package com.example.tickit.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tickit.domains.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Page<User> findAll(Specification<User> spec, Pageable pageable);

	Optional<User> findByUserNameOrEmail(String userName, String email);

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);

}
