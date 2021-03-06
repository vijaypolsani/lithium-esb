/*
 * 
 */
package com.lithium.esb.compliance.beans;

import java.util.Optional;

import lithium.research.key.KeySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lithium.streams.compliance.client.IDecryption;
import com.lithium.streams.compliance.model.SecureEvent;
import com.lithium.streams.compliance.security.KeyServerProperties;
import com.lithium.streams.compliance.util.KeySourceUtil;

/**
 * The AesMessageDecryption provides the entry point to Decryption of the message by KeyServer
 */
public class AesMessageDecryption {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(AesMessageDecryption.class);

	/** The key source util. */
	@Autowired
	private KeySourceUtil keySourceUtil;

	/** The i decryption. */
	@Autowired
	private IDecryption iDecryption;

	/**
	 * Decrypt message.
	 *
	 * @param inputData the input data
	 * @return the secure event
	 */
	public SecureEvent decryptMessage(SecureEvent inputData) {
		final Optional<KeySource> source = keySourceUtil.getKeySource();
		if (source.isPresent()) {
			if (inputData != null) {
				log.info("***Encrypted Data length: " + inputData.getMessage().length);
				SecureEvent eventDecrypted = iDecryption.performMessageDecryption(inputData,
						KeyServerProperties.COMMUNITY_NAME.getValue(), source.get());
				log.info("***Decrypted Data length: " + eventDecrypted.getMessage().length);
				//log.info("***Decrypted Data: " + new String(eventDecrypted.getMessage()));
				return eventDecrypted;
			}
		}
		return inputData;
	}
}
