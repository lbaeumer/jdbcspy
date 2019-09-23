package de.luisoft.jdbcspy.proxy.listener.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The ConnectionStatisticListener.
 */
public class ConnectionStatisticListener implements ConnectionListener {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ConnectionStatisticListener.class);

	/** the connections */
	private Set<ConnectionStatistics> mConns = new HashSet<>();

	/** the count */
	private int mCount;

	/** the max current count */
	private int mMaxCurrentCount;

	/** the max stmt count */
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
	};

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
	};

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
		StringBuffer strb = new StringBuffer("[ConnectionStatisticListener[\n" + "#conn=" + mCount
				+ "; #max open conns=" + mMaxCurrentCount + "; #max stmts/conn=" + mMaxStmtCount);

		int i = 0;
		synchronized (mConns) {
			for (Iterator it = mConns.iterator(); it.hasNext(); i++) {
				ConnectionHandler hndlr = (ConnectionHandler) it.next();
				Connection c = hndlr.getUnderlyingConnection();

				if (i == 1) {
					strb.append("; current:");
				}
				strb.append("\n" + i + ": ");
				strb.append(hndlr.toString());

				try {
					if (c.getAutoCommit()) {
						strb.append("; autocommit");
					}
					strb.append("; isolation=" + Utils.getIsolationLevel(c.getTransactionIsolation()));
					if (c.isReadOnly()) {
						strb.append("; readonly");
					}
					SQLWarning warning = c.getWarnings();
					while (warning != null) {
						strb.append("\nwarning='" + warning.getMessage() + "'; errorCode=" + warning.getErrorCode());
						warning = warning.getNextWarning();
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
