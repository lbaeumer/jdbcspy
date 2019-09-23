package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The Execution checker.
 */
public class ExecutionTimeListener extends ExecutionAdapter {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ExecutionTimeListener.class);

	/** max history */
	private static final int MAX_HISTORY = 15;

	/** the time map */
	private Map<StatementStatistics, Integer> mRunningStmts = new HashMap<>();

	/** the statements that are executed but not closed */
	private Map<StatementStatistics, Integer> mWaiting = new HashMap<>();

	private Utils utils = new Utils();

	/** the history map */
	private SortedSet<Entry> mHistorySet = new TreeSet<>(new Comparator<Entry>() {
		@Override
		public int compare(Entry l1, Entry l2) {
			long c = (l2.duration - l1.duration);
			if (c > 0) {
				return 1;
			}
			if (c < 0) {
				return -1;
			}
			return l1.stmt.compareTo(l2.stmt);
		}
	});

	private Runnable r;
	private int longExecutionThreshold;

	public int getLongExecutionThreshold() {
		return longExecutionThreshold;
	}

	public void setLongExecutionThreshold(int longExecutionThreshold) {
		this.longExecutionThreshold = longExecutionThreshold;
	}

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
	 */
	@Override
	public void startExecution(ExecutionEvent event) {
		if (r == null) {
			r = new Runnable() {
				@Override
				public void run() {
					Set<Map.Entry<StatementStatistics, Integer>> loopSet = new TreeSet<>(
							new Comparator<Map.Entry<StatementStatistics, Integer>>() {
								@Override
								public int compare(Map.Entry<StatementStatistics, Integer> e1,
										Map.Entry<StatementStatistics, Integer> e2) {
									StatementStatistics stmt1 = e1.getKey();
									StatementStatistics stmt2 = e2.getKey();

									return (int) (stmt2.getExecutionStartTime() - stmt1.getExecutionStartTime());
								}
							});

					while (true) {
						try {
							Thread.sleep(60000); // 1 min
							Date now = new Date();

							loopSet.clear();
							synchronized (mRunningStmts) {
								loopSet.addAll(mRunningStmts.entrySet());
							}

							for (Map.Entry<StatementStatistics, Integer> entry : loopSet) {

								StatementStatistics stmt = entry.getKey();
								int loop = entry.getValue().intValue();

								int wait;
								switch (loop) {
								case 1:
									wait = 1;
									break;
								case 2:
									wait = 2;
									break;
								case 3:
									wait = 3;
									break;
								case 4:
									wait = 5;
									break;
								case 5:
									wait = 10;
									break;
								case 6:
									wait = 15;
									break;
								case 7:
									wait = 20;
									break;
								case 8:
									wait = 25;
									break;
								case 9:
									wait = 30;
									break;
								case 10:
									wait = 45;
									break;
								default: {
									wait = (loop <= 15 ? 60 * (loop - 10) : 120 * (loop - 13));
								}
								}

								long execTime = (now.getTime() - stmt.getExecutionStartTime());
								if (execTime > longExecutionThreshold * wait) {
									entry.setValue(new Integer(loop + 1));
									printMessage(stmt.getSQL(), loop, execTime, false, stmt.getExecuteCaller());
								}
							}
						} catch (Exception e) {
							mTrace.warn("failed ", e);
						}
					}
				}
			};
			Thread t = new Thread(r, "CheckLongExecThread");
			t.setDaemon(true);
			t.start();
		}
		synchronized (mRunningStmts) {
			mRunningStmts.put(event.getStatementStatistics(), new Integer(1));
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#endExecution
	 */
	@Override
	public void endExecution(ExecutionEvent event) {
		Integer loop = null;
		synchronized (mRunningStmts) {
			loop = mRunningStmts.remove(event.getStatementStatistics());
		}
		synchronized (mWaiting) {
			mWaiting.put(event.getStatementStatistics(), loop);
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
	 */
	@Override
	public void closeStatement(CloseEvent event) {

		StatementStatistics stmt = event.getStatementStatistics();
		Integer loop = null;
		synchronized (mWaiting) {
			loop = mWaiting.remove(stmt);
		}
		if (loop != null) {
			Entry entry = new Entry();

			entry.duration = stmt.getDuration();
			entry.stmt = stmt.toString();

			synchronized (mHistorySet) {
				mHistorySet.add(entry);
				while (mHistorySet.size() > MAX_HISTORY) {
					mHistorySet.remove(mHistorySet.last());
				}
			}

			if (loop.intValue() > 1) {
				printMessage(stmt.getSQL(), loop.intValue(), entry.duration, true, stmt.getExecuteCaller());
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
		synchronized (mWaiting) {
			mWaiting.clear();
		}
		synchronized (mHistorySet) {
			mHistorySet.clear();
		}
	}

	/**
	 * Print the message.
	 * 
	 * @param sql
	 *            String
	 * @param loop
	 *            int
	 * @param execTime
	 *            long
	 * @param finished
	 *            boolean
	 * @param method
	 *            the method
	 */
	private void printMessage(String sql, int loop, long execTime, boolean finished, String method) {
		if (loop > 1 && sql.length() > 25) {
			sql = sql.substring(0, 25) + "...";
		}
		mTrace.warn("Statement " + sql + " called in " + method + (finished
				? " finished in " + utils.getTimeString(execTime)
				: " executing for " + utils.getTimeString(execTime) + (loop > 1 ? " (loop " + loop + ")" : "")));
	}

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		StringBuffer strb = new StringBuffer("[ExecutionTimeListener[\n");
		synchronized (mRunningStmts) {
			strb.append("currently executing:\n");
			int i = 1;
			for (StatementStatistics stmt : mRunningStmts.keySet()) {

				strb.append(i + ": ");
				strb.append(stmt);
				strb.append("\n");
				i++;
			}
		}

		synchronized (mWaiting) {
			strb.append("\nexecuted but waiting to be closed:\n");
			int i = 1;
			for (StatementStatistics stmt : mWaiting.keySet()) {
				strb.append(i + ": ");
				strb.append(stmt.toString());
				strb.append("\n");
				i++;
			}
		}

		strb.append("\nlong running history (execTime + iterTime):\n");
		synchronized (mHistorySet) {
			int i = 1;
			for (Entry entry : mHistorySet) {
				strb.append(i + ": ");
				strb.append(entry.stmt);
				strb.append("\n");
				i++;
			}
		}
		strb.append("]]\n");
		return strb.toString();
	}

	private class Entry {
		long duration;
		String stmt;
	}
}
