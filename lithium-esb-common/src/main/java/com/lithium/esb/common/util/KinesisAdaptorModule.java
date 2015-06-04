package com.lithium.esb.common.util;

import javax.inject.Singleton;

import com.lithium.esb.common.KinesisAdaptor;
import com.lithium.esb.common.api.KinesisAsyncConnector;

import dagger.Module;
import dagger.Provides;

@Module
public class KinesisAdaptorModule {
	@Provides
	@Singleton
	KinesisAsyncConnector provideKinesisAdaptor() {
		return new KinesisAdaptor(50);
	}

}
