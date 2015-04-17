package com.lithium.esb.moderation.lsw.api;

import java.util.List;

import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

public interface LswTopicOutbound extends LswTopic {
	public PutRecordResult sendStreamMessage(String message);

	public PutRecordsResult sendStreamMessages(List<PutRecordsRequestEntry> messages);
}