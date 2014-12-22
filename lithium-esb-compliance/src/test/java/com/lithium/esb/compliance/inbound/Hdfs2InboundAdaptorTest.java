package com.lithium.esb.compliance.inbound;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hdfs2InboundAdaptorTest {
	private static final Logger log = LoggerFactory.getLogger(Hdfs2InboundAdaptorTest.class);
	private Hdfs2InboundAdaptor hadfs2InboundAdaptor = null;

	@Before
	public void setUp() throws IOException {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		//hadfs2InboundAdaptor = new Hdfs2InboundAdaptor("hdfs://127.0.0.1:9000", "vijay.polsani", "/int");
		hadfs2InboundAdaptor = new Hdfs2InboundAdaptor("/int", true);
		clearAll();
		hadfs2InboundAdaptor.createFile("/int/junit-test-case.test");
	}

	@After
	public void clearAll() {
		try {
			hadfs2InboundAdaptor.deleteAllFiles("/int", true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getListOfFiles() {
		Collection<String> listOfFiles = null;

		try {
			listOfFiles = hadfs2InboundAdaptor.getSetOfLatestHdfsFiles();
			log.info(">>> Size of Files: " + listOfFiles.size());
			log.info(">>> List of Files: " + listOfFiles.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertThat(listOfFiles.size(), is(equalTo(1)));
		//Cleanup after creating
	}
}
