package de.luisoft.jdbcspy.proxy.listener;

/**
 * The Execution Event class.
 */
public class ExecutionFailedEvent {

    /**
     * the statement
     */
    private final String mStmt;

    /**
     * the cause
     */
    private final Throwable mCause;

    /**
     * Constructor.
     *
     * @param stmt  the statement
     * @param cause the cause
     */
    public ExecutionFailedEvent(String stmt, Throwable cause) {
        mStmt = stmt;
        mCause = cause;
    }

    /**
     * Get the statement.
     *
     * @return statistics
     */
    public String getStatement() {
        return mStmt;
    }

    /**
     * Get the cause.
     *
     * @return Throwable
     */
    public Throwable getCause() {
        return mCause;
    }
}
