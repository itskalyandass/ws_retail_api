package com.atgcorp.retail.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -6019013409030071319L;
	
	public BadRequestException() {
		super();
	}

	public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public BadRequestException(String message) {
        super(message);
    }
	
	public BadRequestException(Throwable cause) {
        super(cause);
    }
}
