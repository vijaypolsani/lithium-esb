package com.lithium.esb.common.api;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

public interface KinesisSyncConnector{

	public PutRecordResult putRecord(PutRecordRequest putRecordRequest) throws AmazonServiceException,
			AmazonClientException;

	public PutRecordsResult putRecords(PutRecordsRequest putRecordsRequest) throws AmazonServiceException,
			AmazonClientException;

	public PutRecordResult putRecord(String streamName, java.nio.ByteBuffer data, String partitionKey)
			throws AmazonServiceException, AmazonClientException;

	public PutRecordResult putRecord(String streamName, java.nio.ByteBuffer data, String partitionKey,
			String sequenceNumberForOrdering) throws AmazonServiceException, AmazonClientException;

	public GetRecordsResult getRecords(GetRecordsRequest getRecordsRequest) throws AmazonServiceException, AmazonClientException;


	public void shutdown();
}
