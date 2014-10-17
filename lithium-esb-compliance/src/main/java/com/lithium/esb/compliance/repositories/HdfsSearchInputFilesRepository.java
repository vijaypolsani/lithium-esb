package com.lithium.esb.compliance.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lithium.esb.compliance.model.HdfsFileDetail;

public interface HdfsSearchInputFilesRepository extends ElasticsearchRepository<HdfsFileDetail, String> {

	public List<HdfsFileDetail> findByName(String fileName);

	public List<HdfsFileDetail> findByName(String fileName, Pageable pageable);

	public List<HdfsFileDetail> findByNameAndId(String fileName, String id);

	public Set<HdfsFileDetail> findByFileOpenedAndFileRead(boolean fileOpened, boolean fileRead);

}
