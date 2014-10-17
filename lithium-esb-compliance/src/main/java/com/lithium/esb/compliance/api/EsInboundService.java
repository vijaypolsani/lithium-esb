package com.lithium.esb.compliance.api;

import java.io.IOException;
import java.util.Collection;

public interface EsInboundService {

	public Collection<String> getAllLatestFilesInfo() throws IOException;

	public Collection<String> getMatchingFileNameSearch(String fileName);

	public Collection<String> getAllFileOpenedStatus(boolean fileOpened);

	public Collection<String> getAllFileReadStatus(boolean fileRead);

	public Collection<String> getAllFileReadAndFileOpenedStatus(boolean fileRead, boolean fileOpened);

}
