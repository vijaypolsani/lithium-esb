package com.lithium.esb.compliance.exception;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler implements Processor {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	public void process(Exchange exchange) throws Exception {

		log.error("In ExceptionHandler...{}", exchange.getIn().getBody());

	}

}