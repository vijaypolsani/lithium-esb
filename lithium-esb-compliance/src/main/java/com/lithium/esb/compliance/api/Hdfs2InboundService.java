package com.lithium.esb.compliance.api;

import java.io.IOException;
import java.util.Set;

public interface Hdfs2InboundService {

	public Set<String> getSetOfLatestHdfsFiles() throws IOException;

	public void deleteAllFiles(String path, boolean recursiveTrue) throws IOException;

	public void createFile(String path) throws IOException;
}