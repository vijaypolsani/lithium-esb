package com.lithium.esb.common.task;

import java.util.concurrent.Callable;

import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;

public class SendRecord implements Callable<PutRecordResult> {
	
	private final PutRecordRequest putRecordRequest;
	
	public SendRecord(PutRecordRequest putRecordRequest){
		this.putRecordRequest = putRecordRequest;
	}
	
	public PutRecordResult call() throws Exception {
		
		return null;		
	}
}
