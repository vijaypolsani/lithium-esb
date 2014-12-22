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

public class ConvertFileContentToLswEvent implements Processor {
	private static final Logger log = LoggerFactory.getLogger(ConvertFileContentToLswEvent.class);

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
