package com.lithium.esb.compliance.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitMessageProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(SplitMessageProcessor.class);
	private static final String CAMEL_SPLIT_COMPLETE = "CamelSplitComplete";
	private static final String CAMEL_SPLIT_SIZE = "CamelSplitSize";
	private static final String CAMEL_SPLIT_INDEX = "CamelSplitIndex";
	private static final List<String> splitMessages = new ArrayList<>();

	public void process(Exchange exchange) throws Exception {
		exchange.setOut(exchange.getIn());
		log.info(">>> Inbound messages from SplitMessageProcessor: exchangeID: " + exchange.getExchangeId());
		log.info(">>> Inbound messages from SplitMessageProcessor: " + exchange.toString());
		//exchange.setOut(exchange.getIn());

		if (exchange.getProperty(CAMEL_SPLIT_COMPLETE) != null) {
			//Keep this line of add above split complete check. Last item of the iteration comes with true.
			splitMessages.add(((String) exchange.getIn().getBody()));
			if ((boolean) exchange.getProperty(CAMEL_SPLIT_COMPLETE)) {
				exchange.getOut().setBody(splitMessages, List.class);
				log.info(">>> Split Processor CamelSplitIndex: " + exchange.getProperty(CAMEL_SPLIT_INDEX));
				log.info(">>> Split Processor CamelSplitSize: " + exchange.getProperty(CAMEL_SPLIT_SIZE));
				log.info(">>> Split Processor CamelSplitComplete: " + exchange.getProperty(CAMEL_SPLIT_COMPLETE));
				log.info(">>> Split Processor splitMessages: " + splitMessages);
				splitMessages.clear();
			}
		} else
			log.error("<<< The data from the slip operation does not contain CamelSplitComplete: "
					+ exchange.getProperty(CAMEL_SPLIT_COMPLETE));

	}
}