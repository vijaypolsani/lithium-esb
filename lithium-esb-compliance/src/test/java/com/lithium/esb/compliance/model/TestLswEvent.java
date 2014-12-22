package com.lithium.esb.compliance.model;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.util.UnmarshallToLswEvent;

public class TestLswEvent {
	public static final String SAMPLE_DATA = "{ \"_index\": \"activities_2014-09-29\", \"_type\": \"CaseStateSnapshot\", \"_id\": \"google-6008121-18850376\", \"_score\": 1, \"_source\": { \"id\": 18850376, \"version\": 1412024276065, \"state\": \"AVAILABLE\", \"disposition\": \"UNASSIGNED\", \"encodedDisposition\": 0, \"currentState\": \"CLOSED\", \"currentTarStartDttm\": 1400553563000, \"currentCombinedTagIds\": [ 787,  786, 853, 785, 1735, 784, 1737, 1741, 860, 1719, 1687, 1747, 782, 783,  1725, 781, 845 ], \"currentBusinessHourType\": 0, \"currentTarDocBusinessHourType\": 0, \"beginDttm\": 1412012186970, \"endDttm\": 1412013006403, \"caseId\": 6008121, \"caseUuid\": \"937d01e1-774b-4ba4-9056-a19e8eb6c067\",  \"createdDttm\": 1399570556445, \"userId\": 114, \"teamId\": 63, \"casePriority\": 1, \"caseWorkqueueId\": 99, \"documentCount\": 17678, \"responseCount\": 0, \"companyKey\": \"google\" } }";
	private static final Logger log = LoggerFactory.getLogger(TestLswEvent.class);

	@Test
	public void testParsingOfSampleData() {
		LswEvent lswEvent = null;
		try {
			lswEvent = UnmarshallToLswEvent.parseIncomingsJsonStreamToObject(SAMPLE_DATA.getBytes());
			log.info(lswEvent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(lswEvent);
	}

}
