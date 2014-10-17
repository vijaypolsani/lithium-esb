package com.lithium.esb.compliance.outbound;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lithium.esb.compliance.api.EsOutboundService;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.repositories.HdfsSearchInputFilesRepository;

@Service
public class EsOutboundAdaptor implements EsOutboundService {

	@Autowired
	private HdfsSearchInputFilesRepository hdfsSearchInputFilesRepository;

	@Override
	public void insertFileInfo(Set<HdfsFileDetail> hdfsFileDetails) {
		for (HdfsFileDetail hdfsFileDetail : hdfsFileDetails) {
			hdfsSearchInputFilesRepository.index(hdfsFileDetail);
		}
	}

	@Override
	public void insertFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.index(hdfsFileDetail);
	}

	@Override
	public void updateFileInfo(Set<HdfsFileDetail> hdfsFileDetails) {
		for (HdfsFileDetail hdfsFileDetail : hdfsFileDetails) {
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		}
	}

	@Override
	public void updateFileInfo(HdfsFileDetail hdfsFileDetail) {
		hdfsSearchInputFilesRepository.save(hdfsFileDetail);
	}

	@Override
	public void dropAllFileDetailData() {
		hdfsSearchInputFilesRepository.deleteAll();
	}

}
