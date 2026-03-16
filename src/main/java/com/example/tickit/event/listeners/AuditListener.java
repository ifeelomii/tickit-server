package com.example.tickit.event.listeners;

import java.util.List;

import com.example.tickit.auditing.Auditable;
import com.example.tickit.auditing.SnapshotAware;
import com.example.tickit.context.SpringContext;
import com.example.tickit.domains.AuditLog;
import com.example.tickit.events.AuditEvent;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PreUpdate;

public class AuditListener {

	@PostLoad
	public void postLoad(Object entity) {

		if (entity instanceof SnapshotAware snapshotAware) {
			snapshotAware.captureSnapshot();
		}
	}

	@PreUpdate
	public void preUpdate(Object entity) {

		if (entity instanceof SnapshotAware snapshotAware) {
			snapshotAware.prepareAuditLogs();
			if (entity instanceof Auditable auditable) {
				List<AuditLog> logs = auditable.getAuditLogs();
				if (!logs.isEmpty()) {
					SpringContext.getPublisher().publishEvent(
							new AuditEvent(entity.getClass().getSimpleName(), auditable.getEntityId(), logs));
				}
			}
		}
	}
}