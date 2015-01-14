package com.lithium.esb.compliance.inbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lithium.esb.compliance.api.EsInboundService;
import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.repositories.HdfsSearchInputFilesRepository;

// TODO: Auto-generated Javadoc
/**
 * Inbound Adaptor to read the data from ES. The methods provide access to the storage.
 */
@Service
public class EsInboundAdaptor implements EsInboundService {

	/** The hdfs search input files repository. */
	@Autowired
	private HdfsSearchInputFilesRepository hdfsSearchInputFilesRepository;

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsInboundService#getAllLatestFilesInfo()
	 */
	@Override
	public Collection<String> getAllLatestFilesInfo() throws IOException {
		Iterable<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findAll();
		Iterator<HdfsFileDetail> hdfsInputFilesIter = hdfsInputFiles.iterator();
		Collection<String> listOfFiles = new ArrayList<>();
		while (hdfsInputFilesIter.hasNext()) {
			listOfFiles.add(hdfsInputFilesIter.next().getName());
		}
		return listOfFiles;
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsInboundService#getMatchingFileNameSearch(java.lang.String)
	 */
	@Override
	public Collection<String> getMatchingFileNameSearch(String fileName) {
		Iterable<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findAll();
		Iterator<HdfsFileDetail> hdfsInputFilesIter = hdfsInputFiles.iterator();
		Collection<String> listOfFiles = new ArrayList<>();
		while (hdfsInputFilesIter.hasNext()) {
			listOfFiles.add(hdfsInputFilesIter.next().getName());
		}
		return listOfFiles;
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsInboundService#getAllFileOpenedStatus(boolean)
	 */
	@Override
	public Collection<String> getAllFileOpenedStatus(boolean fileOpened) {
		Set<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByFileOpenedAndFileRead(fileOpened,
				false);
		Collection<String> listOfFiles = new ArrayList<>();
		for (HdfsFileDetail HdfsFileDetail : hdfsInputFiles) {
			listOfFiles.add(HdfsFileDetail.getName());
		}
		return listOfFiles;
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsInboundService#getAllFileReadStatus(boolean)
	 */
	@Override
	public Collection<String> getAllFileReadStatus(boolean fileRead) {
		Set<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository
				.findByFileOpenedAndFileRead(false, fileRead);
		Collection<String> listOfFiles = new ArrayList<>();
		for (HdfsFileDetail HdfsFileDetail : hdfsInputFiles) {
			listOfFiles.add(HdfsFileDetail.getName());
		}
		return listOfFiles;
	}

	/* (non-Javadoc)
	 * @see com.lithium.esb.compliance.api.EsInboundService#getAllFileReadAndFileOpenedStatus(boolean, boolean)
	 */
	@Override
	public Collection<String> getAllFileReadAndFileOpenedStatus(boolean fileRead, boolean fileOpened) {
		Set<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByFileOpenedAndFileRead(fileRead,
				fileOpened);
		Collection<String> listOfFiles = new ArrayList<>();
		for (HdfsFileDetail HdfsFileDetail : hdfsInputFiles) {
			listOfFiles.add(HdfsFileDetail.getName());
		}
		return listOfFiles;
	}
}
