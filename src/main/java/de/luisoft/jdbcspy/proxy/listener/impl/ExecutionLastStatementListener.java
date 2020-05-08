package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

/**
 * The Execution Repeat checker.
 */
public class ExecutionLastStatementListener extends ExecutionAdapter {

    /**
     * the logger object for tracing
     */
    private static final Log mTrace = LogFactory.getLog(ExecutionLastStatementListener.class);

    private int lastStatementMaxHistory;
    private int lastStatementExecutionThreshold;

    /**
     * the time map
     */
    private Entry[] mEntries;

    /**
     * the current position
     */
    private int mCurrentPos;

    public void setLastStatementMaxHistory(final int l) {
        lastStatementMaxHistory = l;
        mEntries = new Entry[l];
    }

    public void setLastStatementExecutionThreshold(int l) {
        lastStatementExecutionThreshold = l;
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    @Override
    public void startExecution(ExecutionEvent event) {
        if (lastStatementMaxHistory == 0) {
            return;
        }

        StatementStatistics stmt = event.getStatementStatistics();

        for (int i = 0; i < lastStatementMaxHistory; i++) {
            // check the statement at mCurrentPos -i
            Entry e = mEntries[(2 * lastStatementMaxHistory + mCurrentPos - i - 1) % lastStatementMaxHistory];
            if (e != null && stmt.getSQL().equals(e.event)) {
                e.count++;
                e.totalCount++;
                if (e.count >= lastStatementExecutionThreshold) {
                    e.count = 0;
                    mTrace.warn("Statement " + stmt + " called in method " + stmt.getExecuteCaller() + " was executed "
                            + e.totalCount + " times in a row.");
                }
                return;
            }
        }

        // event not found, so store it
        Entry newEntry = new Entry();
        newEntry.count = 1;
        newEntry.totalCount = 1;
        newEntry.event = stmt.getSQL();
        newEntry.method = stmt.getExecuteCaller();
        mEntries[mCurrentPos] = newEntry;
        mCurrentPos = (mCurrentPos + 1) % lastStatementMaxHistory;
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
        if (lastStatementMaxHistory == 0) {
            return;
        }

        mCurrentPos = 0;
        Arrays.fill(mEntries, null);
    }

    /**
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {
        if (lastStatementMaxHistory == 0) {
            return null;
        }

        StringBuilder strb = new StringBuilder("[ExecutionLastStatementListener[\n");
        for (int i = 0; i < lastStatementMaxHistory; i++) {
            Entry e = mEntries[(2 * lastStatementMaxHistory + mCurrentPos - i - 1) % lastStatementMaxHistory];
            if (e != null) {
                strb.append(i).append(": \"").append(e.event).append("\" (#=").append(e.count).append(", ").append(e.method).append(")\n");
            }
        }
        strb.append("]]\n");
        return strb.toString();
    }

    /**
     * The entry class.
     */
    private static class Entry {
        /**
         * the event
         */
        String event;
        /**
         * the count
         */
        int count;
        /**
         * the total count
         */
        int totalCount;
        /**
         * the method
         */
        String method;
    }
}
