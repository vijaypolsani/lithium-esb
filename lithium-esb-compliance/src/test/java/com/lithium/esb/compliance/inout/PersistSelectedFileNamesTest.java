package com.lithium.esb.compliance.inout;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lithium.esb.compliance.model.HdfsFileDetail;
import com.lithium.esb.compliance.outbound.EsOutboundAdaptor;
import com.lithium.esb.compliance.repositories.HdfsSearchInputFilesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/beans-test.xml")
public class PersistSelectedFileNamesTest {
	private static final Logger log = LoggerFactory.getLogger(PersistSelectedFileNamesTest.class);
	@Resource
	private HdfsSearchInputFilesRepository hdfsSearchInputFilesRepository = null;

	@Resource
	private ElasticsearchTemplate elasticsearchTemplate = null;

	@Autowired
	private EsOutboundAdaptor persistSelectedFileNames = null;

	@Before
	public void setUp() {
		hdfsSearchInputFilesRepository.index(new HdfsFileDetail("1", "/int/test1.log", "test1", false, false));
		hdfsSearchInputFilesRepository.index(new HdfsFileDetail("2", "/int/test2.log", "test2", false, false));
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
	}

	@Before
	public void cleanUp() {
		hdfsSearchInputFilesRepository.deleteAll();
	}

	@Test
	public void shouldReturnListOfHdfsFileNamesByFileName() {
		Iterable<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findAll();
		Iterator<HdfsFileDetail> hdfsInputFilesIter = hdfsInputFiles.iterator();
		int count = 0;
		while (hdfsInputFilesIter.hasNext()) {
			HdfsFileDetail hdfsFileDetail = hdfsInputFilesIter.next();
			count++;
			log.info("HdfsFileDetail data: " + hdfsFileDetail.toString());
		}
		assertThat(count, is(2));

	}

	@Test
	public void getSelectedFileNameSearch() {
		List<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByName("test1");
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFiles)
			log.info("Print List" + hdfsFileDetail.toString());
		assertThat(hdfsInputFiles.size(), is(1));
	}

	@Test
	public void getMatchingFileNamesTest() {
		List<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByName("test*");
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFiles)
			log.info("Print List" + hdfsFileDetail.toString());
		assertThat(hdfsInputFiles.size(), is(2));
	}

	@Test
	public void getFindByFileOpenedAndFileReadFalse() {
		Set<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByFileOpenedAndFileRead(false, false);
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFiles)
			log.info("Print List" + hdfsFileDetail.toString());
		assertThat(hdfsInputFiles.size(), is(2));
	}

	@Test
	public void doUpdate() {
		List<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByName("test*");
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFiles) {
			log.info("Print List Get" + hdfsFileDetail.toString());
			hdfsFileDetail.setFileOpened(true);
			hdfsFileDetail.setDescription("Modified in Test case: doUpdate()");
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		}
		List<HdfsFileDetail> hdfsInputFilesModified = hdfsSearchInputFilesRepository.findByName("test*");
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFilesModified) {
			log.info("Print List Modified" + hdfsFileDetail.toString());
		}
		assertThat(hdfsInputFiles.size(), is(2));
	}

	@Test
	public void getFindByFileOpenedAndFileReadOpenedTrue() {
		List<HdfsFileDetail> hdfsInputFiles = hdfsSearchInputFilesRepository.findByName("test*");
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFiles) {
			log.info("Print List Get" + hdfsFileDetail.toString());
			hdfsFileDetail.setFileOpened(true);
			hdfsSearchInputFilesRepository.save(hdfsFileDetail);
		}
		Set<HdfsFileDetail> hdfsInputFilesOpenedTrue = hdfsSearchInputFilesRepository.findByFileOpenedAndFileRead(true,
				false);
		for (HdfsFileDetail hdfsFileDetail : hdfsInputFilesOpenedTrue) {
			log.info("Print List Modified" + hdfsFileDetail.toString());
		}
		assertThat(hdfsInputFilesOpenedTrue.size(), is(2));
	}
}
