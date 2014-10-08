package com.lithium.esb.compliance.beans;

import java.util.ArrayList;
import java.util.List;

import com.lithium.esb.compliance.api.SplitMessage;

public class SplitMessageImpl implements SplitMessage {

	public List<String> splitBody(String body) {
		List<String> answer = new ArrayList<String>();
		String[] parts = body.split("\n");
		for (String part : parts) {
			answer.add(part);
		}
		return answer;
	}
}
