package com.lithium.esb.compliance.lsw.outbound;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.lithium.esb.compliance.lsw.api.LswSqsOutbound;
import com.lithium.esb.compliance.lsw.api.outbound.LswSqsOutboundImpl;
import com.lithium.streams.compliance.model.SecureEvent;

public class TestLswSqsOutbound {
	private LswSqsOutbound lswSqsOutbound = null;

	@Before
	public void before() {
		lswSqsOutbound = new LswSqsOutboundImpl();

	}

	@Test
	public void testSqsInboundDataReads() {
		Collection<SecureEvent> secureEvents = lswSqsOutbound.consumeMessages();
		assertNotNull(secureEvents);
		int i = 0;
		for (SecureEvent secureEvent : secureEvents)
			System.out.println("Data returned: " + ++i + " Content: " + secureEvent.toString());
	}

}
