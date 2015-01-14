package com.lithium.esb.compliance.lsw.api.outbound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

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
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.lithium.esb.compliance.lsw.api.LswSqsOutbound;
import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;

/**
 * The implementation of reading Messages to the AWS SQS. ConsumeMessages can read messages in bulk and return the List.
 * 
 */
public class LswSqsOutboundImpl implements LswSqsOutbound {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LswSqsOutboundImpl.class);
	
	/** The credentials. */
	private static AWSCredentials credentials = null;
	
	/** The queue url. */
	private static String queueUrl = null;
	
	/** The sqs. */
	private static AmazonSQS sqs = null;
	
	/** The receive message request. */
	private static ReceiveMessageRequest receiveMessageRequest = null;
	
	/** The Constant usWest2. */
	private static final Region usWest2 = Region.getRegion(Regions.US_WEST_1);

	static {
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
			sqs = new AmazonSQSClient(credentials);
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}
		sqs.setRegion(usWest2);

		log.info("===========================================");
		log.info("Getting Started with Amazon SQS");
		log.info("===========================================\n");
		queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
		receiveMessageRequest = new ReceiveMessageRequest(queueUrl).withWaitTimeSeconds(WAIT_TIME_IN_SECONDS);
	}

	/* The SQS method waits 20 Secs to avoid constant polling. This is a setting in SQS
	 *  
	 */

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.lsw.api.LswSqsOutbound#consumeMessages()
	 */
	@Override
	public List<SecureEvent> consumeMessages() {
		final List<SecureEvent> secureEvents = new ArrayList<>();
		try {
			ReceiveMessageResult receiveMessageResult = null;
			List<Message> messages = null;
			secureEvents.clear();

			do {
				receiveMessageResult = sqs.receiveMessage(receiveMessageRequest);
				messages = receiveMessageResult.getMessages();
				log.info(">>> Number of messages in the queue: " + messages.size());
				for (Message message : messages) {
					log.debug("  Message");
					log.debug("    MessageId:     " + message.getMessageId());
					log.debug("    ReceiptHandle: " + message.getReceiptHandle());
					log.debug("    MD5OfBody:     " + message.getMD5OfBody());
					log.debug("    Body:          " + message.getBody());
					for (Entry<String, String> entry : message.getAttributes().entrySet()) {
						log.debug("  Attribute");
						log.debug("    Name:  " + entry.getKey());
						log.debug("    Value: " + entry.getValue());
					}
					secureEvents.add(new Payload(message.getBody().getBytes()));
				}
			} while (messages.size() != 0);
		} catch (AmazonServiceException ase) {
			log.error("<<< Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SQS, but was rejected with an error response for some reason.");
			log.error("<<< Error Message:    " + ase.getMessage());
			log.error("<<< HTTP Status Code: " + ase.getStatusCode());
			log.error("<<< AWS Error Code:   " + ase.getErrorCode());
			log.error("<<< Error Type:       " + ase.getErrorType());
			log.error("<<< Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.error("<<< Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with SQS, such as not "
					+ "being able to access the network.");
			log.error("<<< Error Message: " + ace.getMessage());
		}
		log.info(">>> Number of messages read from the queue: " + secureEvents.size());
		List<SecureEvent> returnSet = Collections.unmodifiableList(secureEvents);
		return returnSet;
	}
}
