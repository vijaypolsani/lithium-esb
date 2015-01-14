package com.lithium.esb.compliance.lsw;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.streams.compliance.model.SecureEvent;

/**
 * The ConvertToBytesProcessor gets the body of the message and then converts into SecureEvent type
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
		exchange.getOut().setBody(new String (event.getMessage()));
	}
}