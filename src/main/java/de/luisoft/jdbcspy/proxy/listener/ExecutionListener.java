package de.luisoft.jdbcspy.proxy.listener;


/**
 * The Execution time listener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionListener.java 701 2006-04-23 11:43:51Z lui $
 */
public interface ExecutionListener {
    /**
     * Start the execution.
     * @param event ExecutionEvent
     */
    void startExecution(ExecutionEvent event);

    /**
     * End the execution.
     * @param event ExecutionEvent
     */
    void endExecution(ExecutionEvent event);

    /**
     * Close the execution.
     * @param event ClosedEvent
     */
    void closeStatement(CloseEvent event);

    /**
     * Resource Failure.
     * @param event ResourceEvent
     */
    void resourceFailure(ResourceEvent event);

    /**
     * Clear the statistical data.
     */
    void clearStatistics();
}
