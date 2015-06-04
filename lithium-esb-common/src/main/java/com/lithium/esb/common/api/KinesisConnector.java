package com.lithium.esb.common.api;

import com.amazonaws.regions.Region;

public interface KinesisConnector {
    static final String AMAZON = "Amazon";
    static final String AWS = "AWS";
    static final boolean LOGGING_AWS_REQUEST_METRIC = true;
    
	public void setEndpoint(String endpoint) throws java.lang.IllegalArgumentException;
	public void setRegion(Region region) throws java.lang.IllegalArgumentException;
	public void shutdown();

}
