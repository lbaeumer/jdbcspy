package de.luisoft.jdbcspy;

import java.sql.Connection;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;

/**
 * The proxy connection interface provides a method to access the underlying
 * connection.
 */
public interface ProxyConnection extends Connection, ConnectionStatistics {

	/**
	 * Get the underlying connection.
	 * 
	 * @return Connection the connection
	 */
	Connection getUnderlyingConnection();

	/**
	 * Dump the connection.
	 * 
	 * @return String
	 */
	String dump();
}
