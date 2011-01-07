package de.luisoft.jdbcspy.proxy.listener;

/**
 * The Execution failed listener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionListener.java 685 2006-04-21 15:45:01Z lbaeumer $
 */
public interface ExecutionFailedListener {

    /**
     * Execution Failed.
     * @param event ExecutionFailedEvent
     */
    void executionFailed(ExecutionFailedEvent event);

    /**
     * Clear the statistical data.
     */
    void clearStatistics();
}
