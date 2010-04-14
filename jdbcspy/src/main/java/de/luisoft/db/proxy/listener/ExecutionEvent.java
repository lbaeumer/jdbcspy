package de.luisoft.db.proxy.listener;

import de.luisoft.db.proxy.StatementStatistics;

/**
 * The Execution Event class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionEvent.java 724 2006-05-19 18:58:13Z lui $
 */
public class ExecutionEvent {

    /** the statement statistics */
    private StatementStatistics mStmtStats;

    /**
     * Constructor.
     * @param stmtStat the statement
     */
    public ExecutionEvent(StatementStatistics stmtStat) {
        mStmtStats = stmtStat;
    }

    /**
     * Get the statement statistics.
     * @return statistics
     */
    public StatementStatistics getStatementStatistics() {
        return mStmtStats;
    }
}
