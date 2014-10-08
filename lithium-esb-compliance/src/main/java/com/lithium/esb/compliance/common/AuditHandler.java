package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditHandler {
	private static final Logger log = LoggerFactory.getLogger(AuditHandler.class);

	public void performAuditProcessing(Exchange exchange) {
		log.info(">>> Auditing the message. " + exchange.toString());
	}
}
