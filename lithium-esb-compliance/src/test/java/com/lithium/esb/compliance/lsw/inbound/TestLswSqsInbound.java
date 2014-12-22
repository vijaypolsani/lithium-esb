package com.lithium.esb.compliance.lsw.inbound;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lithium.esb.compliance.lsw.api.LswSqsInbound;
import com.lithium.esb.compliance.lsw.api.inbound.LswSqsInboundImpl;

public class TestLswSqsInbound {
	private LswSqsInbound lswSqsInbound = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void before() {
		lswSqsInbound = new LswSqsInboundImpl();

	}

	@Test
	public void testSqsInboundDataReads() {
		for (int i = 0; i < 100; i++)
			lswSqsInbound.sendSqsMessage("Sample message: " + i);
		//exception.expectMessage(matcher);expect(IndexOutOfBoundsException.class);
	}
}
