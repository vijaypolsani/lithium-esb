package com.lithium.esb.compliance.lsw.api;

import java.util.List;

import com.lithium.streams.compliance.model.SecureEvent;

/**
 * The LSW Outbound interface exposes methods to consume messages and read the events as SecureEvents in a List
 * The messages that are read, will perform polling but should be configured with a WAIT on the SQS queue in order to avoid
 * CPU cycles for poll. SQS does not support listeners, the only way the listener functionality is built by making the wait
 * configuration in the Queue.
 */
public interface LswSqsOutbound extends LswQueue {
	
	/**
	 * Consume messages.
	 *
	 * @return the list
	 */
	public List<SecureEvent> consumeMessages();
}
