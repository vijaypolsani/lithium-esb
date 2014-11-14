package com.lithium.esb.compliance.model;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "hdfsfilecontent", type = "hdfsfilecontent", shards = 1, replicas = 0, indexStoreType = "memory")
public class ActivityStreamV1 {

	@Id
	private String frameId = "";
	private String fileSource = "";
	private String title = "";
	private String published = "";
	private String verb = "";
	private static final AtomicLong sequenceNumber = new AtomicLong(1);

	public ActivityStreamV1(String id) {
		if (id == null)
			frameId = String.valueOf(sequenceNumber.getAndIncrement());
		else
			frameId = id;
	}

	public String getFrameId() {
		return frameId;
	}

	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileSource() {
		return fileSource;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

}