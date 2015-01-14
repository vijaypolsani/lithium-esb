package com.lithium.esb.compliance.lsw.api;

/**
 * The LSW inbound interface exposes the methods for sending to a sample SQS message store.
 */
public interface LswSqsInbound extends LswQueue{
	
	/**
	 * Send sqs message.
	 *
	 * @param message the message
	 */
	public void sendSqsMessage(String message);
	
	/**
	 * Send sample sqs message.
	 */
	public void sendSampleSqsMessage();
}
