package de.luisoft.jdbcspy.proxy.listener;

/**
 * The Connection listener.
 */
public interface ConnectionListener {
    /**
     * Open a connection.
     *
     * @param event ConnectionEvent
     */
    void openConnection(ConnectionEvent event);

    /**
     * Close the connection.
     *
     * @param event ConnectionEvent
     */
    void closeConnection(ConnectionEvent event);

    /**
     * Clear the statistical data.
     */
    void clearStatistics();
}
