package com.lithium.esb.compliance.api;

import java.io.IOException;

import org.apache.hadoop.fs.Path;

public interface Hdfs2ConsumerService {
	public String readFileContent(String filePath) throws IOException;
}
