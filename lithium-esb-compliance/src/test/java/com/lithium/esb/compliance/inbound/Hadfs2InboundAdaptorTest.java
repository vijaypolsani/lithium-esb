package com.lithium.esb.compliance.inbound;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hadfs2InboundAdaptorTest {
	private static final Logger log = LoggerFactory.getLogger(Hadfs2InboundAdaptorTest.class);
	private Hdfs2InboundAdaptor hadfs2InboundAdaptor = null;

	@Before
	public void setUp() throws IOException {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		hadfs2InboundAdaptor = new Hdfs2InboundAdaptor("hdfs://127.0.0.1:9000", "vijay.polsani", "/int/client1.test");
	}

	@Test
	public void getListOfFiles() {
		Collection<String> listOfFiles;
		try {
			listOfFiles = hadfs2InboundAdaptor.getListOfLatestHdfsFiles();
			log.info(">>> Size of Files: " + listOfFiles.size());
			log.info(">>> List of Files: " + listOfFiles.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
