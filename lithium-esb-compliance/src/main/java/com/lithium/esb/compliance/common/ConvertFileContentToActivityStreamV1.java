package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.model.ActivityStreamV1;
import com.lithium.esb.compliance.util.JsonMessageParser;

public class ConvertFileContentToActivityStreamV1 implements Processor {
	private static final Logger log = LoggerFactory.getLogger(ConvertFileContentToActivityStreamV1.class);
	private static final String FILE_NAME = "fileName";

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info(">>> Inbound message header: " + exchange.getIn().getHeader(FILE_NAME));
		log.info(">>> Inbound message : " + exchange);
		Message message = exchange.getIn();
		String fileName = (String) message.getHeader(FILE_NAME);
		String rawEvent = (String) message.getBody();
		//Call to transformation.
		ActivityStreamV1 activityStreamV1 = JsonMessageParser.parseIncomingsJsonStream(rawEvent);
		activityStreamV1.setFileSource(fileName);
		message.setHeader(FILE_NAME, fileName);
		message.setBody(activityStreamV1);
		exchange.setOut(message);
		log.info(">>> Completed sending multiple fileNames: " + activityStreamV1.toString());
	}
}
