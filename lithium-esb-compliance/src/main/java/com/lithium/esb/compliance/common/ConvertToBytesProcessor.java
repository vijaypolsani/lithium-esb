package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.streams.compliance.model.SecureEvent;

/**
 * Content from the messaging system (Kafka) is read in the bytes format is converted to String for 
 * splitting and other processing.
 */
public class ConvertToBytesProcessor implements Processor {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ConvertToBytesProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		SecureEvent event = (SecureEvent) exchange.getIn().getBody();
		exchange.setOut(exchange.getIn());
		exchange.getOut().setBody(new String(event.getMessage()));
	}
}