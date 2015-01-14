/*
 * Compliance Real Time Streaming Service
 */
package com.lithium.esb.compliance.api;

import java.util.Collection;
import java.util.Set;

import com.lithium.esb.compliance.model.ActivityStreamV1;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.model.LswEvent;

/**
 * The Interface EsOutboundService provides API for ElasticSearch insert/update calls from Spring Data
 */
public interface EsOutboundService {

	/**
	 * Insert batch files info.
	 *
	 * @param hdfsFileDetails the hdfs file details
	 */
	public void insertBatchFilesInfo(Set<HdfsFileDetail> hdfsFileDetails);

	/**
	 * Insert file info.
	 *
	 * @param hdfsFileDetail the hdfs file detail
	 */
	public void insertFileInfo(HdfsFileDetail hdfsFileDetail);

	/**
	 * Insert batch activity stream info.
	 *
	 * @param activityStreamV1 the activity stream v1
	 */
	public void insertBatchActivityStreamInfo(Set<ActivityStreamV1> activityStreamV1);

	/**
	 * Insert activity stream info.
	 *
	 * @param activityStreamV1 the activity stream v1
	 */
	public void insertActivityStreamInfo(ActivityStreamV1 activityStreamV1);

	/**
	 * Update files info.
	 *
	 * @param hdfsFilesDetails the hdfs files details
	 */
	public void updateFilesInfo(Set<HdfsFileDetail> hdfsFilesDetails);

	/**
	 * Update file info.
	 *
	 * @param hdfsFileDetail the hdfs file detail
	 */
	public void updateFileInfo(HdfsFileDetail hdfsFileDetail);

	/**
	 * Update file opened status.
	 *
	 * @param id the id
	 * @param fileOpened the file opened
	 */
	public void updateFileOpenedStatus(String id, boolean fileOpened);

	/**
	 * Update file read status.
	 *
	 * @param id the id
	 * @param fileRead the file read
	 */
	public void updateFileReadStatus(String id, boolean fileRead);

	/**
	 * Update file kafka sent status.
	 *
	 * @param id the id
	 * @param mqSent the mq sent
	 */
	public void updateFileKafkaSentStatus(String id, boolean mqSent);

	/**
	 * Update file opened and file read status.
	 *
	 * @param id the id
	 * @param fileOpened the file opened
	 * @param fileRead the file read
	 */
	public void updateFileOpenedAndFileReadStatus(String id, boolean fileOpened, boolean fileRead);

	/**
	 * Drop all file detail data.
	 */
	void dropAllFileDetailData();

	/**
	 * Insert batch lsw event.
	 *
	 * @param lswEvents the lsw events
	 */
	public void insertBatchLswEvent(Collection<LswEvent> lswEvents);

	/**
	 * Insert lsw event.
	 *
	 * @param lswEvent the lsw event
	 */
	public void insertLswEvent(LswEvent lswEvent);

}
