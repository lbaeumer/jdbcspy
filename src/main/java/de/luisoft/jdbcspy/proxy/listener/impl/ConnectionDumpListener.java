package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * The ConnectionStatisticListener.
 */
public class ConnectionDumpListener implements ConnectionListener {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(ConnectionDumpListener.class.getName());
    private String dbConnDumpClassExp;

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
     */
    @Override
    public void openConnection(ConnectionEvent event) {
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
     */
    @Override
    public void closeConnection(ConnectionEvent event) {
        List<String> debug = Arrays.asList(dbConnDumpClassExp.split(","));
        String regExp = Utils.isTraceClass(debug);
        if (regExp != null) {
            ConnectionHandler handler = (ConnectionHandler) event.getConnectionStatistics();
            mTrace.info("closed connection: " + handler.dump());
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
    }

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
