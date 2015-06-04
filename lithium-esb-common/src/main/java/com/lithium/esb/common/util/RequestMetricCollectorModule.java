package com.lithium.esb.common.util;

import com.amazonaws.metrics.RequestMetricCollector;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestMetricCollectorModule {
	@Provides
	RequestMetricCollector provideRequestMetricCollector(RequestMetricCollector requestMetricCollector) {
		return requestMetricCollector;
	}
}
