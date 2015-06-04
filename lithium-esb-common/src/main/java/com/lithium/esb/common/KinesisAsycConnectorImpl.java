package com.lithium.esb.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.kinesis.model.transform.ExpiredIteratorExceptionUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.GetRecordsRequestMarshaller;
import com.amazonaws.services.kinesis.model.transform.GetRecordsResultJsonUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.InvalidArgumentExceptionUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.LimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.ProvisionedThroughputExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.PutRecordRequestMarshaller;
import com.amazonaws.services.kinesis.model.transform.PutRecordResultJsonUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.PutRecordsRequestMarshaller;
import com.amazonaws.services.kinesis.model.transform.PutRecordsResultJsonUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.ResourceInUseExceptionUnmarshaller;
import com.amazonaws.services.kinesis.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import com.lithium.esb.common.api.KinesisAsyncConnector;

public class KinesisAsycConnectorImpl extends AmazonWebServiceClient implements KinesisAsyncConnector {

	private AWSCredentialsProvider awsCredentialsProvider;
	protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;
	private ExecutorService executorService;
	private static final int DEFAULT_THREAD_POOL_SIZE = 50;
	private static final String ENDPOINT = "kinesis.us-west-1.amazonaws.com";
    public static final boolean LOGGING_AWS_REQUEST_METRIC = true;
    
	private void init() {
		jsonErrorUnmarshallers = new ArrayList<JsonErrorUnmarshaller>();
		jsonErrorUnmarshallers.add(new ResourceInUseExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new LimitExceededExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new InvalidArgumentExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new ExpiredIteratorExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new ProvisionedThroughputExceededExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
		jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());

		// calling this.setEndPoint(...) will also modify the signer accordingly
		this.setEndpoint(ENDPOINT);

		HandlerChainFactory chainFactory = new HandlerChainFactory();
		requestHandler2s
				.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/kinesis/request.handlers"));
		requestHandler2s.addAll(chainFactory
				.newRequestHandler2Chain("/com/amazonaws/services/kinesis/request.handler2s"));
	}

	public KinesisAsycConnectorImpl() {
		super(new ClientConfiguration(), RequestMetricCollector.NONE);
		this.awsCredentialsProvider = new StaticCredentialsProvider(
				(AWSCredentials) new DefaultAWSCredentialsProviderChain());
		this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
		init();
	}

	public KinesisAsycConnectorImpl(int threadPoolSize) {
		super(new ClientConfiguration(), RequestMetricCollector.NONE);
		this.awsCredentialsProvider = new StaticCredentialsProvider(
				(AWSCredentials) new DefaultAWSCredentialsProviderChain());
		this.executorService = Executors.newFixedThreadPool(threadPoolSize);
		init();
	}

	public KinesisAsycConnectorImpl(int threadPoolSize, RequestMetricCollector requestMetricCollector) {
		super(new ClientConfiguration(), requestMetricCollector);
		this.awsCredentialsProvider = new StaticCredentialsProvider(
				(AWSCredentials) new DefaultAWSCredentialsProviderChain());
		this.executorService = Executors.newFixedThreadPool(threadPoolSize);
		init();
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}


	@Override
	public void shutdown() {
		executorService.shutdownNow();
	}

	@Override
	public Future<PutRecordResult> putRecordAsync(final PutRecordRequest putRecordRequest)
			throws AmazonServiceException, AmazonClientException {
		return executorService.submit(new Callable<PutRecordResult>() {
			public PutRecordResult call() throws Exception {
				return putRecord(putRecordRequest);
			}
		});
	}

