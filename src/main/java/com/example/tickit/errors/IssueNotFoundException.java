package com.example.tickit.errors;

import org.springframework.http.HttpStatus;

public class IssueNotFoundException extends RuntimeException {

	private final String title;
	private final String entityName;
	private final HttpStatus status;
	private static final long serialVersionUID = 1L;

	public IssueNotFoundException(String title, String message, String entityName, HttpStatus status) {
		super(message);
		this.title = title;
		this.entityName = entityName;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public String getEntityName() {
		return entityName;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
