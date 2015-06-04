package com.lithium.esb.common;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.lithium.esb.common.api.KinesisAsyncConnector;

public class KinesisAdaptorClientTest {
	private static Logger log = LoggerFactory.getLogger(KinesisAdaptorClientTest.class);
	private static KinesisAsyncConnector kinesisAdaptor = KinesisAdaptorFactory.getKinesisAdaptor();
	private static final String APPLICATION_STREAM_NAME = "dev-default-tokinesis";
	private static final String PARTITION_KEY = "lithium-community-name";

	/*
	@BeforeClass
	public static void oneTimeSetUp() {
	    // one-time initialization code   
		log.info("@BeforeClass - oneTimeSetUp");
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
	    // one-time cleanup code
		log.info("@AfterClass - oneTimeTearDown");
	}
	**/
	@Before
	public void setUp() {
		log.info("@Before - setUp");
	}

	@After
	public void tearDown() {
		log.info("@After - tearDown");
	}

	@Test
	public void callKinesisAdaptorClient() throws AmazonServiceException, AmazonClientException, InterruptedException,
			ExecutionException {
		PutRecordRequest putRecordRequest = new PutRecordRequest();
		putRecordRequest.setStreamName(APPLICATION_STREAM_NAME);
		putRecordRequest.setData(ByteBuffer.wrap("TEST".getBytes()));
		putRecordRequest.setPartitionKey(PARTITION_KEY);
		System.out.println("Done: " + kinesisAdaptor.putRecordAsync(putRecordRequest).get().getSequenceNumber());
	}

	public static void main(String args[]) {
		try {
			new KinesisAdaptorClientTest().callKinesisAdaptorClient();
		} catch (AmazonClientException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
