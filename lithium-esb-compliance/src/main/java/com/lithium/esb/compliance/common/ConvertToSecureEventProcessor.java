package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.streams.compliance.model.Payload;

public class ConvertToSecureEventProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(ConvertToSecureEventProcessor.class);

	public void process(Exchange exchange) throws Exception {
		String event = (String) exchange.getIn().getBody();
		exchange.setOut(exchange.getIn());
		exchange.getOut().setBody(new Payload(event.getBytes()));
	}
}