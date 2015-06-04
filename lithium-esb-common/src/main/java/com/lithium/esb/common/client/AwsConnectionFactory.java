package com.lithium.esb.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class AwsConnectionFactory {
	//TODO: Connection Pool Implementation.
	private AwsConnection awsConnection = null;
	private static final Logger log = LoggerFactory.getLogger(AwsConnectionFactory.class);

	public AwsConnection getAwsConnection() {
		if (awsConnection != null)
			return awsConnection;

		try {
			/*
			 * The ProfileCredentialsProvider will return your [default]
			 * credential profile by reading from the credentials file located at
			 * (~/.aws/credentials).
			 */
			// Ensure the JVM will refresh the cached IP values of AWS resources (e.g. service endpoints).
			java.security.Security.setProperty("networkaddress.cache.ttl", "60");
			try {
				awsConnection = new AwsConnection(new ProfileCredentialsProvider().getCredentials());

			} catch (Exception e) {
				e.printStackTrace();
				throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
						+ "Please make sure that your credentials file is at the correct "
						+ "location (~/.aws/credentials), and is in valid format.", e);
			}
			awsConnection.setEndpoint("https://kinesis.us-west-1.amazonaws.com");
		} catch (AmazonServiceException ase) {
			log.error("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon Kinesis, but was rejected with an error response for some reason.");
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
		return awsConnection;
	}
}
