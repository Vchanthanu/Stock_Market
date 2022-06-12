package com.cognizant.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User Already Exists")
public class UserAlreadyExistsException extends Exception {

	public UserAlreadyExistsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
