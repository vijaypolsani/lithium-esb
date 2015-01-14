package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.streams.compliance.model.Payload;

/**
 * The the body of the processor from byte[] to SecureEvent
 */
public class ConvertToSecureEventProcessor implements Processor {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ConvertToSecureEventProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		String event = (String) exchange.getIn().getBody();
		exchange.setOut(exchange.getIn());
		exchange.getOut().setBody(new Payload(event.getBytes()));
	}
}