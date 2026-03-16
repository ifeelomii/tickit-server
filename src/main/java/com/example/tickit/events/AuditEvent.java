package com.example.tickit.events;

import java.util.List;

import com.example.tickit.domains.AuditLog;

public class AuditEvent {
	private String entityName;
	private String entityId;
	private List<AuditLog> auditLogs;

	public AuditEvent() {
		super();
	}

	public AuditEvent(String entityName, String entityId, List<AuditLog> auditLogs) {
		super();
		this.entityName = entityName;
		this.entityId = entityId;
		this.auditLogs = auditLogs;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<AuditLog> getAuditLogs() {
		return auditLogs;
	}

	public void setAuditLogs(List<AuditLog> auditLogs) {
		this.auditLogs = auditLogs;
	}
}
