package com.lithium.esb.compliance.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lithium.esb.compliance.model.ActivityStreamV1;

/**
 * The Class JsonMessageParser for parsing LIA events and into AS 1.0 format conversion
 */
public final class JsonMessageParser {
	
	/** The Constant jsonFactory. */
	private static final JsonFactory jsonFactory = new JsonFactory();
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(JsonMessageParser.class);
	
	/** The Constant mapper. */
	private final static ObjectMapper mapper = new ObjectMapper();
	
	/** The Constant cal. */
	private final static Calendar cal = Calendar.getInstance();
	
	/** The Constant formatter. */
	private static final DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
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
	
	/** The Constant SAMPLE_INPUT1. */
	public static final String SAMPLE_INPUT1 = "{\"event\":{\"id\":32250902,\"type\":\"ActionStart\",\"time\":1400014566536,\"frameId\":32250849,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"actionId\":32250901,\"actionKey\":\"kudos.give-message-kudo\",\"user\":{\"type$model\":\"user\",\"type\":\"user\",\"uid\":7,\"registrationStatus\":\"fully-registered\",\"registration.time\":\"2014-05-13T20:56:04.715Z\",\"login\":\"testuserdemo1\",\"email\":\"megha.meshram@lithium.com\",\"rankings\":[{\"ranking\":{\"type$model\":\"ranking\",\"type\":\"ranking\",\"uid\":23,\"name\":\"New Member\"},\"node\":{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1}}],\"roles\":[]}}}";
	
	/** The Constant SAMPLE_INPUT2. */
	public static final String SAMPLE_INPUT2 = "{\"event\":{\"id\":34016437,\"type\":\"EntityCreated\",\"time\":1400101236869,\"frameId\":34016419,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"actionId\":5052,\"target\":{\"type$model\":\"message\",\"type\":\"message\",\"uid\":19,\"node\":{\"type$model\":\"node\",\"type\":\"forum-board\",\"uid\":5,\"id\":\"General\",\"title\":\"A Test Board\",\"ancestors\":[{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1},{\"type$model\":\"node\",\"type\":\"category\",\"uid\":2}]},\"num\":8,\"visibility\":\"public\",\"author\":{\"type$model\":\"user\",\"type\":\"user\",\"uid\":7,\"registrationStatus\":\"fully-registered\",\"registration.time\":\"2014-05-13T20:56:04.715Z\",\"login\":\"testuserdemo1\",\"email\":\"megha.meshram@lithium.com\",\"rankings\":[{\"ranking\":{\"type$model\":\"ranking\",\"type\":\"ranking\",\"uid\":23,\"name\":\"New Member\"},\"node\":{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1}}],\"roles\":[]},\"subject\":\"Testing demo message\",\"post.time\":\"2014-05-14T21:00:36.577Z\",\"conversationStyle\":\"forum\",\"isTopic\":true,\"conversation\":{\"type$model\":\"conversation\",\"type\":\"conversation\",\"uid\":19,\"topic\":{\"type$model\":\"message\",\"type\":\"message\",\"uid\":19},\"node\":{\"type$model\":\"node\",\"type\":\"forum-board\",\"uid\":5},\"style\":\"forum\"},\"message-type\":\"topic\"}}}";
	
	/** The Constant SAMPLE_INPUT3. */
	public static final String SAMPLE_INPUT3 = "{\"event\":{\"id\":34016465,\"type\":\"Blackbox\",\"time\":1400101236974,\"frameId\":34016419,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"line\":\"\"}}";
	
	/** The Constant SAMPLE_INPUT4. */
	public static final String SAMPLE_INPUT4 = "{\"event\":{\"id\":128191559,\"type\":\"FrameStart\",\"time\":1411235100101,\"frameId\":128191559,\"version\":\"14.9.0\",\"service\":\"lia\",\"source\":\"actiance.stage\",\"node\":\"2E8225CA\"},\"payload\":{}}";

	/**
	 * Instantiates a new json message parser.
	 */
	public JsonMessageParser() {
	}

	/**
	 * Parses the incomings json stream.
	 *
	 * @param inputJsonStream the input json stream
	 * @return the activity stream v1
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ActivityStreamV1 parseIncomingsJsonStream(final String inputJsonStream) throws IOException {
		checkNotNull(inputJsonStream, "Message JSON that need to be parsed cannot be null.");
		final ActivityStreamV1 activityStreams = new ActivityStreamV1("");
		try {
			JsonParser jsonParser = jsonFactory.createParser(inputJsonStream);
			JsonToken jsonToken = jsonParser.nextToken();
			if (jsonToken.equals(JsonToken.START_OBJECT) || jsonToken.equals(JsonToken.VALUE_NULL)
					|| jsonToken.equals(JsonToken.END_OBJECT))
				jsonToken = jsonParser.nextToken();

			while (jsonParser.hasCurrentToken() && jsonToken != JsonToken.VALUE_NULL) {
				String fieldName = jsonParser.getCurrentName();
				if (jsonToken == null || fieldName == null)
					break;
				switch (fieldName) {
				case "id":
					jsonParser.nextToken();
					//log.debug("id: " + jsonParser.getText());
					activityStreams.setId(jsonParser.getText());
					break;
				case "frameId":
					jsonParser.nextToken();
					//log.info("frameId: " + jsonParser.getText());
					activityStreams.setFrameId(jsonParser.getText());
					break;
				case "type":
					jsonParser.nextToken();
					//log.debug("type: " + jsonParser.getText());
					activityStreams.setType(jsonParser.getText());
					break;
				case "time":
					jsonParser.nextToken();
					//log.debug("time: " + jsonParser.getText());
					cal.setTimeInMillis(Long.parseLong(jsonParser.getText()));
					activityStreams.setPublished(formatter.format(cal.getTime()));
					break;
				case "version":
					jsonParser.nextToken();
					//log.debug("version: " + jsonParser.getText());
					activityStreams.setVersion(jsonParser.getText());
					break;
				}
				jsonToken = jsonParser.nextToken();
			}
			jsonParser.close();
		} catch (JsonParseException je) {
			log.error("<<< Exception is parsing the Event Logger Message: Skipping this message at Origination ..."
					+ je.getLocalizedMessage());
		}
		return activityStreams;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		try {
			new JsonMessageParser();
			log.info(">>> Transformed Message: " + JsonMessageParser.parseIncomingsJsonStream(SAMPLE_INPUT4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
