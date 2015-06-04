package com.lithium.esb.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.metrics.RequestMetricCollector;

public class RequestMetrics extends RequestMetricCollector{
	private static final Logger log = LoggerFactory.getLogger(RequestMetricCollector.class);
	@Override
	public void collectMetrics(Request<?> request, Response<?> response) {
		log.debug("Time taken:"+request.getAWSRequestMetrics().getTimingInfo());
		
	}

}
