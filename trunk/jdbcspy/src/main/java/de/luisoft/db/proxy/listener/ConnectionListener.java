package de.luisoft.db.proxy.listener;

/**
 * The Connection listener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ConnectionListener.java 701 2006-04-23 11:43:51Z lui $
 */
public interface ConnectionListener {
    /**
     * Open a connection.
     * @param event ConnectionEvent
     */
    void openConnection(ConnectionEvent event);

    /**
     * Close the connection.
     * @param event ConnectionEvent
     */
    void closeConnection(ConnectionEvent event);

    /**
     * Clear the statistical data.
     */
    void clearStatistics();
}
