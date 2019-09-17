package de.luisoft.jdbcspy;

import java.sql.Connection;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;

/**
 * The proxy connection interface provides a method to access the underlying
 * connection.
 * <p>Title: ProxyConnection</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ProxyConnection.java 747 2006-06-08 18:28:10Z lui $
 */
public interface ProxyConnection extends Connection, ConnectionStatistics {

    /**
     * Get the underlying connection.
     * @return Connection the connection
     */
    Connection getUnderlyingConnection();

    /**
     * Dump the connection.
     * @return String
     */
    String dump();
}
