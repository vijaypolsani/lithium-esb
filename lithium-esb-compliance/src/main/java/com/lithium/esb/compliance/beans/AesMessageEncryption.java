package com.lithium.esb.compliance.beans;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import lithium.research.key.KeySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lithium.streams.compliance.client.IEncryption;
import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;
import com.lithium.streams.compliance.security.KeyServerProperties;
import com.lithium.streams.compliance.util.KeySourceUtil;

public class AesMessageEncryption {

	private static final Logger log = LoggerFactory.getLogger(AesMessageEncryption.class);

	@Autowired
	private KeySourceUtil keySourceUtil;

	@Autowired
	private IEncryption iEncryption;

	public SecureEvent encryptMessage(SecureEvent inputData) {
		//final SecureEvent inputData = new Payload("THIS IS A TEST MESSAGE. HARD CODED IN CODE.".getBytes(StandardCharsets.UTF_8));
		final Optional<KeySource> source = keySourceUtil.getKeySource();
		if (source.isPresent()) {
			if (inputData != null) {
				log.info("***Un-encrypted Data length: " + inputData.getMessage().length);
				//log.info("***Un-encrypted Data : " + new String(inputData.getMessage()));
				SecureEvent eventEncrypted = iEncryption.performMessageEncryption(inputData,
						KeyServerProperties.COMMUNITY_NAME.getValue(), source.get());
				log.info("***Encrypted Data length: " + eventEncrypted.getMessage().length);
				//log.info("***Encrypted Data : " + new String(eventEncrypted.getMessage()));
				return eventEncrypted;
			}
		}
		log.error("***Could not encrypt message. Returning original.");
		return inputData;
	}
}
