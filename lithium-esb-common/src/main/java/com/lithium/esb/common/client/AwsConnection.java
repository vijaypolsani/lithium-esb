package com.lithium.esb.common.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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
import com.lithium.esb.common.api.KinesisConnector;

public class AwsConnection extends AmazonKinesisClient implements KinesisConnector {
	private static final Log log = LogFactory.getLog(AwsConnection.class);
	private AWSCredentialsProvider awsCredentialsProvider;
	protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;
	public static final boolean LOGGING_AWS_REQUEST_METRIC = true;

	public AwsConnection() {
		this(new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
	}

	public AwsConnection(AWSCredentials awsCredentials) {

		this(awsCredentials, new ClientConfiguration());
	}

	public AwsConnection(ClientConfiguration clientConfiguration) {
		this(new DefaultAWSCredentialsProviderChain(), clientConfiguration);
	}

	public AwsConnection(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
		super(adjustClientConfiguration(clientConfiguration));
		this.awsCredentialsProvider = new StaticCredentialsProvider(awsCredentials);
		init();
	}

	public AwsConnection(AWSCredentialsProvider awsCredentialsProvider) {
		this(awsCredentialsProvider, new ClientConfiguration());
	}

	public AwsConnection(AWSCredentialsProvider awsCredentialsProvider, ClientConfiguration clientConfiguration) {
		this(awsCredentialsProvider, clientConfiguration, null);
	}

	public AwsConnection(AWSCredentialsProvider awsCredentialsProvider, ClientConfiguration clientConfiguration,
			RequestMetricCollector requestMetricCollector) {
		super(adjustClientConfiguration(clientConfiguration));

		this.awsCredentialsProvider = awsCredentialsProvider;
		init();
	}

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
		this.setEndpoint("kinesis.us-west-1.amazonaws.com");

		HandlerChainFactory chainFactory = new HandlerChainFactory();
		requestHandler2s
				.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/kinesis/request.handlers"));
		requestHandler2s.addAll(chainFactory
				.newRequestHandler2Chain("/com/amazonaws/services/kinesis/request.handler2s"));
	}

	private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
		ClientConfiguration config = orig;

		return config;
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

            Unmarshaller<PutRecordResult, JsonUnmarshallerContext> unmarshaller =
                new PutRecordResultJsonUnmarshaller();
            JsonResponseHandler<PutRecordResult> responseHandler =
                new JsonResponseHandler<PutRecordResult>(unmarshaller);

            response = invoke(request, responseHandler, executionContext);

            return response.getAwsResponse();
        } finally {
            
            endClientExecution(awsRequestMetrics, request, response, LOGGING_AWS_REQUEST_METRIC);
        }
    }

	public PutRecordsResult putRecords(PutRecordsRequest putRecordsRequest) {
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
		System.out.println("Credentials3 Access: " + credentials.getAWSAccessKeyId());
		System.out.println("Credentials3 Secret: " + credentials.getAWSSecretKey());

		executionContext.setCredentials(credentials);
		JsonErrorResponseHandler errorResponseHandler = new JsonErrorResponseHandler(jsonErrorUnmarshallers);
		System.out.println("Reuest ServiceName: " + request.getServiceName());
		System.out.println("Reuest toString: " + request.toString());
		System.out.println("Reuest executionContext: " + executionContext.toString());

		Response<X> result = client.execute(request, responseHandler, errorResponseHandler, executionContext);
		System.out.println("Result: " + result.getAwsResponse().toString());
		return result;
	}
}
