package com.lithium.esb.compliance.exception;

/**
 * Exception thrown when the LSW Event that is expected is not received in the pre-defined format.
 */
public class LswEventUnmarshallException extends RuntimeException {
	
	/** The message. */
	private String message = null;
	
	/** The error code. */
	private String errorCode = null;

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 */
	public LswEventUnmarshallException() {
	}

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 *
	 * @param message the message
	 */
	public LswEventUnmarshallException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 */
	public LswEventUnmarshallException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public LswEventUnmarshallException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 * @param cause the cause
	 */
	public LswEventUnmarshallException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new lsw event unmarshall exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public LswEventUnmarshallException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
