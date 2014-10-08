package com.lithium.esb.compliance.exception;

public class EsbGeneralException extends RuntimeException {
	private String message = null;
	private String errorCode = null;

	public EsbGeneralException() {
		// TODO Auto-generated constructor stub
	}

	public EsbGeneralException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EsbGeneralException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EsbGeneralException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	public EsbGeneralException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
