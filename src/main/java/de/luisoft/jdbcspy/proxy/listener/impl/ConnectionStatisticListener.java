package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.XAConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The ConnectionStatisticListener.
 */
public class ConnectionStatisticListener implements ConnectionListener {

    /**
     * the logger object for tracing
     */
    private static final Log mTrace = LogFactory.getLog(ConnectionStatisticListener.class);

    /**
     * the connections
     */
    private final Set<ConnectionStatistics> mConns = new HashSet<>();

    /**
     * the count
     */
    private int mCount;

    /**
     * the max current count
     */
    private int mMaxCurrentCount;

    /**
     * the max stmt count
     */
    private int mMaxStmtCount;

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
     */
    @Override
    public void openConnection(ConnectionEvent event) {
        mCount++;
        synchronized (mConns) {
            mConns.add(event.getConnectionStatistics());
            if (mConns.size() > mMaxCurrentCount) {
                mMaxCurrentCount = mConns.size();
            }
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#openConnection
     */
    @Override
    public void closeConnection(ConnectionEvent event) {
        if (event.getConnectionStatistics().getItemCount() > mMaxStmtCount) {
            mMaxStmtCount = event.getConnectionStatistics().getItemCount();
        }

        synchronized (mConns) {
            mConns.remove(event.getConnectionStatistics());
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ConnectionListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
        synchronized (mConns) {
            mConns.clear();
        }
        mCount = 0;
        mMaxCurrentCount = 0;
        mMaxStmtCount = 0;
    }

    /**
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder("[ConnectionStatisticListener[\n" + "#conn=" + mCount
                + "; #max open conns=" + mMaxCurrentCount + "; #max stmts/conn=" + mMaxStmtCount);

        int i = 0;
        synchronized (mConns) {
            for (Iterator it = mConns.iterator(); it.hasNext(); i++) {
                ConnectionHandler hndlr = (ConnectionHandler) it.next();
                Object c = hndlr.getUnderlyingConnection();

                if (i == 1) {
                    strb.append("; current:");
                }
                strb.append("\n").append(i).append(": ");
                strb.append(hndlr.toString());

                try {
                    if ((c instanceof Connection) && ((Connection) c).getAutoCommit()
                            || (c instanceof XAConnection) && ((XAConnection) c).getConnection().getAutoCommit()) {
                        strb.append("; autocommit");
                    }
                    strb.append("; isolation=").append(Utils.getIsolationLevel(
                            (c instanceof Connection
                                    ? ((Connection) c).getTransactionIsolation()
                                    : ((XAConnection) c).getConnection().getTransactionIsolation())
                    ));
                    if ((c instanceof Connection) && ((Connection) c).isReadOnly()
                            || (c instanceof XAConnection) && ((XAConnection) c).getConnection().isReadOnly()) {
                        strb.append("; readonly");
                    }
                } catch (SQLException e) {
                    strb.append("; no connection properties");
                    mTrace.info("property reading failed, but ignored ", e);
                    it.remove();
                }
            }
            strb.append("\n");
        }
        strb.append("]]");

        return strb.toString();
    }
}
