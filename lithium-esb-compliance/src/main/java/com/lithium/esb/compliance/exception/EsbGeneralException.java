package com.lithium.esb.compliance.exception;

/**
 * The Class EsbGeneralException is a general ESB exception which is used a the base class for custom exceptions.
 */
public class EsbGeneralException extends RuntimeException {
	
	/** The message. */
	private String message = null;
	
	/** The error code. */
	private String errorCode = null;

	/**
	 * Instantiates a new esb general exception.
	 */
	public EsbGeneralException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new esb general exception.
	 *
	 * @param message the message
	 */
	public EsbGeneralException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new esb general exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public EsbGeneralException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new esb general exception.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 * @param cause the cause
	 */
	public EsbGeneralException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new esb general exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public EsbGeneralException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
