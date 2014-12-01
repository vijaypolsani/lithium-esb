package com.lithium.esb.compliance.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lithium.streams.compliance.model.SecureEvent;

public class WriteFileContent {
	public void readContent(SecureEvent secureEvent) {
		Path path = Paths.get("/home/user/app/lithium-esb-compliance/logs/encrypted.log");
		try {
			Files.write(path, secureEvent.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