	public Future<PutRecordResult> putRecordAsync(final PutRecordRequest putRecordRequest,
			final AsyncHandler<PutRecordRequest, PutRecordResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return executorService.submit(new Callable<PutRecordResult>() {
			public PutRecordResult call() throws Exception {
				PutRecordResult result;
				try {
					result = putRecord(putRecordRequest);
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
	public Future<PutRecordsResult> putRecordsAsync(final PutRecordsRequest putRecordsRequest)
			throws AmazonServiceException, AmazonClientException {
		return executorService.submit(new Callable<PutRecordsResult>() {
			public PutRecordsResult call() throws Exception {
				return putRecords(putRecordsRequest);
			}
		});
	}

	public Future<PutRecordsResult> putRecordsAsync(final PutRecordsRequest putRecordsRequest,
			final AsyncHandler<PutRecordsRequest, PutRecordsResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return executorService.submit(new Callable<PutRecordsResult>() {
			public PutRecordsResult call() throws Exception {
				PutRecordsResult result;
				try {
					result = putRecords(putRecordsRequest);
				} catch (Exception ex) {
					asyncHandler.onError(ex);
					throw ex;
				}
				asyncHandler.onSuccess(putRecordsRequest, result);
				return result;
			}
		});
	}

	public Future<GetRecordsResult> getRecordsAsync(final GetRecordsRequest getRecordsRequest,
			final AsyncHandler<GetRecordsRequest, GetRecordsResult> asyncHandler) throws AmazonServiceException,
			AmazonClientException {
		return executorService.submit(new Callable<GetRecordsResult>() {
			public GetRecordsResult call() throws Exception {
				GetRecordsResult result;
				try {
					result = getRecords(getRecordsRequest);
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
	public Future<GetRecordsResult> getRecordsAsync(final GetRecordsRequest getRecordsRequest)
			throws AmazonServiceException, AmazonClientException {
		return executorService.submit(new Callable<GetRecordsResult>() {
			public GetRecordsResult call() throws Exception {
				return getRecords(getRecordsRequest);
			}
		});
	}

	public GetRecordsResult getRecords(GetRecordsRequest getRecordsRequest) {
		ExecutionContext executionContext = createExecutionContext(getRecordsRequest);
		AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
		awsRequestMetrics.startEvent(Field.ClientExecuteTime);
		Request<GetRecordsRequest> request = null;
		Response<GetRecordsResult> response = null;

		try {
			awsRequestMetrics.startEvent(Field.RequestMarshallTime);
			try {
				request = new GetRecordsRequestMarshaller().marshall(super.beforeMarshalling(getRecordsRequest));
				// Binds the request metrics to the current request.
				request.setAWSRequestMetrics(awsRequestMetrics);
			} finally {
				awsRequestMetrics.endEvent(Field.RequestMarshallTime);
			}

			Unmarshaller<GetRecordsResult, JsonUnmarshallerContext> unmarshaller = new GetRecordsResultJsonUnmarshaller();
			JsonResponseHandler<GetRecordsResult> responseHandler = new JsonResponseHandler<GetRecordsResult>(
					unmarshaller);

			response = invoke(request, responseHandler, executionContext);

			return response.getAwsResponse();
		} finally {

			endClientExecution(awsRequestMetrics, request, response, LOGGING_AWS_REQUEST_METRIC);
		}
	}

	private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request,
			HttpResponseHandler<AmazonWebServiceResponse<X>> responseHandler, ExecutionContext executionContext) {
		request.setEndpoint(endpoint);
		request.setTimeOffset(timeOffset);

		AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
		AWSCredentials credentials;
		awsRequestMetrics.startEvent(Field.CredentialsRequestTime);
		try {
			credentials = awsCredentialsProvider.getCredentials();
		} finally {
			awsRequestMetrics.endEvent(Field.CredentialsRequestTime);
		}

		AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
		if (originalRequest != null && originalRequest.getRequestCredentials() != null) {
			credentials = originalRequest.getRequestCredentials();
		}

		executionContext.setCredentials(credentials);
		JsonErrorResponseHandler errorResponseHandler = new JsonErrorResponseHandler(jsonErrorUnmarshallers);
		Response<X> result = client.execute(request, responseHandler, errorResponseHandler, executionContext);
		return result;
	}


	public PutRecordResult putRecord(PutRecordRequest putRecordRequest) {
		ExecutionContext executionContext = createExecutionContext(putRecordRequest);
		AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
		awsRequestMetrics.startEvent(Field.ClientExecuteTime);
		Request<PutRecordRequest> request = null;
		Response<PutRecordResult> response = null;

		try {
			awsRequestMetrics.startEvent(Field.RequestMarshallTime);
			try {
				request = new PutRecordRequestMarshaller().marshall(super.beforeMarshalling(putRecordRequest));
				// Binds the request metrics to the current request.
				request.setAWSRequestMetrics(awsRequestMetrics);
			} finally {
				awsRequestMetrics.endEvent(Field.RequestMarshallTime);
			}

			Unmarshaller<PutRecordResult, JsonUnmarshallerContext> unmarshaller = new PutRecordResultJsonUnmarshaller();
			JsonResponseHandler<PutRecordResult> responseHandler = new JsonResponseHandler<PutRecordResult>(
					unmarshaller);

			response = invoke(request, responseHandler, executionContext);

			return response.getAwsResponse();
		} finally {

			endClientExecution(awsRequestMetrics, request, response, LOGGING_AWS_REQUEST_METRIC);
		}
	}

	public  PutRecordsResult putRecords(PutRecordsRequest putRecordsRequest) {
		ExecutionContext executionContext = createExecutionContext(putRecordsRequest);
		AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
		awsRequestMetrics.startEvent(Field.ClientExecuteTime);
		Request<PutRecordsRequest> request = null;
		Response<PutRecordsResult> response = null;

		try {
			awsRequestMetrics.startEvent(Field.RequestMarshallTime);
			try {
				request = new PutRecordsRequestMarshaller().marshall(super.beforeMarshalling(putRecordsRequest));
				// Binds the request metrics to the current request.
				request.setAWSRequestMetrics(awsRequestMetrics);
			} finally {
				awsRequestMetrics.endEvent(Field.RequestMarshallTime);
			}

			Unmarshaller<PutRecordsResult, JsonUnmarshallerContext> unmarshaller = new PutRecordsResultJsonUnmarshaller();
			JsonResponseHandler<PutRecordsResult> responseHandler = new JsonResponseHandler<PutRecordsResult>(
					unmarshaller);

			response = invoke(request, responseHandler, executionContext);

			return response.getAwsResponse();
		} finally {
			endClientExecution(awsRequestMetrics, request, response, LOGGING_AWS_REQUEST_METRIC);
		}
	}

}
