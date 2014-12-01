package com.lithium.esb.compliance.exception;

public class HdfsFileNotFoundException extends RuntimeException {
	private String message = null;
	private String errorCode = null;

	public HdfsFileNotFoundException() {
	}

	public HdfsFileNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public HdfsFileNotFoundException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public HdfsFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public HdfsFileNotFoundException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	public HdfsFileNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
