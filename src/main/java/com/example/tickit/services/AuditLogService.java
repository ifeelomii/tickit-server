package com.example.tickit.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.AuditLog;
import com.example.tickit.repositories.AuditLogRepository;

@Service
public class AuditLogService {

	private final AuditLogRepository auditLogRepository;

	public AuditLogService(AuditLogRepository auditLogRepository) {
		super();
		this.auditLogRepository = auditLogRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveAuditLogs(List<AuditLog> logs) {

		List<AuditLog> entities = new ArrayList<>();

		for (AuditLog log : logs) {

			AuditLog entity = new AuditLog();

			entity.setEntityName(log.getEntityName());
			entity.setEntityId(log.getEntityId());
			entity.setFieldName(log.getFieldName());
			entity.setOldValue(log.getOldValue());
			entity.setNewValue(log.getNewValue());
			entity.setModifiedBy(log.getModifiedBy());
			entity.setModifiedAt(LocalDateTime.now());

			entities.add(entity);
		}

		auditLogRepository.saveAll(entities);
		auditLogRepository.flush();
	}

	public List<AuditLog> getAuditLogs(String entityName, String entityId) {
		return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId);
	}
}
