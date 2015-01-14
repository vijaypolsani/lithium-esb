package com.lithium.esb.compliance.exception;

/**
 * The Class Exception throw when a HDFS file that is listed in the Storage is not found on subsequent querying from
 * the HDFS files list. Either the file is invalid or moved needs to be verified and necessary compensation action
 * can be taken.
 */
public class HdfsFileNotFoundException extends EsbGeneralException {
	
	/** The message. */
	private String message = null;
	
	/** The error code. */
	private String errorCode = null;

	/**
	 * Instantiates a new hdfs file not found exception.
	 */
	public HdfsFileNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new hdfs file not found exception.
	 *
	 * @param message the message
	 */
	public HdfsFileNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Instantiates a new hdfs file not found exception.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 */
	public HdfsFileNotFoundException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * Instantiates a new hdfs file not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public HdfsFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new hdfs file not found exception.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 * @param cause the cause
	 */
	public HdfsFileNotFoundException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new hdfs file not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public HdfsFileNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
