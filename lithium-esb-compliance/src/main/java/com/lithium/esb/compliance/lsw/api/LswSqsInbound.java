package com.lithium.esb.compliance.lsw.api;

public interface LswSqsInbound extends LswQueue{
	public void sendSqsMessage(String message);
	public void sendSampleSqsMessage();
}
