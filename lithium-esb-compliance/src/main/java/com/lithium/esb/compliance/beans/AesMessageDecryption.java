package com.lithium.esb.compliance.beans;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import lithium.research.key.KeySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lithium.streams.compliance.client.IDecryption;
import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;
import com.lithium.streams.compliance.security.KeyServerProperties;
import com.lithium.streams.compliance.util.KeySourceUtil;

public class AesMessageDecryption {

	private static final Logger log = LoggerFactory.getLogger(AesMessageDecryption.class);

	@Autowired
	private KeySourceUtil keySourceUtil;

	@Autowired
	private IDecryption iDecryption;

	public byte[] decryptMessage(String inputData) {
		final SecureEvent eventEncrypted = new Payload(inputData.getBytes(StandardCharsets.UTF_8));

		final Optional<KeySource> source = keySourceUtil.getKeySource();
		if (source.isPresent()) {
			if (eventEncrypted != null) {
				log.info("***Encrypted Data length: " + eventEncrypted.getMessage().length);
				SecureEvent eventDecrypted = iDecryption.performMessageDecryption(eventEncrypted,
						KeyServerProperties.COMMUNITY_NAME.getValue(), source.get());
				log.info("***Decrypted Data length: " + eventDecrypted.getMessage().length);
				log.info("***Decrypted Data: " + new String(eventDecrypted.getMessage()));
				return eventDecrypted.getMessage();
			}
		}
		return eventEncrypted.getMessage();
	}

}
