package com.lithium.esb.moderation.lsw.api.outbound;

import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.lithium.esb.moderation.lsw.api.LswTopicOutbound;

/**
 * The logic here creates the necessary Factory & Initialization functionality. Once the factory creates the connection,
 * the connection can be used in sending messages to the AWS Queue.
 */
public class LswTopicOutboundImpl implements LswTopicOutbound {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LswTopicOutboundImpl.class);
	private static final Region REGION_US_WEST_1 = Region.getRegion(Regions.US_WEST_1);
	private static final Region REGION_US_EAST_1 = Region.getRegion(Regions.US_EAST_1);
	private static final Region REGION_US_WEST_2 = Region.getRegion(Regions.US_WEST_2);
	// Initial position in the stream when the application starts up for the first time.
	// Position can be one of LATEST (most recent data) or TRIM_HORIZON (oldest available data)
	private static AmazonKinesisClient amazonKinesisClient;
	private final String regionName;
	private final String streamName;

	public LswTopicOutboundImpl(String region, String streamName) {
		this.regionName = region;
		this.streamName = streamName;
		try {
			/*
			 * The ProfileCredentialsProvider will return your [default]
			 * credential profile by reading from the credentials file located at
			 * (~/.aws/credentials).
			 */
			// Ensure the JVM will refresh the cached IP values of AWS resources (e.g. service endpoints).
			java.security.Security.setProperty("networkaddress.cache.ttl", "60");
			try {
				amazonKinesisClient = new AmazonKinesisClient(new ProfileCredentialsProvider().getCredentials());
			} catch (Exception e) {
				throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
						+ "Please make sure that your credentials file is at the correct "
						+ "location (~/.aws/credentials), and is in valid format.", e);
			}
			if (region != null) {
				if (region.equalsIgnoreCase("US_EAST_1"))
					amazonKinesisClient.setRegion(REGION_US_EAST_1);
				else if (region.equalsIgnoreCase("US_WEST_1"))
					amazonKinesisClient.setRegion(REGION_US_WEST_1);
				else if (region.equalsIgnoreCase("US_WEST_2"))
					amazonKinesisClient.setRegion(REGION_US_WEST_2);
			} else
				amazonKinesisClient.setRegion(REGION_US_WEST_1);
		} catch (AmazonServiceException ase) {
			log.debug("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon Kinesis, but was rejected with an error response for some reason.");
			log.debug("Error Message:    " + ase.getMessage());
			log.debug("HTTP Status Code: " + ase.getStatusCode());
			log.debug("AWS Error Code:   " + ase.getErrorCode());
			log.debug("Error Type:       " + ase.getErrorType());
			log.debug("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.debug("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with Kinesis, such as not "
					+ "being able to access the network.");
			log.debug("Error Message: " + ace.getMessage());
		}
	}

	public PutRecordResult sendStreamMessage(String message) {
		PutRecordResult putRecordResult = null;
		try {
			final long createTime = System.currentTimeMillis();
			final PutRecordRequest putRecordRequest = new PutRecordRequest();
			putRecordRequest.setStreamName(streamName);

			putRecordRequest.setData(ByteBuffer.wrap(String.format(message, createTime).getBytes()));
			putRecordRequest.setPartitionKey(String.format("partitionKey-%d", createTime));
			putRecordResult = amazonKinesisClient.putRecord(putRecordRequest);
			log.info("Successfully put record, partition key : " + putRecordRequest.getPartitionKey() + " ShardID : "
					+ putRecordResult.getShardId() + "SequenceNumber : " + putRecordResult.getSequenceNumber());
		} catch (AmazonServiceException ase) {
			log.error("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SQS, but was rejected with an error response for some reason.");
			log.error("Error Message:    " + ase.getMessage());
			log.error("HTTP Status Code: " + ase.getStatusCode());
			log.error("AWS Error Code:   " + ase.getErrorCode());
			log.error("Error Type:       " + ase.getErrorType());
			log.error("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.error("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with Kinesis, such as not "
					+ "being able to access the network.");
			log.error("Error Message: " + ace.getMessage());
		}
		return putRecordResult;
	}

	public PutRecordsResult sendStreamMessages(List<PutRecordsRequestEntry> messages) {
		PutRecordsResult putRecordsResult = null;
		try {
			final long createTime = System.currentTimeMillis();
			final PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
			putRecordsRequest.setStreamName(streamName);
			putRecordsRequest.setRecords(messages);
			putRecordsResult = amazonKinesisClient.putRecords(putRecordsRequest);
			//putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", createTime));
			log.error("Failed to put records count : " + putRecordsResult.getFailedRecordCount());
			log.info("Total time taken to put the records into Kinesis: " + (System.currentTimeMillis() - createTime));
		} catch (AmazonServiceException ase) {
			log.error("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SQS, but was rejected with an error response for some reason.");
			log.error("Error Message:    " + ase.getMessage());
			log.error("HTTP Status Code: " + ase.getStatusCode());
			log.error("AWS Error Code:   " + ase.getErrorCode());
			log.error("Error Type:       " + ase.getErrorType());
			log.error("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.error("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with Kinesis, such as not "
					+ "being able to access the network.");
			log.error("Error Message: " + ace.getMessage());
		}
		return putRecordsResult;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @return the streamName
	 */
	public String getStreamName() {
		return streamName;
	}

}
