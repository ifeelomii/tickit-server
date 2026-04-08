package com.example.tickit.errors;

public class GlobalExceptionResponseObject {
	private String title;
	private String message;
	private String entity;
	private int status;

	public GlobalExceptionResponseObject(String title, String message, String entity, int status) {
		super();
		this.title = title;
		this.message = message;
		this.entity = entity;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
