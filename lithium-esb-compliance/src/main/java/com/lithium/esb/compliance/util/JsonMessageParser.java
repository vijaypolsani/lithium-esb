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

public final class JsonMessageParser {
	private static final JsonFactory jsonFactory = new JsonFactory();
	private static final Logger log = LoggerFactory.getLogger(JsonMessageParser.class);
	private static final ObjectMapper mapper = new ObjectMapper();
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

	public static ActivityStreamV1 parseIncomingsJsonStream(final String inputJsonStream) throws IOException {
		checkNotNull(inputJsonStream, "Message JSON that need to be parsed cannot be null.");
		final ActivityStreamV1 activityStreamV1 = new ActivityStreamV1(null);
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
					log.info("id: " + jsonParser.getText());
					activityStreamV1.setFrameId(jsonParser.getText());
					break;
				case "title":
					jsonParser.nextToken();
					log.info("id: " + jsonParser.getText());
					activityStreamV1.setTitle(jsonParser.getText());
					break;
				case "verb":
					jsonParser.nextToken();
					log.info("type: " + jsonParser.getText());
					if (activityStreamV1.getTitle() == null)
						activityStreamV1.setTitle(jsonParser.getText());
					break;
				case "published":
					jsonParser.nextToken();
					log.info("time: " + jsonParser.getText());
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(Long.parseLong(jsonParser.getText()));
					activityStreamV1.setPublished(formatter.format(cal.getTime()));
					break;

				}
				jsonToken = jsonParser.nextToken();
			}
			jsonParser.close();
		} catch (JsonParseException je) {
			log.error("<<< Exception is parsing the Event Logger Message: Skipping this message at Origination ..."
					+ je.getLocalizedMessage());
		}
		return activityStreamV1;
	}

	public static void main(String args[]) {
		final String SAMPLE_INPUT1 = "{\"event\":{\"id\":32250902,\"type\":\"ActionStart\",\"time\":1400014566536,\"frameId\":32250849,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"actionId\":32250901,\"actionKey\":\"kudos.give-message-kudo\",\"user\":{\"type$model\":\"user\",\"type\":\"user\",\"uid\":7,\"registrationStatus\":\"fully-registered\",\"registration.time\":\"2014-05-13T20:56:04.715Z\",\"login\":\"testuserdemo1\",\"email\":\"megha.meshram@lithium.com\",\"rankings\":[{\"ranking\":{\"type$model\":\"ranking\",\"type\":\"ranking\",\"uid\":23,\"name\":\"New Member\"},\"node\":{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1}}],\"roles\":[]}}}";
		final String SAMPLE_INPUT2 = "{\"event\":{\"id\":34016437,\"type\":\"EntityCreated\",\"time\":1400101236869,\"frameId\":34016419,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"actionId\":5052,\"target\":{\"type$model\":\"message\",\"type\":\"message\",\"uid\":19,\"node\":{\"type$model\":\"node\",\"type\":\"forum-board\",\"uid\":5,\"id\":\"General\",\"title\":\"A Test Board\",\"ancestors\":[{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1},{\"type$model\":\"node\",\"type\":\"category\",\"uid\":2}]},\"num\":8,\"visibility\":\"public\",\"author\":{\"type$model\":\"user\",\"type\":\"user\",\"uid\":7,\"registrationStatus\":\"fully-registered\",\"registration.time\":\"2014-05-13T20:56:04.715Z\",\"login\":\"testuserdemo1\",\"email\":\"megha.meshram@lithium.com\",\"rankings\":[{\"ranking\":{\"type$model\":\"ranking\",\"type\":\"ranking\",\"uid\":23,\"name\":\"New Member\"},\"node\":{\"type$model\":\"node\",\"type\":\"community\",\"uid\":1}}],\"roles\":[]},\"subject\":\"Testing demo message\",\"post.time\":\"2014-05-14T21:00:36.577Z\",\"conversationStyle\":\"forum\",\"isTopic\":true,\"conversation\":{\"type$model\":\"conversation\",\"type\":\"conversation\",\"uid\":19,\"topic\":{\"type$model\":\"message\",\"type\":\"message\",\"uid\":19},\"node\":{\"type$model\":\"node\",\"type\":\"forum-board\",\"uid\":5},\"style\":\"forum\"},\"message-type\":\"topic\"}}}";
		final String SAMPLE_INPUT3 = "{\"event\":{\"id\":34016465,\"type\":\"Blackbox\",\"time\":1400101236974,\"frameId\":34016419,\"version\":\"14.5.0\",\"service\":\"lia\",\"source\":\"lsw.qa\",\"node\":\"A65670F2\"},\"payload\":{\"line\":\"\"}}";

		try {
			log.info(">>> Transformed Message: " + JsonMessageParser.parseIncomingsJsonStream(SAMPLE_INPUT1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}