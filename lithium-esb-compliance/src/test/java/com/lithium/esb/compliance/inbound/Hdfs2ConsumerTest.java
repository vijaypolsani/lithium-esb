package com.lithium.esb.compliance.inbound;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hdfs2ConsumerTest {
	private Hdfs2ConsumerProvider hdfs2ConsumerProvider;
	private static final Logger log = LoggerFactory.getLogger(Hdfs2ConsumerTest.class);
	private static final String FILE_NAME = "hdfs://localhost:9000/int/in.log";

	@Before
	public void setup() throws IOException {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		hdfs2ConsumerProvider = Hdfs2ConsumerProvider.creatHdfs2ConsumerProviderWithDefaultConfiguration();
		//hdfs2ConsumerProvider.createFile(FILE_NAME);
	}

	@Test
	public void consumeFiles() {
		String content = null;
		try {
			content = hdfs2ConsumerProvider.readFileContent(FILE_NAME);
			log.info(">>> Content" + content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(content);
	}

	@After
	public void cleanUp() throws IOException {
		//hdfs2ConsumerProvider.deleteFile(FILE_NAME);
	}
}
