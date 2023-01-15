package com.sapient.exeption;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApplicationException> handleAllExceptions(Exception ex, WebRequest request) {
		ApplicationException exceptionResponse = new ApplicationException(ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ApplicationException>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(ApplicationException.class)
	public final ResponseEntity<ApplicationException> handleApplicationExceptions(Exception ex, WebRequest request) {
		ApplicationException exceptionResponse = new ApplicationException( ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ApplicationException>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<NotFoundException> handleUserNotFoundException(NotFoundException ex, WebRequest request) {
		NotFoundException exceptionResponse = new NotFoundException( ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<NotFoundException>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApplicationException exceptionResponse = new ApplicationException("Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}	

}
