package com.lithium.esb.compliance.inbound;

import com.lithium.esb.compliance.api.Hdfs2InboundService;

/**
 * A factory for creating Hdfs2InboundAdaptor objects.
 */
public class Hdfs2InboundAdaptorFactory {
	
	/** The hdfs2 inbound service. */
	private static Hdfs2InboundService hdfs2InboundService;

	/**
	 * Instantiates a new hdfs2 inbound adaptor factory.
	 */
	private Hdfs2InboundAdaptorFactory() {
	}

	/**
	 * Creates a new Hdfs2InboundAdaptor object.
	 *
	 * @return the hdfs2 inbound service
	 */
	public Hdfs2InboundService createHdfs2InboundService() {
		return hdfs2InboundService;
	}

	/**
	 * Gets the hdfs2 inbound service.
	 *
	 * @return the hdfs2 inbound service
	 */
	public static Hdfs2InboundService getHdfs2InboundService() {
		return hdfs2InboundService;
	}

	/**
	 * Sets the hdfs2 inbound service.
	 *
	 * @param hdfs2InboundService the new hdfs2 inbound service
	 */
	public static void setHdfs2InboundService(Hdfs2InboundService hdfs2InboundService) {
		Hdfs2InboundAdaptorFactory.hdfs2InboundService = hdfs2InboundService;
	}

}
