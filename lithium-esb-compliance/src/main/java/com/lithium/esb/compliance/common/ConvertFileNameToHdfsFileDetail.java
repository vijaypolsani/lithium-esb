package com.lithium.esb.compliance.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.model.HdfsFileDetail;

/**
 * The Class ConvertFileNameToHdfsFileDetail reads the file name and formats to the ES model strucutre so as to store and 
 * Query for details later for updates on the status.
 */
public class ConvertFileNameToHdfsFileDetail implements Processor {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ConvertFileNameToHdfsFileDetail.class);

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		Set<String> listOfFiles = (Set) message.getBody();
		message.setMessageId(UUID.randomUUID().toString());
		Set<HdfsFileDetail> updateToEs = new HashSet<>();
		for (String fileName : listOfFiles) {
			//INFO: We are using filename with directory structure as ID. (ex: /int/in.log)
			updateToEs.add(new HdfsFileDetail(fileName, fileName, "HdfsFile read from Integrations Modules.", false,
					false));
		}
		message.setBody(updateToEs);
		exchange.setOut(message);
		log.info(">>> Completed sending multiple fileNames total= " + listOfFiles.size());
	}
}