package com.lithium.esb.compliance.inbound;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lithium.esb.compliance.api.Hdfs2ConsumerService;
import com.lithium.esb.compliance.exception.HdfsFileNotFoundException;
import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;

public class Hdfs2ConsumerProvider implements Hdfs2ConsumerService {
	private final Configuration conf;
	private final CompressionCodecFactory factory;
	private static final Logger log = LoggerFactory.getLogger(Hdfs2ConsumerProvider.class);

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

	@Override
	public SecureEvent readFileContent(String pathLocation) throws IOException {
		//INFO: Estimating the files are never more than 64MB size. May be smaller for LIA
		Path filePath = new Path(pathLocation);
		FileSystem fileSystem = FileSystem.get(filePath.toUri(), conf);
		if (!fileSystem.exists(filePath)) {
			log.error("Input file not found. Please check the location: " + pathLocation);
			throw new HdfsFileNotFoundException("HDFS-ESB-001", "Input file not found. " + pathLocation);
		}
		FSDataInputStream inputStream = fileSystem.open(filePath);
		byte[] bytes = IOUtils.toByteArray(inputStream);
		return new Payload(bytes);
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
