package com.lithium.esb.compliance.lsw.api;

import java.util.List;

import com.lithium.streams.compliance.model.SecureEvent;

public interface LswSqsOutbound extends LswQueue {
	public List<SecureEvent> consumeMessages();
}
