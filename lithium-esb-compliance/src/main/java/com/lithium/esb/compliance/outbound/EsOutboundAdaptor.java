package com.lithium.esb.compliance.outbound;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lithium.esb.compliance.api.EsOutboundService;
import com.lithium.esb.compliance.model.ActivityStreamV1;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.model.LswEvent;
import com.lithium.esb.compliance.repositories.HdfsFileContentRepository;
import com.lithium.esb.compliance.repositories.HdfsSearchInputFilesRepository;
import com.lithium.esb.compliance.repositories.LswEventContentRepository;

/**
 * The Es OutboundAdaptor is an implementation of methods for writing and updating data contents into ES.
 * LIA File status is read and constantly updated for status changes and phase of life
 */
@Service
public class EsOutboundAdaptor implements EsOutboundService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(EsOutboundAdaptor.class);

	/** The hdfs search input files repository. */
	@Autowired
	private HdfsSearchInputFilesRepository hdfsSearchInputFilesRepository;

	/** The hdfs file content repository. */
	@Autowired
	private HdfsFileContentRepository hdfsFileContentRepository;

	/** The lsw event content repository. */
	@Autowired
	private LswEventContentRepository lswEventContentRepository;

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertBatchFilesInfo(java.util.Set)
	 */
	@Override
	public void insertBatchFilesInfo(Set<HdfsFileDetail> hdfsFileDetails) {
		if (hdfsFileDetails.size() > 0)
			hdfsSearchInputFilesRepository.save(hdfsFileDetails);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertFileInfo(com.lithium.esb.compliance.model.HdfsFileDetail)
	 */
	@Override
	public void insertFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.save(hdfsFileDetail);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFilesInfo(java.util.Set)
	 */
	@Override
	public void updateFilesInfo(Set<HdfsFileDetail> hdfsFilesDetails) {
		log.info(">>> New Files list recieved to be persisted into ES. total: " + hdfsFilesDetails.size());
		for (HdfsFileDetail hdfsFileDetail : hdfsFilesDetails) {
			if (!(hdfsSearchInputFilesRepository.exists(hdfsFileDetail.getId())))
				hdfsSearchInputFilesRepository.index(hdfsFileDetail);
			//else
			//log.info(">>> File already exits in ES: " + hdfsFileDetail);

		}

	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFileInfo(com.lithium.esb.compliance.model.HdfsFileDetail)
	 */
	@Override
	public void updateFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.save(hdfsFileDetail);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#dropAllFileDetailData()
	 */
	@Override
	public void dropAllFileDetailData() {
		hdfsSearchInputFilesRepository.deleteAll();
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFileOpenedStatus(java.lang.String, boolean)
	 */
	@Override
	public void updateFileOpenedStatus(String id, boolean fileOpened) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setFileOpened(fileOpened);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFileReadStatus(java.lang.String, boolean)
	 */
	@Override
	public void updateFileReadStatus(String id, boolean fileRead) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setFileRead(fileRead);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFileKafkaSentStatus(java.lang.String, boolean)
	 */
	@Override
	public void updateFileKafkaSentStatus(String id, boolean mqSent) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setMqSent(mqSent);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);

	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#updateFileOpenedAndFileReadStatus(java.lang.String, boolean, boolean)
	 */
	@Override
	public void updateFileOpenedAndFileReadStatus(String id, boolean fileOpened, boolean fileRead) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setFileOpened(fileRead);
			hdfsFileDetail.setFileOpened(fileOpened);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertBatchActivityStreamInfo(java.util.Set)
	 */
	@Override
	public void insertBatchActivityStreamInfo(Set<ActivityStreamV1> activityStreamV1) {
		if (activityStreamV1.size() > 0)
			hdfsFileContentRepository.save(activityStreamV1);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertActivityStreamInfo(com.lithium.esb.compliance.model.ActivityStreamV1)
	 */
	@Override
	public void insertActivityStreamInfo(ActivityStreamV1 activityStreamV1) {
		hdfsFileContentRepository.index(activityStreamV1);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertBatchLswEvent(java.util.Collection)
	 */
	@Override
	public void insertBatchLswEvent(Collection<LswEvent> lswEvents) {
		if (lswEvents.size() > 0)
			lswEventContentRepository.save(lswEvents);
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsOutboundService#insertLswEvent(com.lithium.esb.compliance.model.LswEvent)
	 */
	@Override
	public void insertLswEvent(LswEvent lswEvent) {
		lswEventContentRepository.save(lswEvent);
	}

}
