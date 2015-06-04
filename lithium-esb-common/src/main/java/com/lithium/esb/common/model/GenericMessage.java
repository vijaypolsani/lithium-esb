package com.lithium.esb.common.model;

public class GenericMessage<MessageType> implements Message<MessageType>{

	private static final long serialVersionUID = -1481201590049787616L;
	private final MessageType payload;
	private final MessageHeaders headers;
	private final MessageFooters footers;
	
	public GenericMessage(MessageType payload){
		this.payload = payload;
		this.headers = new MessageHeaders();
		this.footers = new MessageFooters();
	}
	public GenericMessage(MessageType payload,MessageHeaders headers ){
		this.payload = payload;
		this.headers = new MessageHeaders();
		this.footers = new MessageFooters();
	}
	public GenericMessage(MessageType payload, MessageHeaders headers, MessageFooters footers){
		this.payload = payload;
		this.headers = new MessageHeaders();
		this.footers = new MessageFooters();
	}
	@Override
	public int compareTo(MessageType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MessageType getPayload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageHeaders getMessageHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageFooters getMessageFooters() {
		// TODO Auto-generated method stub
		return null;
	}

}
