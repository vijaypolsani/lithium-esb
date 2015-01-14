/*
 * Compliance Real Time Streaming Service
 */
package com.lithium.esb.compliance.api;

import java.io.IOException;
import java.util.Set;

/**
 * The Interface Hdfs2InboundService provides capabilities to list/delete/create HDFS2 files on remote server.
 */
public interface Hdfs2InboundService {

	/**
	 * Gets the sets the of latest hdfs files.
	 *
	 * @return the sets the of latest hdfs files
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Set<String> getSetOfLatestHdfsFiles() throws IOException;

	/**
	 * Delete all files.
	 *
	 * @param path the path
	 * @param recursiveTrue the recursive true
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteAllFiles(String path, boolean recursiveTrue) throws IOException;

	/**
	 * Creates the file.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createFile(String path) throws IOException;
}