package de.luisoft.db.proxy.listener.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.db.ClientProperties;
import de.luisoft.db.proxy.StatementStatistics;
import de.luisoft.db.proxy.listener.CloseEvent;
import de.luisoft.db.proxy.listener.ExecutionAdapter;
import de.luisoft.db.proxy.listener.ExecutionEvent;
import de.luisoft.db.proxy.util.Utils;

/**
 * The Execution checker.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id$
 */
public class ExecutionTimeListener extends ExecutionAdapter {

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(ExecutionTimeListener.class);

    /** max history */
    private static final int MAX_HISTORY = 15;

    /** the time map */
    private Map mRunningStmts = new HashMap();

    /** the statements that are executed but not closed */
    private Map mWaiting = new HashMap();

    /** the history map */
    private SortedSet mHistorySet = new TreeSet(new Comparator() {
        public int compare(Object o1, Object o2) {
            Entry l1 = (Entry) o1;
            Entry l2 = (Entry) o2;
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
     * @see de.luisoft.db.proxy.listener.ExecutionListener#startExecution
     */
    public void startExecution(ExecutionEvent event) {
    	if (r == null) {
            r = new Runnable() {
                public void run() {
                    Set loopSet = new TreeSet(new Comparator() {
                        public int compare(Object o1, Object o2) {
                            Map.Entry e1 = (Map.Entry) o1;
                            Map.Entry e2 = (Map.Entry) o2;

                            StatementStatistics stmt1 = (StatementStatistics) e1.getKey();
                            StatementStatistics stmt2 = (StatementStatistics) e2.getKey();

                            return (int) (stmt2.getExecutionStartTime()
                                - stmt1.getExecutionStartTime());
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

                            for (Iterator it = loopSet.iterator(); it.hasNext(); ) {
                                Map.Entry entry = (Map.Entry) it.next();

                                StatementStatistics stmt = (StatementStatistics) entry.getKey();
                                int loop = ((Integer) entry.getValue()).intValue();

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
                                        wait = (loop <= 15
                                            ? 60 * (loop - 10)
                                            : 120 * (loop - 13));
                                    }
                                }

                                long execTime = (now.getTime() - stmt.getExecutionStartTime());
                                if (execTime > longExecutionThreshold * wait) {
                                    entry.setValue(new Integer(loop + 1));
                                    printMessage(stmt.getSQL(), loop, execTime, false,
                                                 stmt.getExecuteCaller());
                                }
                            }
                        }
                        catch (Exception e) {
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
     * @see de.luisoft.db.proxy.listener.ExecutionListener#endExecution
     */
    public void endExecution(ExecutionEvent event) {
        Integer loop = null;
        synchronized (mRunningStmts) {
            loop = (Integer) mRunningStmts.remove(event.getStatementStatistics());
        }
        synchronized (mWaiting) {
            mWaiting.put(event.getStatementStatistics(), loop);
        }
    };

    /**
     * @see de.luisoft.db.proxy.listener.ExecutionListener#closeStatement
     */
    public void closeStatement(CloseEvent event) {

        StatementStatistics stmt = event.getStatementStatistics();
        Integer loop = null;
        synchronized (mWaiting) {
            loop = (Integer) mWaiting.remove(stmt);
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
                printMessage(stmt.getSQL(), loop.intValue(),
                             entry.duration,
                             true, stmt.getExecuteCaller());
            }
        }
    };

    /**
     * @see de.luisoft.db.proxy.listener.ExecutionListener#clearStatistics
     */
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
     * @param sql String
     * @param loop int
     * @param execTime long
     * @param finished boolean
     * @param method the method
     */
    private void printMessage(String sql, int loop,
        long execTime, boolean finished, String method) {
        if (loop > 1 && sql.length() > 25) {
            sql = sql.substring(0, 25) + "...";
        }
        mTrace.warn("Statement " + sql
                    + " called in " + method
                    + (finished
                       ? " finished in " + Utils.getTimeString(execTime)
                       : " executing for "
                       + Utils.getTimeString(execTime)
                       + (loop > 1
                          ? " (loop " + loop + ")"
                          : "")));
    }

    /**
     * @see java.lang.Object#toString
     */
    public String toString() {
        StringBuffer strb = new StringBuffer("[ExecutionTimeListener[\n");
        synchronized (mRunningStmts) {
            strb.append("currently executing:\n");
            int i = 1;
            for (Iterator it = mRunningStmts.entrySet().iterator(); it.hasNext();
                i++) {
                Map.Entry entry = (Map.Entry) it.next();
                StatementStatistics stmt = (StatementStatistics) entry.getKey();

                strb.append(i + ": ");
                strb.append(stmt);
                strb.append("\n");
            }
        }

        synchronized (mWaiting) {
            strb.append("\nexecuted but waiting to be closed:\n");
            int i = 1;
            for (Iterator it = mWaiting.entrySet().iterator(); it.hasNext();
                i++) {
                Map.Entry entry = (Map.Entry) it.next();
                StatementStatistics stmt = (StatementStatistics) entry.getKey();
                strb.append(i + ": ");
                strb.append(stmt.toString());
                strb.append("\n");
            }
        }

        strb.append("\nlong running history (execTime + iterTime):\n");
        synchronized (mHistorySet) {
            int i = 1;
            for (Iterator it = mHistorySet.iterator(); it.hasNext();
                i++) {
                Entry entry = (Entry) it.next();
                strb.append(i + ": ");
                strb.append(entry.stmt);
                strb.append("\n");
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
