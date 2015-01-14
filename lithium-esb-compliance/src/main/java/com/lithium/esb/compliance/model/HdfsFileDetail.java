package com.lithium.esb.compliance.model;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * The HdfsFileDetail contains information for storing file statues.
 * File is opened and then read and then MQ Sent. During the process, corresponding status are updated in the table 
 * with true/false
 * File Opened = Opened and in-process for reading the file content
 * File Read = Completed reading the file content.
 * MQ Sent = Sent to Kafka MQ for processing.
 */
@Document(indexName = "hdfsfiledetails", type = "hdfsfiledetail", shards = 1, replicas = 0, indexStoreType = "memory")
//@Document(indexName = "hdfsfiledetails", type = "hdfsfiledetails", shards = 1, replicas = 0 , indexStoreType = "default")
public class HdfsFileDetail {

	/** The id. */
	@Id
	private String id;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The file opened. */
	private boolean fileOpened;
	
	/** The file read. */
	private boolean fileRead;
	
	/** The mq sent. */
	private boolean mqSent;
	
	/** The Constant sequenceNumber. */
	private static final AtomicLong sequenceNumber = new AtomicLong(1);

	/**
	 * Instantiates a new hdfs file detail.
	 */
	public HdfsFileDetail() {
	}

	/**
	 * Instantiates a new hdfs file detail.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param fileOpened the file opened
	 * @param fileRead the file read
	 */
	public HdfsFileDetail(String id, String name, String description, boolean fileOpened, boolean fileRead) {
		this();
		if (id == null)
			this.id = String.valueOf(sequenceNumber.getAndIncrement());
		else
			this.id = id;
		this.name = name;
		this.description = description;
		this.fileOpened = fileOpened;
		this.fileRead = fileRead;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is file opened.
	 *
	 * @return true, if is file opened
	 */
	public boolean isFileOpened() {
		return fileOpened;
	}

	/**
	 * Sets the file opened.
	 *
	 * @param fileOpened the new file opened
	 */
	public void setFileOpened(boolean fileOpened) {
		this.fileOpened = fileOpened;
	}

	/**
	 * Checks if is file read.
	 *
	 * @return true, if is file read
	 */
	public boolean isFileRead() {
		return fileRead;
	}

	/**
	 * Sets the file read.
	 *
	 * @param fileRead the new file read
	 */
	public void setFileRead(boolean fileRead) {
		this.fileRead = fileRead;
	}

	/**
	 * Checks if is mq sent.
	 *
	 * @return the kafkaSent
	 */
	public boolean isMqSent() {
		return mqSent;
	}

	/**
	 * Sets the mq sent.
	 *
	 * @param mqSent the new mq sent
	 */
	public void setMqSent(boolean mqSent) {
		this.mqSent = mqSent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HdfsFileDetail [id=" + id + ", name=" + name + ", description=" + description + ", fileOpened="
				+ fileOpened + ", fileRead=" + fileRead + ", kafkaSent=" + mqSent + "]";
	}

}
