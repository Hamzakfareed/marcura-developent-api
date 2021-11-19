package com.wlogsolutions.marcura.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> exceptionsHandler(Exception e,WebRequest request) {
		e.printStackTrace();
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}
}
