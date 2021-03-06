package de.luisoft.jdbcspy.proxy.listener;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;

/**
 * The Connection Event class.
 */
public class ConnectionEvent {

    /**
     * the proxy object
     */
    private final ConnectionStatistics mConn;

    /**
     * Constructor.
     *
     * @param conn the proxy object
     */
    public ConnectionEvent(ConnectionStatistics conn) {
        mConn = conn;
    }

    /**
     * Get the connection statistics object.
     *
     * @return Object
     */
    public ConnectionStatistics getConnectionStatistics() {
        return mConn;
    }
}
