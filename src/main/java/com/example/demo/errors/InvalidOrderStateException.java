package com.example.demo.errors;

public class InvalidOrderStateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOrderStateException(String message) {
	      super(message);
	}
}
