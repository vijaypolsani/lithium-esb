package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AuditHandler is useful in logging the messages while passing thought the Camel Processor.
 */
public class AuditHandler {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(AuditHandler.class);

	/**
	 * Perform audit processing.
	 *
	 * @param exchange the exchange
	 */
	public void performAuditProcessing(Exchange exchange) {
		log.info(">>> Auditing the message. " + exchange.toString());
	}
}
