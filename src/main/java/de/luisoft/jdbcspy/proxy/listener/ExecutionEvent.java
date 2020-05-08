package de.luisoft.jdbcspy.proxy.listener;

import de.luisoft.jdbcspy.proxy.StatementStatistics;

/**
 * The Execution Event class.
 */
public class ExecutionEvent {

    /**
     * the statement statistics
     */
    private final StatementStatistics mStmtStats;

    /**
     * Constructor.
     *
     * @param stmtStat the statement
     */
    public ExecutionEvent(StatementStatistics stmtStat) {
        mStmtStats = stmtStat;
    }

    /**
     * Get the statement statistics.
     *
     * @return statistics
     */
    public StatementStatistics getStatementStatistics() {
        return mStmtStats;
    }
}
