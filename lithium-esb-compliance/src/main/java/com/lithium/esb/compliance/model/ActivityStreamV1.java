package com.lithium.esb.compliance.model;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "hdfsfilecontent", type = "hdfsfilecontent", shards = 1, replicas = 0, indexStoreType = "memory")
//@Document(indexName = "hdfsfilecontent", type = "hdfsfilecontent", shards = 1, replicas = 0, indexStoreType = "default")
public class ActivityStreamV1 {

	@Id
	private String id = "";
	private String frameId = "";
	private String type = "";
	private String published = "";
	private String version = "";
	private String fileSource = "";
	private static final AtomicLong sequenceNumber = new AtomicLong(1);

	public ActivityStreamV1(String id) {
		if (id == null)
			frameId = String.valueOf(sequenceNumber.getAndIncrement());
		else
			frameId = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ActivityStreamV1 [id=" + id + ", frameId=" + frameId + ", type=" + type + ", published=" + published
				+ ", version=" + version + ", fileSource=" + fileSource + "]";
	}

}