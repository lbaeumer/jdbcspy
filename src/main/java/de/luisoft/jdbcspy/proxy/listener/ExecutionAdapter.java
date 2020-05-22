package de.luisoft.jdbcspy.proxy.listener;

/**
 * The ExecutionAdapter.
 */
public class ExecutionAdapter implements ExecutionListener {

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    @Override
    public void startExecution(ExecutionEvent event) {
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#endExecution
     */
    @Override
    public void endExecution(ExecutionEvent event) {
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
     */
    @Override
    public void closeStatement(CloseEvent event) {
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#resourceFailure
     */
    @Override
    public void resourceFailure(ResourceEvent event) {
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
    }
}
