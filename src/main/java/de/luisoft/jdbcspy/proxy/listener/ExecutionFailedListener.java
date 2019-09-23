package de.luisoft.jdbcspy.proxy.listener;

/**
 * The Execution failed listener.
 */
public interface ExecutionFailedListener {

	/**
	 * Execution Failed.
	 * 
	 * @param event
	 *            ExecutionFailedEvent
	 */
	void executionFailed(ExecutionFailedEvent event);

	/**
	 * Clear the statistical data.
	 */
	void clearStatistics();
}
