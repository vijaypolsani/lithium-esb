package com.lithium.esb.common.client;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.services.kinesis.model.InvalidArgumentException;
import com.amazonaws.services.kinesis.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import com.amazonaws.services.kinesis.model.transform.ExpiredIteratorExceptionUnmarshaller;
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

/**
 * Client for accessing AmazonKinesis.  All service calls made
 * using this client are blocking, and will not return until the service call
 * completes.
 * <p>
 * Amazon Kinesis Service API Reference <p>
 * Amazon Kinesis is a managed service that scales elastically for real
 * time processing of streaming big data.
 * </p>
 */
public class AmazonKinesisClient extends AmazonWebServiceClient  {

    /** Provider for AWS credentials. */
    private AWSCredentialsProvider awsCredentialsProvider;

    private static final Log log = LogFactory.getLog(AmazonKinesisClient.class);

    /**
     * List of exception unmarshallers for all AmazonKinesis exceptions.
     */
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis.  A credentials provider chain will be used
     * that searches for credentials in this order:
     * <ul>
     *  <li> Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY </li>
     *  <li> Java System Properties - aws.accessKeyId and aws.secretKey </li>
     *  <li> Instance profile credentials delivered through the Amazon EC2 metadata service </li>
     * </ul>
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @see DefaultAWSCredentialsProviderChain
     */
    public AmazonKinesisClient() {
        this(new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis.  A credentials provider chain will be used
     * that searches for credentials in this order:
     * <ul>
     *  <li> Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY </li>
     *  <li> Java System Properties - aws.accessKeyId and aws.secretKey </li>
     *  <li> Instance profile credentials delivered through the Amazon EC2 metadata service </li>
     * </ul>
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param clientConfiguration The client configuration options controlling how this
     *                       client connects to AmazonKinesis
     *                       (ex: proxy settings, retry counts, etc.).
     *
     * @see DefaultAWSCredentialsProviderChain
     */
    public AmazonKinesisClient(ClientConfiguration clientConfiguration) {
        this(new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis using the specified AWS account credentials.
     * 
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param awsCredentials The AWS credentials (access key ID and secret key) to use
     *                       when authenticating with AWS services.
     */
    public AmazonKinesisClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis using the specified AWS account credentials
     * and client configuration options.
     * 
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param awsCredentials The AWS credentials (access key ID and secret key) to use
     *                       when authenticating with AWS services.
     * @param clientConfiguration The client configuration options controlling how this
     *                       client connects to AmazonKinesis
     *                       (ex: proxy settings, retry counts, etc.).
     */
    public AmazonKinesisClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        super(adjustClientConfiguration(clientConfiguration));
        
        this.awsCredentialsProvider = new StaticCredentialsProvider(awsCredentials);
        
        init();
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis using the specified AWS account credentials provider.
     * 
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param awsCredentialsProvider
     *            The AWS credentials provider which will provide credentials
     *            to authenticate requests with AWS services.
     */
    public AmazonKinesisClient(AWSCredentialsProvider awsCredentialsProvider) {
        this(awsCredentialsProvider, new ClientConfiguration());
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis using the specified AWS account credentials
     * provider and client configuration options.
     * 
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param awsCredentialsProvider
     *            The AWS credentials provider which will provide credentials
     *            to authenticate requests with AWS services.
     * @param clientConfiguration The client configuration options controlling how this
     *                       client connects to AmazonKinesis
     *                       (ex: proxy settings, retry counts, etc.).
     */
    public AmazonKinesisClient(AWSCredentialsProvider awsCredentialsProvider, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider, clientConfiguration, null);
    }

    /**
     * Constructs a new client to invoke service methods on
     * AmazonKinesis using the specified AWS account credentials
     * provider, client configuration options and request metric collector.
     * 
     * <p>
     * All service calls made using this new client object are blocking, and will not
     * return until the service call completes.
     *
     * @param awsCredentialsProvider
     *            The AWS credentials provider which will provide credentials
     *            to authenticate requests with AWS services.
     * @param clientConfiguration The client configuration options controlling how this
     *                       client connects to AmazonKinesis
     *                       (ex: proxy settings, retry counts, etc.).
     * @param requestMetricCollector optional request metric collector
     */
    public AmazonKinesisClient(AWSCredentialsProvider awsCredentialsProvider,
            ClientConfiguration clientConfiguration,
            RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        
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
        requestHandler2s.addAll(chainFactory.newRequestHandlerChain(
                "/com/amazonaws/services/kinesis/request.handlers"));
        requestHandler2s.addAll(chainFactory.newRequestHandler2Chain(
                "/com/amazonaws/services/kinesis/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        ClientConfiguration config = orig;
        
        return config;
    }

    
    /**
     * <p>
     * Puts (writes) a single data record from a producer into an Amazon
     * Kinesis stream. Call <code>PutRecord</code> to send data from the
     * producer into the Amazon Kinesis stream for real-time ingestion and
     * subsequent processing, one record at a time. Each shard can support up
     * to 1000 records written per second, up to a maximum total of 1 MB data
     * written per second.
     * </p>
     * <p>
     * You must specify the name of the stream that captures, stores, and
     * transports the data; a partition key; and the data blob itself.
     * </p>
     * <p>
     * The data blob can be any type of data; for example, a segment from a
     * log file, geographic/location data, website clickstream data, and so
     * on.
     * </p>
     * <p>
     * The partition key is used by Amazon Kinesis to distribute data across
     * shards. Amazon Kinesis segregates the data records that belong to a
     * data stream into multiple shards, using the partition key associated
     * with each data record to determine which shard a given data record
     * belongs to.
     * </p>
     * <p>
     * Partition keys are Unicode strings, with a maximum length limit of
     * 256 bytes. An MD5 hash function is used to map partition keys to
     * 128-bit integer values and to map associated data records to shards
     * using the hash key ranges of the shards. You can override hashing the
     * partition key to determine the shard by explicitly specifying a hash
     * value using the <code>ExplicitHashKey</code> parameter. For more
     * information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-partition-key"> Partition Key </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * <code>PutRecord</code> returns the shard ID of where the data record
     * was placed and the sequence number that was assigned to the data
     * record.
     * </p>
     * <p>
     * Sequence numbers generally increase over time. To guarantee strictly
     * increasing ordering, use the <code>SequenceNumberForOrdering</code>
     * parameter. For more information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-sequence-number"> Sequence Number </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * If a <code>PutRecord</code> request cannot be processed because of
     * insufficient provisioned throughput on the shard involved in the
     * request, <code>PutRecord</code> throws
     * <code>ProvisionedThroughputExceededException</code> .
     * </p>
     * <p>
     * Data records are accessible for only 24 hours from the time that they
     * are added to an Amazon Kinesis stream.
     * </p>
     *
     * @param putRecordRequest Container for the necessary parameters to
     *           execute the PutRecord service method on AmazonKinesis.
     * 
     * @return The response from the PutRecord service method, as returned by
     *         AmazonKinesis.
     * 
     * @throws ProvisionedThroughputExceededException
     * @throws InvalidArgumentException
     * @throws ResourceNotFoundException
     *
     * @throws AmazonClientException
     *             If any internal errors are encountered inside the client while
     *             attempting to make the request or handle the response.  For example
     *             if a network connection is not available.
     * @throws AmazonServiceException
     *             If an error response is returned by AmazonKinesis indicating
     *             either a problem with the data in the request, or a server side issue.
     */
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

 
    /**
     * <p>
     * Puts (writes) multiple data records from a producer into an Amazon
     * Kinesis stream in a single call (also referred to as a
     * <code>PutRecords</code> request). Use this operation to send data from
     * a data producer into the Amazon Kinesis stream for real-time ingestion
     * and processing. Each shard can support up to 1000 records written per
     * second, up to a maximum total of 1 MB data written per second.
     * </p>
     * <p>
     * You must specify the name of the stream that captures, stores, and
     * transports the data; and an array of request <code>Records</code> ,
     * with each record in the array requiring a partition key and data blob.
     * </p>
     * <p>
     * The data blob can be any type of data; for example, a segment from a
     * log file, geographic/location data, website clickstream data, and so
     * on.
     * </p>
     * <p>
     * The partition key is used by Amazon Kinesis as input to a hash
     * function that maps the partition key and associated data to a specific
     * shard. An MD5 hash function is used to map partition keys to 128-bit
     * integer values and to map associated data records to shards. As a
     * result of this hashing mechanism, all data records with the same
     * partition key map to the same shard within the stream. For more
     * information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-partition-key"> Partition Key </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * Each record in the <code>Records</code> array may include an optional
     * parameter, <code>ExplicitHashKey</code> , which overrides the
     * partition key to shard mapping. This parameter allows a data producer
     * to determine explicitly the shard where the record is stored. For more
     * information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-putrecords"> Adding Multiple Records with PutRecords </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * The <code>PutRecords</code> response includes an array of response
     * <code>Records</code> . Each record in the response array directly
     * correlates with a record in the request array using natural ordering,
     * from the top to the bottom of the request and response. The response
     * <code>Records</code> array always includes the same number of records
     * as the request array.
     * </p>
     * <p>
     * The response <code>Records</code> array includes both successfully
     * and unsuccessfully processed records. Amazon Kinesis attempts to
     * process all records in each <code>PutRecords</code> request. A single
     * record failure does not stop the processing of subsequent records.
     * </p>
     * <p>
     * A successfully-processed record includes <code>ShardId</code> and
     * <code>SequenceNumber</code> values. The <code>ShardId</code> parameter
     * identifies the shard in the stream where the record is stored. The
     * <code>SequenceNumber</code> parameter is an identifier assigned to the
     * put record, unique to all records in the stream.
     * </p>
     * <p>
     * An unsuccessfully-processed record includes <code>ErrorCode</code>
     * and <code>ErrorMessage</code> values. <code>ErrorCode</code> reflects
     * the type of error and can be one of the following values:
     * <code>ProvisionedThroughputExceededException</code> or
     * <code>InternalFailure</code> . <code>ErrorMessage</code> provides more
     * detailed information about the
     * <code>ProvisionedThroughputExceededException</code> exception
     * including the account ID, stream name, and shard ID of the record that
     * was throttled.
     * </p>
     * <p>
     * Data records are accessible for only 24 hours from the time that they
     * are added to an Amazon Kinesis stream.
     * </p>
     *
     * @param putRecordsRequest Container for the necessary parameters to
     *           execute the PutRecords service method on AmazonKinesis.
     * 
     * @return The response from the PutRecords service method, as returned
     *         by AmazonKinesis.
     * 
     * @throws ProvisionedThroughputExceededException
     * @throws InvalidArgumentException
     * @throws ResourceNotFoundException
     *
     * @throws AmazonClientException
     *             If any internal errors are encountered inside the client while
     *             attempting to make the request or handle the response.  For example
     *             if a network connection is not available.
     * @throws AmazonServiceException
     *             If an error response is returned by AmazonKinesis indicating
     *             either a problem with the data in the request, or a server side issue.
     */
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

            Unmarshaller<PutRecordsResult, JsonUnmarshallerContext> unmarshaller =
                new PutRecordsResultJsonUnmarshaller();
            JsonResponseHandler<PutRecordsResult> responseHandler =
                new JsonResponseHandler<PutRecordsResult>(unmarshaller);

            response = invoke(request, responseHandler, executionContext);

            return response.getAwsResponse();
        } finally {
            
            endClientExecution(awsRequestMetrics, request, response, LOGGING_AWS_REQUEST_METRIC);
        }
    }


    
    /**
     * <p>
     * Puts (writes) a single data record from a producer into an Amazon
     * Kinesis stream. Call <code>PutRecord</code> to send data from the
     * producer into the Amazon Kinesis stream for real-time ingestion and
     * subsequent processing, one record at a time. Each shard can support up
     * to 1000 records written per second, up to a maximum total of 1 MB data
     * written per second.
     * </p>
     * <p>
     * You must specify the name of the stream that captures, stores, and
     * transports the data; a partition key; and the data blob itself.
     * </p>
     * <p>
     * The data blob can be any type of data; for example, a segment from a
     * log file, geographic/location data, website clickstream data, and so
     * on.
     * </p>
     * <p>
     * The partition key is used by Amazon Kinesis to distribute data across
     * shards. Amazon Kinesis segregates the data records that belong to a
     * data stream into multiple shards, using the partition key associated
     * with each data record to determine which shard a given data record
     * belongs to.
     * </p>
     * <p>
     * Partition keys are Unicode strings, with a maximum length limit of
     * 256 bytes. An MD5 hash function is used to map partition keys to
     * 128-bit integer values and to map associated data records to shards
     * using the hash key ranges of the shards. You can override hashing the
     * partition key to determine the shard by explicitly specifying a hash
     * value using the <code>ExplicitHashKey</code> parameter. For more
     * information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-partition-key"> Partition Key </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * <code>PutRecord</code> returns the shard ID of where the data record
     * was placed and the sequence number that was assigned to the data
     * record.
     * </p>
     * <p>
     * Sequence numbers generally increase over time. To guarantee strictly
     * increasing ordering, use the <code>SequenceNumberForOrdering</code>
     * parameter. For more information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-sequence-number"> Sequence Number </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * If a <code>PutRecord</code> request cannot be processed because of
     * insufficient provisioned throughput on the shard involved in the
     * request, <code>PutRecord</code> throws
     * <code>ProvisionedThroughputExceededException</code> .
     * </p>
     * <p>
     * Data records are accessible for only 24 hours from the time that they
     * are added to an Amazon Kinesis stream.
     * </p>
     * 
     * @param streamName The name of the stream to put the data record into.
     * @param data The data blob to put into the record, which is
     * base64-encoded when the blob is serialized. The maximum size of the
     * data blob (the payload before base64-encoding) is 50 kilobytes (KB)
     * @param partitionKey Determines which shard in the stream the data
     * record is assigned to. Partition keys are Unicode strings with a
     * maximum length limit of 256 bytes. Amazon Kinesis uses the partition
     * key as input to a hash function that maps the partition key and
     * associated data to a specific shard. Specifically, an MD5 hash
     * function is used to map partition keys to 128-bit integer values and
     * to map associated data records to shards. As a result of this hashing
     * mechanism, all data records with the same partition key will map to
     * the same shard within the stream.
     * 
     * @return The response from the PutRecord service method, as returned by
     *         AmazonKinesis.
     * 
     * @throws ProvisionedThroughputExceededException
     * @throws InvalidArgumentException
     * @throws ResourceNotFoundException
     *
     * @throws AmazonClientException
     *             If any internal errors are encountered inside the client while
     *             attempting to make the request or handle the response.  For example
     *             if a network connection is not available.
     * @throws AmazonServiceException
     *             If an error response is returned by AmazonKinesis indicating
     *             either a problem with the data in the request, or a server side issue.
     */
    public PutRecordResult putRecord(String streamName, java.nio.ByteBuffer data, String partitionKey)
             throws AmazonServiceException, AmazonClientException  {
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setStreamName(streamName);
        putRecordRequest.setData(data);
        putRecordRequest.setPartitionKey(partitionKey);
        return putRecord(putRecordRequest);
    }
    
    /**
     * <p>
     * Puts (writes) a single data record from a producer into an Amazon
     * Kinesis stream. Call <code>PutRecord</code> to send data from the
     * producer into the Amazon Kinesis stream for real-time ingestion and
     * subsequent processing, one record at a time. Each shard can support up
     * to 1000 records written per second, up to a maximum total of 1 MB data
     * written per second.
     * </p>
     * <p>
     * You must specify the name of the stream that captures, stores, and
     * transports the data; a partition key; and the data blob itself.
     * </p>
     * <p>
     * The data blob can be any type of data; for example, a segment from a
     * log file, geographic/location data, website clickstream data, and so
     * on.
     * </p>
     * <p>
     * The partition key is used by Amazon Kinesis to distribute data across
     * shards. Amazon Kinesis segregates the data records that belong to a
     * data stream into multiple shards, using the partition key associated
     * with each data record to determine which shard a given data record
     * belongs to.
     * </p>
     * <p>
     * Partition keys are Unicode strings, with a maximum length limit of
     * 256 bytes. An MD5 hash function is used to map partition keys to
     * 128-bit integer values and to map associated data records to shards
     * using the hash key ranges of the shards. You can override hashing the
     * partition key to determine the shard by explicitly specifying a hash
     * value using the <code>ExplicitHashKey</code> parameter. For more
     * information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-partition-key"> Partition Key </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * <code>PutRecord</code> returns the shard ID of where the data record
     * was placed and the sequence number that was assigned to the data
     * record.
     * </p>
     * <p>
     * Sequence numbers generally increase over time. To guarantee strictly
     * increasing ordering, use the <code>SequenceNumberForOrdering</code>
     * parameter. For more information, see
     * <a href="http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html#kinesis-using-api-defn-sequence-number"> Sequence Number </a>
     * in the <i>Amazon Kinesis Developer Guide</i> .
     * </p>
     * <p>
     * If a <code>PutRecord</code> request cannot be processed because of
     * insufficient provisioned throughput on the shard involved in the
     * request, <code>PutRecord</code> throws
     * <code>ProvisionedThroughputExceededException</code> .
     * </p>
     * <p>
     * Data records are accessible for only 24 hours from the time that they
     * are added to an Amazon Kinesis stream.
     * </p>
     * 
     * @param streamName The name of the stream to put the data record into.
     * @param data The data blob to put into the record, which is
     * base64-encoded when the blob is serialized. The maximum size of the
     * data blob (the payload before base64-encoding) is 50 kilobytes (KB)
     * @param partitionKey Determines which shard in the stream the data
     * record is assigned to. Partition keys are Unicode strings with a
     * maximum length limit of 256 bytes. Amazon Kinesis uses the partition
     * key as input to a hash function that maps the partition key and
     * associated data to a specific shard. Specifically, an MD5 hash
     * function is used to map partition keys to 128-bit integer values and
     * to map associated data records to shards. As a result of this hashing
     * mechanism, all data records with the same partition key will map to
     * the same shard within the stream.
     * @param sequenceNumberForOrdering Guarantees strictly increasing
     * sequence numbers, for puts from the same client and to the same
     * partition key. Usage: set the <code>SequenceNumberForOrdering</code>
     * of record <i>n</i> to the sequence number of record <i>n-1</i> (as
     * returned in the <a>PutRecordResult</a> when putting record
     * <i>n-1</i>). If this parameter is not set, records will be coarsely
     * ordered based on arrival time.
     * 
     * @return The response from the PutRecord service method, as returned by
     *         AmazonKinesis.
     * 
     * @throws ProvisionedThroughputExceededException
     * @throws InvalidArgumentException
     * @throws ResourceNotFoundException
     *
     * @throws AmazonClientException
     *             If any internal errors are encountered inside the client while
     *             attempting to make the request or handle the response.  For example
     *             if a network connection is not available.
     * @throws AmazonServiceException
     *             If an error response is returned by AmazonKinesis indicating
     *             either a problem with the data in the request, or a server side issue.
     */
    public PutRecordResult putRecord(String streamName, java.nio.ByteBuffer data, String partitionKey, String sequenceNumberForOrdering)
             throws AmazonServiceException, AmazonClientException  {
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setStreamName(streamName);
        putRecordRequest.setData(data);
        putRecordRequest.setPartitionKey(partitionKey);
        putRecordRequest.setSequenceNumberForOrdering(sequenceNumberForOrdering);
        return putRecord(putRecordRequest);
    }
    
    @Override
    public void setEndpoint(String endpoint) {
        super.setEndpoint(endpoint);
    }

    private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request,
            HttpResponseHandler<AmazonWebServiceResponse<X>> responseHandler,
            ExecutionContext executionContext) {
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
        Response<X> result = client.execute(request, responseHandler,
                errorResponseHandler, executionContext);
        return result;
    }
}
