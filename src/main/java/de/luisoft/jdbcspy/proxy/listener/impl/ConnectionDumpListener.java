package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The ConnectionStatisticListener.
 */
public class ConnectionDumpListener implements ConnectionListener {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ConnectionDumpListener.class);

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
	 */
	@Override
	public void openConnection(ConnectionEvent event) {
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
	 */
	@Override
	public void closeConnection(ConnectionEvent event) {
		List<String> debug = Arrays.asList(dbConnDumpClassExp.split(","));
		if (debug != null) {
			String regExp = Utils.isTraceClass(debug);
			if (regExp != null) {
				ConnectionHandler handler = (ConnectionHandler) event.getConnectionStatistics();
				mTrace.info("closed connection: " + handler.dump());
			}
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#clearStatistics
	 */
	@Override
	public void clearStatistics() {
	}

	private String dbConnDumpClassExp;

	public void setConnDumpCloseClassExp(String exp) {
		dbConnDumpClassExp = exp;
	}

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		return dbConnDumpClassExp;
	}
}
