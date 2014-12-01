package com.lithium.esb.compliance.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;

public class ReadFileContent {
	public SecureEvent readContent() {
		Path path = Paths.get("/home/user/app/lithium-esb-compliance/logs/encrypted.log");
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Payload(data);
	}
}
