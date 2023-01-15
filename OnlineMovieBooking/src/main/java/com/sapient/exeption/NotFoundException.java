package com.sapient.exeption;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	private String message;
	private String details;

	public NotFoundException( String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}


}
