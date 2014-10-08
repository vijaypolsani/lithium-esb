package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLogProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(CommonLogProcessor.class);

	public void process(Exchange exchange) throws Exception {
		exchange.setOut(exchange.getIn());
		log.info(">>> Inbound messages from CommonLogProcessor: exchangeID: " + exchange.getExchangeId());
		log.info(">>> Inbound messages from CommonLogProcessor: " + exchange.getProperties());
	}
}