package com.lithium.esb.compliance.ws;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/v1")
public class LithiumEsbComplianceService {

	private static final Logger log = LoggerFactory.getLogger(LithiumEsbComplianceService.class);
	private static final String COMMUNITY_NAME = "actiance.stage";
	private static final String LOGIN = "-user";
	private static final String PASSCODE = "sCe9KITKh8+h1w4e9EDnVwzXBM8NjiilrWS6dOdMNr0=";

	/**
	 * API to get the last Event Unique SequenceId delivered to the Consumer. This helps in tracking the consumption of the client.
	 * @param communityName
	 * @param login
	 * @return String Last event UniqueID of the content supplied to the customer
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@GET
	@Path("esb")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEsbRunning(@QueryParam("login") String login) throws InterruptedException, ExecutionException {
		return "DEMO123456789";
	}
}
