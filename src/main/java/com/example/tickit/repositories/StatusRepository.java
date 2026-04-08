package com.example.tickit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tickit.domains.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
