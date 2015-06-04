package com.lithium.esb.common.util;

import javax.inject.Singleton;

import com.lithium.esb.common.api.KinesisAsyncConnector;

import dagger.Component;
import dagger.Subcomponent;

public class KinesisAdaptorComponent {

	@Singleton
	@Component(modules = { KinesisAdaptorModule.class })
	public interface Adaptor {
		KinesisAsyncConnector get();
	}

}
