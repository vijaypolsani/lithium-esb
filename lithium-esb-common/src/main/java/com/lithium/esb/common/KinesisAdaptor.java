package com.lithium.esb.common;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lithium.esb.common.api.KinesisAsyncConnector;
import com.lithium.esb.common.client.AwsConnectionFactory;

@Singleton
public class KinesisAdaptor implements KinesisAsyncConnector {
	private static final int DEFAULT_THREAD_POOL_SIZE = 10;
	protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;
	private final ListeningExecutorService listeningExecutorService;
	public static final boolean LOGGING_AWS_REQUEST_METRIC = true;

	public KinesisAdaptor() {
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors
				.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE));
	}

	public KinesisAdaptor(int threadPoolSize) {
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threadPoolSize));
	}

	@Override
	public ListenableFuture<PutRecordResult> putRecordAsync(final PutRecordRequest putRecordRequest)
			throws AmazonServiceException, AmazonClientException {
		return listeningExecutorService.submit(new Callable<PutRecordResult>() {
			public PutRecordResult call() throws Exception {
				return new AwsConnectionFactory().getAwsConnection().putRecord(putRecordRequest);
			}
		});
	}

	public ListenableFuture<PutRecordResult> putRecordAsync(final PutRecordRequest putRecordRequest,
			final AsyncHandler<PutRecordRequest, PutRecordResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return listeningExecutorService.submit(new Callable<PutRecordResult>() {
			public PutRecordResult call() throws Exception {
				PutRecordResult result;
				try {
					result = new AwsConnectionFactory().getAwsConnection().putRecord(putRecordRequest);
				} catch (Exception ex) {
					asyncHandler.onError(ex);
					throw ex;
				}
				asyncHandler.onSuccess(putRecordRequest, result);
				return result;
			}
		});
	}

	@Override
	public ListenableFuture<PutRecordsResult> putRecordsAsync(final PutRecordsRequest putRecordsRequest)
			throws AmazonServiceException, AmazonClientException {
		return listeningExecutorService.submit(new Callable<PutRecordsResult>() {
			public PutRecordsResult call() throws Exception {
				return new AwsConnectionFactory().getAwsConnection().putRecords(putRecordsRequest);
			}
		});
	}

	public ListenableFuture<PutRecordsResult> putRecordsAsync(final PutRecordsRequest putRecordsRequest,
			final AsyncHandler<PutRecordsRequest, PutRecordsResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return listeningExecutorService.submit(new Callable<PutRecordsResult>() {
			public PutRecordsResult call() throws Exception {
				PutRecordsResult result;
				try {
					result = new AwsConnectionFactory().getAwsConnection().putRecords(putRecordsRequest);
				} catch (Exception ex) {
					asyncHandler.onError(ex);
					throw ex;
				}
				asyncHandler.onSuccess(putRecordsRequest, result);
				return result;
			}
		});
	}

	public ListenableFuture<GetRecordsResult> getRecordsAsync(final GetRecordsRequest getRecordsRequest,
			final AsyncHandler<GetRecordsRequest, GetRecordsResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return listeningExecutorService.submit(new Callable<GetRecordsResult>() {
			public GetRecordsResult call() throws Exception {
				GetRecordsResult result;
				try {
					result = new AwsConnectionFactory().getAwsConnection().getRecords(getRecordsRequest);
				} catch (Exception ex) {
					asyncHandler.onError(ex);
					throw ex;
				}
				asyncHandler.onSuccess(getRecordsRequest, result);
				return result;
			}
		});
	}

	@Override
	public ListenableFuture<GetRecordsResult> getRecordsAsync(final GetRecordsRequest getRecordsRequest)
			throws AmazonServiceException, AmazonClientException {
		return listeningExecutorService.submit(new Callable<GetRecordsResult>() {
			public GetRecordsResult call() throws Exception {
				return new AwsConnectionFactory().getAwsConnection().getRecords(getRecordsRequest);
			}
		});
	}

}
