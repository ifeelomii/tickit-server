package com.example.tickit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tickit.domains.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

	List<AuditLog> findByEntityNameAndEntityId(String entityName, String entityId);
}