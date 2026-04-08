package com.example.tickit.event.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.tickit.events.AuditEvent;
import com.example.tickit.services.AuditLogService;

@Component
public class AuditLogEventListener {

	@Autowired
	private AuditLogService auditLogService;

	public AuditLogEventListener() {
		super();
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleAuditEvent(AuditEvent event) {
		System.out.println("Audit logs count in handleAuditEvent(): " + event.getAuditLogs().size());
		auditLogService.saveAuditLogs(event.getAuditLogs());
	}
}
