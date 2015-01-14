package com.lithium.esb.compliance.beans;

import java.util.Optional;

import lithium.research.key.KeySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lithium.streams.compliance.client.IEncryption;
import com.lithium.streams.compliance.model.SecureEvent;
import com.lithium.streams.compliance.security.KeyServerProperties;
import com.lithium.streams.compliance.util.KeySourceUtil;

/**
 * The AesMessageEncryption provides an entry point to KeyServer decryption mechanism.
 */
public class AesMessageEncryption {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(AesMessageEncryption.class);

	/** The key source util. */
	@Autowired
	private KeySourceUtil keySourceUtil;

	/** The i encryption. */
	@Autowired
	private IEncryption iEncryption;

	/**
	 * Encrypt message.
	 *
	 * @param inputData the input data
	 * @return the secure event
	 */
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
