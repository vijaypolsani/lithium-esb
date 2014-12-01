package com.lithium.esb.compliance.api;

import java.io.IOException;

import com.lithium.streams.compliance.model.SecureEvent;

public interface Hdfs2ConsumerService {
	public SecureEvent readFileContent(String filePath) throws IOException;
}
