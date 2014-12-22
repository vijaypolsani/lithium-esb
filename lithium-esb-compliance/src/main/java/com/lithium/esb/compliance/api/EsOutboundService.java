package com.lithium.esb.compliance.api;

import java.util.Collection;
import java.util.Set;

import com.lithium.esb.compliance.model.ActivityStreamV1;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.model.LswEvent;

public interface EsOutboundService {

	public void insertBatchFilesInfo(Set<HdfsFileDetail> hdfsFileDetails);

	public void insertFileInfo(HdfsFileDetail hdfsFileDetail);

	public void insertBatchActivityStreamInfo(Set<ActivityStreamV1> activityStreamV1);

	public void insertActivityStreamInfo(ActivityStreamV1 activityStreamV1);

	public void updateFilesInfo(Set<HdfsFileDetail> hdfsFilesDetails);

	public void updateFileInfo(HdfsFileDetail hdfsFileDetail);

	public void updateFileOpenedStatus(String id, boolean fileOpened);

	public void updateFileReadStatus(String id, boolean fileRead);

	public void updateFileKafkaSentStatus(String id, boolean mqSent);

	public void updateFileOpenedAndFileReadStatus(String id, boolean fileOpened, boolean fileRead);

	void dropAllFileDetailData();

	public void insertBatchLswEvent(Collection<LswEvent> lswEvents);

	public void insertLswEvent(LswEvent lswEvent);

}
