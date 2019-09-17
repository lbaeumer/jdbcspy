package de.luisoft.jdbcspy.proxy.listener;


/**
 * The ExecutionAdapter.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionAdapter.java 701 2006-04-23 11:43:51Z lui $
 */
public class ExecutionAdapter implements ExecutionListener {

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    public void startExecution(ExecutionEvent event) {
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#endExecution
     */
    public void endExecution(ExecutionEvent event) {
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
     */
    public void closeStatement(CloseEvent event) {
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#resourceFailure
     */
    public void resourceFailure(ResourceEvent event) {
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    public void clearStatistics() {
    };
}
