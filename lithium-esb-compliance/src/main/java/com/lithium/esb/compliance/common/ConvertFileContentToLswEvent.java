package com.lithium.esb.compliance.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.model.LswEvent;
import com.lithium.esb.compliance.util.UnmarshallToLswEvent;
import com.lithium.streams.compliance.model.SecureEvent;

/**
 * The Class ConvertFileContentToLswEvent converts LSW SQS event to Event Formatted to display.
 */
public class ConvertFileContentToLswEvent implements Processor {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ConvertFileContentToLswEvent.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		@SuppressWarnings("unchecked")
		List<SecureEvent> secureEvents = (List<SecureEvent>) message.getBody();
		//Call to transformation.
		List<LswEvent> lswEvents = new ArrayList<>();
		for (SecureEvent secureEvent : secureEvents) {
			lswEvents.add(UnmarshallToLswEvent.parseIncomingsJsonStreamToObject(secureEvent.getMessage()));
		}
		log.info(">>> Number of messages received and transformed: " + lswEvents.size());
		message.setBody(lswEvents);
		exchange.setOut(message);
	}
}
