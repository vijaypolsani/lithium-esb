package com.lithium.esb.compliance.inbound;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.lithium.esb.compliance.api.Hdfs2InboundService;
import static com.lithium.esb.compliance.util.DateMonthZeroPadder.paddWithZerosForMonth;

public class Hdfs2InboundAdaptor implements Hdfs2InboundService {

	private static final Logger log = LoggerFactory.getLogger(Hdfs2InboundAdaptor.class);
	private final Configuration conf = new Configuration();
	private final Set<String> listOfFiles = new LinkedHashSet<>();
	private final String lookupFolderName;
	private final Boolean currentMonthFilesOnly;
	private static final String FOLDER_END_DELIMITER = "/";
	private final FileSystem fs;
	private final String folderPath;

	public Hdfs2InboundAdaptor(String lookupFolderName, Boolean currentMonthFilesOnly) throws IOException {
		//Perform Argument Check.
		this.lookupFolderName = lookupFolderName;
		this.currentMonthFilesOnly = currentMonthFilesOnly;
		if (currentMonthFilesOnly.booleanValue()) {
			if (lookupFolderName.endsWith(FOLDER_END_DELIMITER))
				folderPath = lookupFolderName + Year.now() + FOLDER_END_DELIMITER
						+ paddWithZerosForMonth(LocalDate.now().getMonthValue());
			else
				folderPath = lookupFolderName + FOLDER_END_DELIMITER + Year.now() + FOLDER_END_DELIMITER
						+ paddWithZerosForMonth(LocalDate.now().getMonthValue());
		} else
			folderPath = lookupFolderName;
		fs = FileSystem.get(conf);
	}

	//Location of the path: Ex: /stage/actiance.stage/events/2014/09. Always searched in the current MONTH folder of HDFS.
	public Set<String> getSetOfLatestHdfsFiles() throws IOException {

		RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(new Path(folderPath), true);
		while (remoteIterator.hasNext()) {
			LocatedFileStatus locatedFileStatus = remoteIterator.next();
			//log.info(">>> File Path Name: " + locatedFileStatus.getPath().toString());
			//log.info(">>> File Name: " + locatedFileStatus.getPath().getName());
			//Keeping hdfs://ip:host from the name 
			listOfFiles.add(locatedFileStatus.getPath().toString());
		}
		log.info(">>>Total number of files: " + listOfFiles.size());
		return ImmutableSet.<String> builder().addAll(listOfFiles).build();
	}

	public void deleteAllFiles(String path, boolean recursiveTrue) throws IOException {
		fs.delete(new Path(path), recursiveTrue);
	}

	public void createFile(String path) throws IOException {
		fs.createNewFile(new Path(path));
	}

	public static void main(String args[]) {

		try {
			UserGroupInformation ugi = UserGroupInformation.createRemoteUser("vijay.polsani");

			ugi.doAs(new PrivilegedExceptionAction<Void>() {

				public Void run() throws Exception {

					Configuration conf = new Configuration();
					conf.set("fs.defaultFS", "hdfs://127.0.0.1:9000");
					conf.set("hadoop.job.ugi", "vijay.polsani");

					FileSystem fs = FileSystem.get(conf);

					log.info("***File created: " + fs.createNewFile(new Path("/int/maintestcase.log")));

					RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(new Path("/int"), true);
					while (remoteIterator.hasNext()) {
						LocatedFileStatus locatedFileStatus = remoteIterator.next();
						log.info("***File remoteIterator: " + locatedFileStatus.getPath().getName());
						log.info("***File remoteIterator Name: " + locatedFileStatus.getPath().toString());
						log.info("***File remoteIterator toString(): " + locatedFileStatus.toString());
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
