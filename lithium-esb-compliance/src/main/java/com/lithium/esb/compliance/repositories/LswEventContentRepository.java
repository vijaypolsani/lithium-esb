package com.lithium.esb.compliance.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.lithium.esb.compliance.model.LswEvent;

public interface LswEventContentRepository extends ElasticsearchRepository<LswEvent, String> {

}
