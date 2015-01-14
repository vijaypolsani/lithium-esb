package com.lithium.esb.compliance.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.lithium.esb.compliance.model.LswEvent;

/**
 * The Spring Data repository for storing and retrieving data from ES.
 */
public interface LswEventContentRepository extends ElasticsearchRepository<LswEvent, String> {

}
