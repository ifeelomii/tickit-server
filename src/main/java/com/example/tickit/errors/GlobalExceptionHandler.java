package com.example.tickit.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handle(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.status(500).body(ex.getMessage());
	}

	@ExceptionHandler(IssueNotFoundException.class)
	public ResponseEntity<GlobalExceptionResponseObject> handleIssueNotFoundException(IssueNotFoundException ex) {
		GlobalExceptionResponseObject exObj = new GlobalExceptionResponseObject(ex.getTitle(), ex.getMessage(),
				ex.getEntityName(), ex.getStatus().value());
		return new ResponseEntity<>(exObj, ex.getStatus());
	}

	@ExceptionHandler(StatusNotFoundException.class)
	public ResponseEntity<GlobalExceptionResponseObject> handleStatusNotFoundException(StatusNotFoundException ex) {
		GlobalExceptionResponseObject exObj = new GlobalExceptionResponseObject(ex.getTitle(), ex.getMessage(),
				ex.getEntityName(), ex.getStatus().value());
		return new ResponseEntity<>(exObj, ex.getStatus());
	}

}
