package com.lithium.esb.compliance.exception;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GlobalExceptionProcessor as the Global Exception Handler to process retries
 */
public class GlobalExceptionProcessor extends ExceptionHandler {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionProcessor.class);

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.exception.ExceptionHandler#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		try {
			log.error("In ExceptionHandler...{}", exchange.getIn().getBody());

			Object exception = null;

			exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
			log.error("Exception is {}", exception);

			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		} catch (Exception e) {
			log.error("Exception while processing error. ", e);
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		}
	}

}
