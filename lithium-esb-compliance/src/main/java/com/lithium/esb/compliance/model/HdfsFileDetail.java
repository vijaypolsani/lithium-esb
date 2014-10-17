package com.lithium.esb.compliance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "hdfsfiledetails", type = "hdfsfiledetail", shards = 1, replicas = 0, indexStoreType = "memory")
public class HdfsFileDetail {

	@Id
	private String id;
	private String name;
	private String description;
	private boolean fileOpened;
	private boolean fileRead;

	public HdfsFileDetail() {
	}

	public HdfsFileDetail(String id, String name, String description, boolean fileOpened, boolean fileRead) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileOpened = fileOpened;
		this.fileRead = fileRead;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFileOpened() {
		return fileOpened;
	}

	public void setFileOpened(boolean fileOpened) {
		this.fileOpened = fileOpened;
	}

	public boolean isFileRead() {
		return fileRead;
	}

	public void setFileRead(boolean fileRead) {
		this.fileRead = fileRead;
	}

	@Override
	public String toString() {
		return "HdfsFileDetail [id=" + id + ", name=" + name + ", description=" + description + ", fileOpened="
				+ fileOpened + ", fileRead=" + fileRead + "]";
	}

}
