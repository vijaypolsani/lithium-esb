package com.lithium.esb.compliance.lsw.api.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.lithium.esb.compliance.lsw.api.LswSqsInbound;

/**
 * The logic here creates the necessary Factory & Initialization functionality. Once the factory creates the connection,
 * the connection can be used in sending messages to the AWS Queue.
 */
public class LswSqsInboundImpl implements LswSqsInbound {
	
	/** The Constant US_WEST1. */
	private static final Region US_WEST1 = Region.getRegion(Regions.US_WEST_1);
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LswSqsInboundImpl.class);
	
	/** The credentials. */
	private static AWSCredentials credentials = null;
	
	/** The amazon sqs queue variable. */
	private static AmazonSQS sqs = null;
	
	/** The queue url. */
	private static String queueUrl = null;
	
	/** The Constant SAMPLE_DATA_PART1. */
	private static final String SAMPLE_DATA_PART1 = "{ \"_index\": \"activities_2014-09-29\", \"_type\": \"CaseStateSnapshot\", \"_id\": ";
	
	/** The Constant SAMPLE_DATA_PART2. */
	private static final String SAMPLE_DATA_PART2 = " , \"_score\": 1, \"_source\": { \"id\": 18850376, \"version\": 1412024276065, \"state\": \"AVAILABLE\", \"disposition\": \"UNASSIGNED\", \"encodedDisposition\": 0, \"currentState\": \"CLOSED\", \"currentTarStartDttm\": 1400553563000, \"currentCombinedTagIds\": [ 787,  786, 853, 785, 1735, 784, 1737, 1741, 860, 1719, 1687, 1747, 782, 783,  1725, 781, 845 ], \"currentBusinessHourType\": 0, \"currentTarDocBusinessHourType\": 0, \"beginDttm\": 1412012186970, \"endDttm\": 1412013006403, \"caseId\": 6008121, \"caseUuid\": \"937d01e1-774b-4ba4-9056-a19e8eb6c067\",  \"createdDttm\": 1399570556445, \"userId\": 114, \"teamId\": 63, \"casePriority\": 1, \"caseWorkqueueId\": 99, \"documentCount\": 17678, \"responseCount\": 0, \"companyKey\": \"google\" } }";
	
	/** The message id. */
	private static long messageId = 0;

	static {
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
			sqs = new AmazonSQSClient(credentials);
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}
		try {
			sqs.setRegion(US_WEST1);

			log.info("===========================================");
			log.info("Getting Started with Amazon SQS");
			log.info("===========================================\n");
			queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
		} catch (AmazonServiceException ase) {
			log.debug("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SQS, but was rejected with an error response for some reason.");
			log.debug("Error Message:    " + ase.getMessage());
			log.debug("HTTP Status Code: " + ase.getStatusCode());
			log.debug("AWS Error Code:   " + ase.getErrorCode());
			log.debug("Error Type:       " + ase.getErrorType());
			log.debug("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.debug("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with SQS, such as not "
					+ "being able to access the network.");
			log.debug("Error Message: " + ace.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.lsw.api.LswSqsInbound#sendSqsMessage(java.lang.String)
	 */
	@Override
	public void sendSqsMessage(String message) {
		try {
			log.info("Sending a message to: " + QUEUE_NAME + " : " + message);
			sqs.sendMessage(new SendMessageRequest(queueUrl, message));
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
					+ "a serious internal problem while trying to communicate with SQS, such as not "
					+ "being able to access the network.");
			log.error("Error Message: " + ace.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.lsw.api.LswSqsInbound#sendSampleSqsMessage()
	 */
	@Override
	public void sendSampleSqsMessage() {
		for (int i = 0; i < 100; i++)
			sendSqsMessage(SAMPLE_DATA_PART1 + messageId++ + SAMPLE_DATA_PART2);
	}
}
