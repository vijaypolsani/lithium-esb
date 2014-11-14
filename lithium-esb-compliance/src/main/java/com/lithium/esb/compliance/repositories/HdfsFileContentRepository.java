package com.lithium.esb.compliance.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lithium.esb.compliance.model.ActivityStreamV1;

public interface HdfsFileContentRepository extends ElasticsearchRepository<ActivityStreamV1, String> {

}
