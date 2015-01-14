package com.lithium.esb.compliance.model;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * The ES model containing the table structure. If the SequenceNumber is missing, then a internal running sequence
 * number will be created for use.
 */
@Document(indexName = "hdfsfilecontent", type = "hdfsfilecontent", shards = 1, replicas = 0, indexStoreType = "memory")
//@Document(indexName = "hdfsfilecontent", type = "hdfsfilecontent", shards = 1, replicas = 0, indexStoreType = "default")
public class ActivityStreamV1 {

	/** The id. */
	@Id
	private String id = "";
	
	/** The frame id. */
	private String frameId = "";
	
	/** The type. */
	private String type = "";
	
	/** The published. */
	private String published = "";
	
	/** The version. */
	private String version = "";
	
	/** The file source. */
	private String fileSource = "";
	
	/** The Constant sequenceNumber. */
	private static final AtomicLong sequenceNumber = new AtomicLong(1);

	/**
	 * Instantiates a new activity stream v1.
	 *
	 * @param id the id
	 */
	public ActivityStreamV1(String id) {
		if (id == null)
			frameId = String.valueOf(sequenceNumber.getAndIncrement());
		else
			frameId = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the frame id.
	 *
	 * @return the frame id
	 */
	public String getFrameId() {
		return frameId;
	}

	/**
	 * Sets the frame id.
	 *
	 * @param frameId the new frame id
	 */
	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	/**
	 * Sets the file source.
	 *
	 * @param fileSource the new file source
	 */
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	/**
	 * Gets the file source.
	 *
	 * @return the file source
	 */
	public String getFileSource() {
		return fileSource;
	}

	/**
	 * Gets the published.
	 *
	 * @return the published
	 */
	public String getPublished() {
		return published;
	}

	/**
	 * Sets the published.
	 *
	 * @param published the new published
	 */
	public void setPublished(String published) {
		this.published = published;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ActivityStreamV1 [id=" + id + ", frameId=" + frameId + ", type=" + type + ", published=" + published
				+ ", version=" + version + ", fileSource=" + fileSource + "]";
	}

}