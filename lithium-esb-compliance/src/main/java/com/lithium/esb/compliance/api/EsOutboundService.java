package com.lithium.esb.compliance.api;

import java.util.Set;

import com.lithium.esb.compliance.model.HdfsFileDetail;

public interface EsOutboundService {

	public void insertFileInfo(Set<HdfsFileDetail> hdfsFileDetails);

	public void insertFileInfo(HdfsFileDetail hdfsFileDetail);

	public void updateFilesInfo(Set<HdfsFileDetail> hdfsFilesDetails);

	public void updateFileInfo(HdfsFileDetail hdfsFileDetail);

	public void dropAllFileDetailData();

}
