package com.lithium.esb.compliance.lsw;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.streams.compliance.model.SecureEvent;

public class ConvertToBytesProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(ConvertToBytesProcessor.class);

	public void process(Exchange exchange) throws Exception {
		SecureEvent event = (SecureEvent) exchange.getIn().getBody();
		exchange.setOut(exchange.getIn());
		exchange.getOut().setBody(new String (event.getMessage()));
	}
}