package de.luisoft.jdbcspy.proxy.listener;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;

/**
 * The Connection Event class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ConnectionEvent.java 747 2006-06-08 18:28:10Z lui $
 */
public class ConnectionEvent {

    /** the proxy object */
    private ConnectionStatistics mConn;

    /**
     * Constructor.
     * @param proxy the proxy object
     */
    public ConnectionEvent(ConnectionStatistics conn) {
        mConn = conn;
    }

    /**
     * Get the connection statistics object.
     * @return Object
     */
    public ConnectionStatistics getConnectionStatistics() {
        return mConn;
    }
}
