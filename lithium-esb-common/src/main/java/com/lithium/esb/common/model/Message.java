package com.lithium.esb.common.model;

import java.io.Serializable;

public interface Message<MessageType> extends Serializable, Comparable<MessageType>, Cloneable{
	abstract MessageType getPayload();
	abstract MessageHeaders getMessageHeaders();
	abstract MessageFooters getMessageFooters();
}
