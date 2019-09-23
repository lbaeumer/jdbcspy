package de.luisoft.jdbcspy.proxy.listener;

/**
 * The Execution time listener.
 */
public interface ExecutionListener {
	/**
	 * Start the execution.
	 * 
	 * @param event
	 *            ExecutionEvent
	 */
	void startExecution(ExecutionEvent event);

	/**
	 * End the execution.
	 * 
	 * @param event
	 *            ExecutionEvent
	 */
	void endExecution(ExecutionEvent event);

	/**
	 * Close the execution.
	 * 
	 * @param event
	 *            ClosedEvent
	 */
	void closeStatement(CloseEvent event);

	/**
	 * Resource Failure.
	 * 
	 * @param event
	 *            ResourceEvent
	 */
	void resourceFailure(ResourceEvent event);

	/**
	 * Clear the statistical data.
	 */
	void clearStatistics();
}
