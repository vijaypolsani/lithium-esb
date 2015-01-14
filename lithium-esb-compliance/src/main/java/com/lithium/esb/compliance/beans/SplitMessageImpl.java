package com.lithium.esb.compliance.beans;

import java.util.ArrayList;
import java.util.List;

import com.lithium.esb.compliance.api.SplitMessage;

/**
 * The SplitMessageImpl implemented @SplitMessage interface for splitting the body of the messages based on <p>NewLine Character: \n<p>
 */
public class SplitMessageImpl implements SplitMessage {

	/**
	 * Split body.
	 *
	 * @param body the body
	 * @return the list
	 */
	public List<String> splitBody(String body) {
		List<String> answer = new ArrayList<String>();
		String[] parts = body.split("\n");
		for (String part : parts) {
			answer.add(part);
		}
		return answer;
	}
}
