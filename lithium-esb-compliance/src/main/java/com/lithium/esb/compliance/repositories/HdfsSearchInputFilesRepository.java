package com.lithium.esb.compliance.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lithium.esb.compliance.model.HdfsFileDetail;

/**
 * The Spring Data Repository for HdfsSearchInputFilesRepository. Contains custom methods other than
 * the regular ones for storing and reading data from ES.
 */
public interface HdfsSearchInputFilesRepository extends ElasticsearchRepository<HdfsFileDetail, String> {

	/**
	 * Find by name.
	 *
	 * @param fileName the file name
	 * @return the list
	 */
	public List<HdfsFileDetail> findByName(String fileName);

	/**
	 * Find by name.
	 *
	 * @param fileName the file name
	 * @param pageable the pageable
	 * @return the list
	 */
	public List<HdfsFileDetail> findByName(String fileName, Pageable pageable);

	/**
	 * Find by name and id.
	 *
	 * @param fileName the file name
	 * @param id the id
	 * @return the list
	 */
	public List<HdfsFileDetail> findByNameAndId(String fileName, String id);

	/**
	 * Find by file opened and file read.
	 *
	 * @param fileOpened the file opened
	 * @param fileRead the file read
	 * @return the sets the
	 */
	public Set<HdfsFileDetail> findByFileOpenedAndFileRead(boolean fileOpened, boolean fileRead);

}
