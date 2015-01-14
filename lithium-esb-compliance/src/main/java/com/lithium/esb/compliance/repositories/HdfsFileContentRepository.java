package com.lithium.esb.compliance.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lithium.esb.compliance.model.ActivityStreamV1;

/**
 * The Spring Data Repository for HdfsFileContentRepository. An extension to a simple tagging interface 
 * will help in configuring the data into ES.
 */
public interface HdfsFileContentRepository extends ElasticsearchRepository<ActivityStreamV1, String> {

}
