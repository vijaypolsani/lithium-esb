package com.lithium.esb.compliance.outbound;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lithium.esb.compliance.api.EsOutboundService;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.repositories.HdfsFileContentRepository;
import com.lithium.esb.compliance.repositories.HdfsSearchInputFilesRepository;
import com.lithium.esb.compliance.model.ActivityStreamV1;

@Service
public class EsOutboundAdaptor implements EsOutboundService {
	private static final Logger log = LoggerFactory.getLogger(EsOutboundAdaptor.class);
	@Autowired
	private HdfsSearchInputFilesRepository hdfsSearchInputFilesRepository;
	@Autowired
	private HdfsFileContentRepository hdfsFileContentRepository;

	@Override
	public void insertBatchFilesInfo(Set<HdfsFileDetail> hdfsFileDetails) {
		for (HdfsFileDetail hdfsFileDetail : hdfsFileDetails) {
			hdfsSearchInputFilesRepository.index(hdfsFileDetail);
		}
	}

	@Override
	public void insertFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.index(hdfsFileDetail);
	}

	@Override
	public void updateFilesInfo(Set<HdfsFileDetail> hdfsFilesDetails) {
		log.info(">>> New Files list recieved to be persisted into ES: " + hdfsFilesDetails);
		for (HdfsFileDetail hdfsFileDetail : hdfsFilesDetails) {
			if (!(hdfsSearchInputFilesRepository.exists(hdfsFileDetail.getId())))
				hdfsSearchInputFilesRepository.save(hdfsFileDetail);
			else
				log.info(">>> File already exits in ES: " + hdfsFileDetail);

		}
		log.info(">>> Completed save: ");

	}

	@Override
	public void updateFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.save(hdfsFileDetail);
	}

	@Override
	public void dropAllFileDetailData() {
		hdfsSearchInputFilesRepository.deleteAll();
	}

	@Override
	public void updateFileOpenedStatus(String id, boolean fileOpened) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setFileOpened(fileOpened);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);
	}

	@Override
	public void updateFileReadStatus(String id, boolean fileRead) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setFileRead(fileRead);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);
	}

	@Override
	public void updateFileKafkaSentStatus(String id, boolean mqSent) {
		if (hdfsSearchInputFilesRepository.exists(id)) {
			HdfsFileDetail hdfsFileDetail = hdfsSearchInputFilesRepository.findOne(id);
			hdfsFileDetail.setMqSent(mqSent);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		} else
			log.info(">>> File with name does not exist in ES: " + id);

	}

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

	@Override
	public void insertBatchActivityStreamInfo(Set<ActivityStreamV1> activityStreamV1) {
		for (ActivityStreamV1 asV1 : activityStreamV1) {
			hdfsFileContentRepository.index(asV1);
		}

	}

	@Override
	public void insertActivityStreamInfo(ActivityStreamV1 activityStreamV1) {
		hdfsFileContentRepository.index(activityStreamV1);
	}

}
