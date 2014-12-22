package com.lithium.esb.compliance.exception;

public class LswEventUnmarshallException extends RuntimeException {
	private String message = null;
	private String errorCode = null;

	public LswEventUnmarshallException() {
	}

	public LswEventUnmarshallException(String message) {
		super(message);
		this.message = message;
	}

	public LswEventUnmarshallException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public LswEventUnmarshallException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LswEventUnmarshallException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	public LswEventUnmarshallException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
