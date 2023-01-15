package com.sapient.exeption;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	private Date timestamp;
	private String message;
	private String details;

	public ApplicationException(String message, String details) {
		super();
		this.timestamp = new Date();
		this.message = message;
		this.details = details;
	}


}
