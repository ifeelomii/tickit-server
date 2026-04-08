package com.example.tickit.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.tickit.auditing.AuditableField;
import com.example.tickit.domains.AuditLog;

public class AuditDiffEngine {
	public static List<AuditLog> generateLogs(Object oldEntity, Object newEntity, String entityName, String entityId) {

		List<AuditLog> logs = new ArrayList<>();

		Field[] fields = newEntity.getClass().getDeclaredFields();

		for (Field field : fields) {

			if (!field.isAnnotationPresent(AuditableField.class)) {
				continue;
			}

			field.setAccessible(true);

			try {

				Object oldValue = oldEntity == null ? oldEntity : field.get(oldEntity);
				Object newValue = field.get(newEntity);

				if (!Objects.equals(oldValue, newValue)) {

					AuditLog log = new AuditLog();
					log.setEntityName(entityName);
					log.setEntityId(entityId);
					log.setFieldName(field.getName());
					log.setOldValue(valueToString(oldValue));
					log.setNewValue(valueToString(newValue));
					log.setModifiedAt(LocalDateTime.now());

					logs.add(log);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return logs;
	}

	public static List<AuditLog> generateLogsForCreate(Object entity, String entityName, String entityId) {

		List<AuditLog> logs = new ArrayList<>();

		Field[] fields = entity.getClass().getDeclaredFields();

		for (Field field : fields) {

			if (!field.isAnnotationPresent(AuditableField.class)) {
				continue;
			}

			field.setAccessible(true);

			try {

				Object value = field.get(entity);

				if (value != null) {

					AuditLog log = new AuditLog();
					log.setEntityName(entityName);
					log.setEntityId(entityId);
					log.setFieldName(field.getName());
					log.setOldValue(valueToString(null));
					log.setNewValue(valueToString(value));

					logs.add(log);

				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return logs;
	}

	private static String valueToString(Object value) {
		return value == null ? null : String.valueOf(value);
	}
}
