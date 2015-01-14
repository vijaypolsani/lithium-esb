package com.lithium.esb.compliance.common;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.model.ActivityStreamV1;
import com.lithium.esb.compliance.util.JsonMessageParser;

/**
 * The processor converts the FileContent generated from the LIA Logs into the Standard ActivityStreams v1.0 model.
 */
public class ConvertFileContentToActivityStreamV1 implements Processor {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ConvertFileContentToActivityStreamV1.class);

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "fileName";

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		log.debug(">>> Inbound message header: " + exchange.getIn().getHeader(FILE_NAME));
		Message message = exchange.getIn();
		String fileName = (String) message.getHeader(FILE_NAME);
		String rawEvent = (String) message.getBody();
		//Call to transformation.
		ActivityStreamV1 activityStreamV1 = JsonMessageParser.parseIncomingsJsonStream(rawEvent);
		activityStreamV1.setFileSource(fileName);
		message.setHeader(FILE_NAME, fileName);
		message.setBody(activityStreamV1);
		exchange.setOut(message);
		log.debug(">>> Completed sending multiple fileNames: " + activityStreamV1.toString());
	}
}
