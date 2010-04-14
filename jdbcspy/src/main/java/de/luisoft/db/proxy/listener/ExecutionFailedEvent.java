package de.luisoft.db.proxy.listener;

/**
 * The Execution Event class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionEvent.java 686 2006-05-08 09:59:04Z lbaeumer $
 */
public class ExecutionFailedEvent {

    /** the statement */
    private String mStmt;

    /** the cause */
    private Throwable mCause;

    /**
     * Constructor.
     * @param stmt the statement
     * @param cause the cause
     */
    public ExecutionFailedEvent(String stmt, Throwable cause) {
        mStmt = stmt;
        mCause = cause;
    }

    /**
     * Get the statement.
     * @return statistics
     */
    public String getStatement() {
        return mStmt;
    }

    /**
     * Get the cause.
     * @return Throwable
     */
    public Throwable getCause() {
        return mCause;
    }
}
