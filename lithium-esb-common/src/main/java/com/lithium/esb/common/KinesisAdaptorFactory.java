package com.lithium.esb.common;

import javax.inject.Inject;
import javax.inject.Named;

import com.lithium.esb.common.api.KinesisAsyncConnector;
import com.lithium.esb.common.util.DaggerKinesisAdaptorComponent_Adaptor;
import com.lithium.esb.common.util.KinesisAdaptorComponent.Adaptor;

public class KinesisAdaptorFactory {

	@Inject
	@Named("default")
	KinesisAsyncConnector kinesisAdaptor;

	public static KinesisAsyncConnector getKinesisAdaptor() {
		Adaptor adaptor = DaggerKinesisAdaptorComponent_Adaptor.builder().build();
		return adaptor.get();
	}

}
