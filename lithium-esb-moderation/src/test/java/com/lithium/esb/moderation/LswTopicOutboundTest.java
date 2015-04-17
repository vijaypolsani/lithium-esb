package com.lithium.esb.moderation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.lithium.esb.moderation.lsw.api.LswTopicOutbound;
import com.lithium.esb.moderation.lsw.api.outbound.LswTopicOutboundImpl;

public class LswTopicOutboundTest {
	private static final Logger log = LoggerFactory.getLogger(LswTopicOutboundTest.class);
	private static final String SAMPLE_DATA_PART1 = "{\"formatVersion\":\"1.0\",\"published\":\"1403890120083\",\"generator\":{\"source\":\"actiance.stage\",\"eventId\":\"3943224\"},\"provider\":{\"service\":\"lia\",\"version\":\"14.6.0\"},\"title\":\"Re: Plain text post\",\"actor\":{\"uid\":\"2\",\"login\":\"testuser\",\"registrationStatus\":\"FULLY_REGISTERED\",\"email\":\"test@test.com\",\"type\":\"user\",\"registrationTime\":\"1402005134171\"},\"verb\":\"EntityCreated message\",\"target\":{\"type\":\"forum-board\",\"conversationType\":\"conversation\",\"id\":\"9\",\"conversationId\":\"";
	private static final String SAMPLE_DATA_PART2 = "\"},\"streamObject\":{\"objectType\":\"reply\",\"id\":\"22\",\"displayName\":\"Re: Plain text post\",\"content\":\"<p>Plain text reply</p>\",\"visibility\":\"public\",\"subject\":\"Re: Plain text post\",\"added\":\"\",\"postTime\":\"1403890119991\",\"isTopic\":\"false\"}}";
	private static final Region REGION = Region.getRegion(Regions.US_WEST_1);
	private static final String APPLICATION_STREAM_NAME = "dev-default-tokinesis";
	private static final String PARTITION_KEY = "lithium-community-name";

	private LswTopicOutbound lswTopicOutbound = null; 

	public LswTopicOutboundTest() {
	}

	@Before
	public void setup() {
		lswTopicOutbound = new LswTopicOutboundImpl("US_WEST_1", APPLICATION_STREAM_NAME);
	}

	@Test
	public void sendSampleStreamMessage() {
		//500 is the limit for consumption in batch by Kinesis
		int numberOfTestRecords = 500;
		List<PutRecordsRequestEntry> sampleStreamMessages = new ArrayList<PutRecordsRequestEntry>();
		PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
		for (int i = 0; i < numberOfTestRecords; i++) {
			putRecordsRequestEntry.setData(ByteBuffer.wrap((SAMPLE_DATA_PART1 + i + SAMPLE_DATA_PART2).getBytes()));
			putRecordsRequestEntry.setPartitionKey(PARTITION_KEY);
			sampleStreamMessages.add(putRecordsRequestEntry);
		}
		PutRecordsResult putRecordsResult = lswTopicOutbound.sendStreamMessages(sampleStreamMessages);
		assertNotNull(putRecordsResult);
		assertEquals(numberOfTestRecords, putRecordsResult.getFailedRecordCount().intValue() + numberOfTestRecords);
		log.info("PutRecordsResult: " + putRecordsResult);

	}
}
