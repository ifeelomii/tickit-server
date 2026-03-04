package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.response.vms.ErrorResponseVM;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponseVM> handleEntityNotFound(EntityNotFoundException ex){
		ErrorResponseVM response = new ErrorResponseVM(ex.getMessage(), 404);
		return new ResponseEntity<ErrorResponseVM>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponseVM> handleInvalidOrderStateException(InvalidOrderStateException ex){
		ErrorResponseVM response = new ErrorResponseVM(ex.getMessage(), 400);
		return new ResponseEntity<ErrorResponseVM>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponseVM> handleGenericException(Exception ex){
		ErrorResponseVM response = new ErrorResponseVM("Someting went wrong...", 500);
		return new ResponseEntity<ErrorResponseVM>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
