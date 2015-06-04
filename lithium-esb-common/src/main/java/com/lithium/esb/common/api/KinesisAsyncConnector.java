package com.lithium.esb.common.api;

import java.util.concurrent.Future;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

public interface KinesisAsyncConnector {


    public Future<PutRecordResult> putRecordAsync(PutRecordRequest putRecordRequest) 
            throws AmazonServiceException, AmazonClientException;


    public Future<PutRecordResult> putRecordAsync(PutRecordRequest putRecordRequest,
            AsyncHandler<PutRecordRequest, PutRecordResult> asyncHandler)
                    throws AmazonServiceException, AmazonClientException;


    public Future<PutRecordsResult> putRecordsAsync(PutRecordsRequest putRecordsRequest) 
            throws AmazonServiceException, AmazonClientException;

 
    public Future<PutRecordsResult> putRecordsAsync(PutRecordsRequest putRecordsRequest,
            AsyncHandler<PutRecordsRequest, PutRecordsResult> asyncHandler)
                    throws AmazonServiceException, AmazonClientException;

    public Future<GetRecordsResult> getRecordsAsync(GetRecordsRequest getRecordsRequest) 
            throws AmazonServiceException, AmazonClientException;

    public Future<GetRecordsResult> getRecordsAsync(GetRecordsRequest getRecordsRequest,
            AsyncHandler<GetRecordsRequest, GetRecordsResult> asyncHandler)
                    throws AmazonServiceException, AmazonClientException;

}
