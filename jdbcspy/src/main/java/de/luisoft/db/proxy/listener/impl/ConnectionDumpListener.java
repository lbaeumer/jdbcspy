package de.luisoft.db.proxy.listener.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.db.proxy.handler.ConnectionHandler;
import de.luisoft.db.proxy.listener.ConnectionEvent;
import de.luisoft.db.proxy.listener.ConnectionListener;
import de.luisoft.db.proxy.util.Utils;

/**
 * The ConnectionStatisticListener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ConnectionStatisticListener.java 688 2006-05-19 15:20:53Z lbaeumer $
 */
public class ConnectionDumpListener implements ConnectionListener {

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(ConnectionDumpListener.class);

    /**
     * @see de.luisoft.db.proxy.listener.ConnectionListener#openConnection
     */
    public void openConnection(ConnectionEvent event) {
    };

    /**
     * @see de.luisoft.db.proxy.listener.ConnectionListener#openConnection
     */
    public void closeConnection(ConnectionEvent event) {
        List debug = Arrays.asList(dbConnDumpClassExp.split(","));
        if (debug != null) {
            String regExp = Utils.isTraceClass(debug);
            if (regExp != null) {
                ConnectionHandler handler = (ConnectionHandler) event.getConnectionStatistics();
                mTrace.info("closed connection: " + handler.dump());
            }
        }
    };

    /**
     * @see de.luisoft.db.proxy.listener.ConnectionListener#clearStatistics
     */
    public void clearStatistics() {
    }

    private String dbConnDumpClassExp;
    public void setConnDumpCloseClassExp(String exp) {
    	dbConnDumpClassExp = exp;
    }

    /**
     * @see java.lang.Object#toString
     */
    public String toString() {
        return dbConnDumpClassExp;
    }
}
