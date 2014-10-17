package com.lithium.esb.compliance.api;

import java.io.IOException;
import java.util.Set;

import com.lithium.esb.compliance.model.HdfsFileDetail;

public interface Hdfs2InboundService {

	public Set<String> getListOfLatestHdfsFiles() throws IOException;
	
}