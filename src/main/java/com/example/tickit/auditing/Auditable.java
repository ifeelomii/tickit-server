package com.example.tickit.auditing;

import java.util.List;

import com.example.tickit.domains.AuditLog;

public interface Auditable {
	String getEntityId();

	List<AuditLog> getAuditLogs();
}
