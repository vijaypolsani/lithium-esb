package com.lithium.esb.compliance.exception;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ExceptionHandler for handling exceptions in routes.
 */
public class ExceptionHandler implements Processor {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {

		log.error("In ExceptionHandler...{}", exchange.getIn().getBody());

	}

}