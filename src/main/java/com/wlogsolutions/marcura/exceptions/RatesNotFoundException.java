package com.wlogsolutions.marcura.exceptions;

public class RatesNotFoundException extends Exception {
	
	private String message;
	
	public RatesNotFoundException(String message) {
		this.message = message;
	}

}
