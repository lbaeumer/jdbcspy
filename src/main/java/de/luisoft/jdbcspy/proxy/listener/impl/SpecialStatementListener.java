package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The Special statement listener.
 */
public class SpecialStatementListener extends ExecutionAdapter {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(SpecialStatementListener.class);

	/** the time map */
	private Map<StatementStatistics, String> mRunningStmts = new HashMap<>();

	/** the history map */
	private Map<String, List<String>> mHistoryMap = new HashMap<>();

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
	 */
	@Override
	public void startExecution(ExecutionEvent event) {
		StatementStatistics stmt = event.getStatementStatistics();
		String regExp = Utils.isHistoryTrace(stmt.getSQL());
		if (regExp != null) {
			synchronized (mRunningStmts) {
				mRunningStmts.put(stmt, regExp);
			}
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
	 */
	@Override
	public void closeStatement(CloseEvent event) {
		String regExp = null;
		synchronized (mRunningStmts) {
			regExp = mRunningStmts.remove(event.getStatementStatistics());
		}
		if (regExp != null) {
			StatementStatistics stmt = event.getStatementStatistics();

			mTrace.info(stmt.toString());

			synchronized (mHistoryMap) {
				List<String> l = mHistoryMap.get(regExp);
				if (l == null) {
					l = new ArrayList<>();
					mHistoryMap.put(regExp, l);
				}
				l.add(stmt.toString());
				if (l.size() > 200) {
					l.remove(0);
				}
			}
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
	 */
	@Override
	public void clearStatistics() {
		synchronized (mRunningStmts) {
			mRunningStmts.clear();
		}
		synchronized (mHistoryMap) {
			mHistoryMap.clear();
		}
	}

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		if (mHistoryMap.isEmpty()) {
			return null;
		}

		StringBuffer strb = new StringBuffer("[SpecialStatementListener[");
		synchronized (mHistoryMap) {

			for (Map.Entry<String, List<String>> e : mHistoryMap.entrySet()) {
				List<String> stmts = e.getValue();

				strb.append("\nhistory list for (" + e.getKey() + "):\n");

				int i = 1;
				for (String s : stmts) {
					strb.append(i + ": ");
					strb.append(s);
					strb.append("\n");
					i++;
				}
			}
			strb.append("]]\n");
		}

		strb.append("]\n");
		return strb.toString();
	}
}
