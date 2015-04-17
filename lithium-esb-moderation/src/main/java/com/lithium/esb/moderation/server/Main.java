package com.lithium.esb.moderation.server;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main server class for bootstrapping WEB Context & Server.
 * Uses web.xml for booting the necessary information in contrast with tying everything to inside this class.
 */
public class Main {
	
	/** The Constant props. */
	private static final Properties props = new Properties();
	
	/** The Constant WEB_APP_DIRECTORY_LOCATION. */
	private static final String WEB_APP_DIRECTORY_LOCATION = ".";
	
	/** The Constant SERVER_PORT. */
	private static final String SERVER_PORT = "server_port";
	
	/** The Constant DEFAULT_SERVER_PORT. */
	private static final int DEFAULT_SERVER_PORT = 8080;
	
	/** The server. */
	private static Server server = null;

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		props.load(new FileInputStream(WEB_APP_DIRECTORY_LOCATION + "/conf/beanEsbConfig.properties"));
		log.debug("Properties data: " + props.toString());
		PropertyConfigurator.configure(WEB_APP_DIRECTORY_LOCATION + "/conf/log4j.properties");
		if (props.getProperty(SERVER_PORT) != null)
			server = new Server(Integer.parseInt(props.getProperty(SERVER_PORT)));
		else
			server = new Server(DEFAULT_SERVER_PORT);

		WebAppContext webContext = new WebAppContext();
		webContext.setContextPath("/");
		webContext.setDescriptor(WEB_APP_DIRECTORY_LOCATION + "/conf/flow/web.xml");
		webContext.setResourceBase(WEB_APP_DIRECTORY_LOCATION);
		webContext.setServer(server);
		webContext.setParentLoaderPriority(true);
		server.setHandler(webContext);
		server.start();
		server.join();
		log.info("ESB Server started and serving reqeuests.");
	}
}

