/*
 * Compliance Real Time Streaming Service
 */
package com.lithium.esb.compliance.api;

import java.io.IOException;

import com.lithium.streams.compliance.model.SecureEvent;

/**
 * The Interface Hdfs2ConsumerService provides capabilities to read file content of HDFS2 files remotely
 */
public interface Hdfs2ConsumerService {
	
	/**
	 * Read file content.
	 *
	 * @param filePath the file path
	 * @return the secure event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SecureEvent readFileContent(String filePath) throws IOException;
}
