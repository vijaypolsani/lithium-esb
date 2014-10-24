package com.lithium.esb.compliance.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.lithium.esb.compliance.model.HdfsFileDetail;

public class ConvertFileNameToModel implements Processor {
	private static final Logger log = LoggerFactory.getLogger(ConvertFileNameToModel.class);

	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		log.info(">>> Inbound message : " + exchange);
		Set<String> listOfFiles = (Set) message.getBody();
		message.setMessageId(UUID.randomUUID().toString());
		Set<HdfsFileDetail> updateToEs = new HashSet<>();
		for (String fileName : listOfFiles) {
			updateToEs.add(new HdfsFileDetail(null, fileName, "HdfsFile Read from Integrations.", false, false));
		}
		message.setBody(updateToEs);
		exchange.setOut(message);
		log.info(">>> Completed sending multiple fileNames: " + listOfFiles);
	}
}