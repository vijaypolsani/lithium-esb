package com.lithium.esb.compliance.common;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleHdfsLogProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(HandleHdfsLogProcessor.class);

	public void process(Exchange exchange) throws Exception {
		log.info(">>> Inbound messages from integration: exchangeID: " + exchange.getExchangeId());
		Message message = exchange.getIn();
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) message.getBody();
		message.setMessageId(UUID.randomUUID().toString());
		message.setBody(byteArrayOutputStream.toByteArray());
		exchange.setOut(message);
		log.info(">>> Outbound messages from integration: " + exchange.toString());
	}
}