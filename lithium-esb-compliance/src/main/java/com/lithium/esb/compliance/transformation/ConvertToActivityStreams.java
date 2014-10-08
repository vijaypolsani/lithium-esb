package com.lithium.esb.compliance.transformation;

import java.util.List;

public class ConvertToActivityStreams {

	public byte[] convertListToSingleObject(List<byte[]> listOfObjects) {
		for (byte[] data : listOfObjects) {
			return data;
		}
		return null;
	}
}
