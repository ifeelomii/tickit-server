package com.example.tickit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tickit.domains.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}
