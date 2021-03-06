package com.lithium.esb.compliance.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lithium.esb.compliance.model.LswEvent;

/**
 * The unmarshall class for LSW object model definition.
 */
public class UnmarshallToLswEvent {
	
	/** The Constant mapper. */
	private final static ObjectMapper mapper = new ObjectMapper();
	
	/** The Constant jsonFactory. */
	private static final JsonFactory jsonFactory = new JsonFactory();
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(UnmarshallToLswEvent.class);
	static {
		jsonFactory.enable(Feature.ALLOW_COMMENTS);
		jsonFactory.enable(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
		jsonFactory.enable(Feature.ALLOW_SINGLE_QUOTES);
		jsonFactory.enable(Feature.ALLOW_NUMERIC_LEADING_ZEROS);
		jsonFactory.enable(Feature.ALLOW_NON_NUMERIC_NUMBERS);
		jsonFactory.enable(Feature.ALLOW_SINGLE_QUOTES);
		jsonFactory.enable(Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
		jsonFactory.enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		jsonFactory.enable(Feature.ALLOW_YAML_COMMENTS);
		//configure Object mapper for pretty print
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	/**
	 * Parses the incomings json stream to object.
	 *
	 * @param inputJsonStream the input json stream
	 * @return the lsw event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static LswEvent parseIncomingsJsonStreamToObject(final byte[] inputJsonStream) throws IOException {
		return mapper.readValue(inputJsonStream, LswEvent.class);
	}
}
