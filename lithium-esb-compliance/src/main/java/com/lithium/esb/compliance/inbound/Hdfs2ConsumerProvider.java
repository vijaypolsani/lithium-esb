package com.lithium.esb.compliance.inbound;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

import com.lithium.esb.compliance.api.Hdfs2ConsumerService;

public class Hdfs2ConsumerProvider implements Hdfs2ConsumerService {
	private final Configuration conf;
	private final CompressionCodecFactory factory;

	private Hdfs2ConsumerProvider(Configuration conf) {
		if (conf == null)
			this.conf = conf;
		else
			this.conf = new Configuration();
		this.factory = new CompressionCodecFactory(conf);

	}

	public static Hdfs2ConsumerProvider creatHdfs2ConsumerProviderWithConfiguration(Configuration conf) {
		return new Hdfs2ConsumerProvider(conf);
	}

	public static Hdfs2ConsumerProvider creatHdfs2ConsumerProviderWithDefaultConfiguration() {
		return new Hdfs2ConsumerProvider(new Configuration());
	}

	public String readFileContent(String pathLocation) throws IOException {
		Path filePath = new Path(pathLocation);
		FileSystem fileSystem = FileSystem.get(filePath.toUri(), conf);
		CompressionCodec codec = factory.getCodec(filePath);

		InputStream stream = null;
		if (codec != null) {
			stream = codec.createInputStream(fileSystem.open(filePath));
		} else {
			stream = fileSystem.open(filePath);
		}
		//INFO: Estimating the files are never more than 64MB size. May be smaller for LIA
		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(stream, stringWriter, "UTF-8");
		return stringWriter.toString();
	}

	public void createFile(String pathLocation) throws IOException {
		Path filePath = new Path(pathLocation);
		FileSystem fileSystem = FileSystem.get(filePath.toUri(), conf);
		FSDataOutputStream out = fileSystem.create(filePath);
		out.write("This is a sample from JUNIT Hdfs2ConsumerProviderTest.".getBytes());
	}

	public boolean deleteFile(String pathLocation) throws IOException {
		Path filePath = new Path(pathLocation);
		FileSystem fileSystem = FileSystem.get(filePath.toUri(), conf);
		return fileSystem.delete(filePath, false);
	}

}
