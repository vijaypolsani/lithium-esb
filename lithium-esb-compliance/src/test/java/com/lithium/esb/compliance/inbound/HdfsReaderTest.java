package com.lithium.esb.compliance.inbound;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.junit.Test;

public class HdfsReaderTest {

	@Test
	public void readData() {
		try {
			String rawData = new HdfsReaderTest().readFileContent(new Path("hdfs://localhost:9000/int/in.log"),
					new Configuration());
			System.out.println(">>> Data from the Hdfs log files: " + rawData);
			assertNotNull(rawData);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String readFileContent(Path location, Configuration conf) throws IOException {
		FileSystem fileSystem = FileSystem.get(location.toUri(), conf);
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);

		CompressionCodec codec = factory.getCodec(location);
		InputStream stream = null;

		// check if we have a compression codec we need to use
		if (codec != null) {
			stream = codec.createInputStream(fileSystem.open(location));
		} else {
			stream = fileSystem.open(location);
		}

		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(stream, stringWriter, "UTF-8");
		String rawData = stringWriter.toString();
		return rawData;
	}
}
