/*
 * Compliance Real Time Streaming Service
 */
package com.lithium.esb.compliance.api;

import java.io.IOException;
import java.util.Collection;

/**
 * The Interface EsInboundService provides access methods for ElasticSearch inbound data query api's.
 */
public interface EsInboundService {

	/**
	 * Gets the all latest files info.
	 *
	 * @return the all latest files info
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Collection<String> getAllLatestFilesInfo() throws IOException;

	/**
	 * Gets the matching file name search.
	 *
	 * @param fileName the file name
	 * @return the matching file name search
	 */
	public Collection<String> getMatchingFileNameSearch(String fileName);

	/**
	 * Gets the all file opened status.
	 *
	 * @param fileOpened the file opened
	 * @return the all file opened status
	 */
	public Collection<String> getAllFileOpenedStatus(boolean fileOpened);

	/**
	 * Gets the all file read status.
	 *
	 * @param fileRead the file read
	 * @return the all file read status
	 */
	public Collection<String> getAllFileReadStatus(boolean fileRead);

	/**
	 * Gets the all file read and file opened status.
	 *
	 * @param fileRead the file read
	 * @param fileOpened the file opened
	 * @return the all file read and file opened status
	 */
	public Collection<String> getAllFileReadAndFileOpenedStatus(boolean fileRead, boolean fileOpened);

}
