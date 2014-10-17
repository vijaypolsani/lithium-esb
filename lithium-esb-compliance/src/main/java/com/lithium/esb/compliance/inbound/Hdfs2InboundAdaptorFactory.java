package com.lithium.esb.compliance.inbound;

import com.lithium.esb.compliance.api.Hdfs2InboundService;

public class Hdfs2InboundAdaptorFactory {
	private static Hdfs2InboundService hdfs2InboundService;

	private Hdfs2InboundAdaptorFactory() {
	}

	public Hdfs2InboundService createHdfs2InboundService() {
		return hdfs2InboundService;
	}

	public static Hdfs2InboundService getHdfs2InboundService() {
		return hdfs2InboundService;
	}

	public static void setHdfs2InboundService(Hdfs2InboundService hdfs2InboundService) {
		Hdfs2InboundAdaptorFactory.hdfs2InboundService = hdfs2InboundService;
	}

}
