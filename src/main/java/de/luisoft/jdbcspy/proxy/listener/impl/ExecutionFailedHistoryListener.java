package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.ArrayList;
import java.util.List;

import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;

/**
 * The Execution Failed Listener.
 */
public class ExecutionFailedHistoryListener implements ExecutionFailedListener {

	/** the list */
	private List<ExecutionFailedEvent> mList = new ArrayList<>();

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#executionFailed
	 */
	@Override
	public void executionFailed(ExecutionFailedEvent event) {
		synchronized (mList) {
			mList.add(event);
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#clearStatistics
	 */
	@Override
	public void clearStatistics() {
		synchronized (mList) {
			mList.clear();
		}
	};

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		if (mList.isEmpty()) {
			return null;
		}

		StringBuffer strb = new StringBuffer("[ExecutionFailedHistoryListener[\n");
		synchronized (mList) {
			int i = 1;
			for (ExecutionFailedEvent ev : mList) {
				strb.append(i + ": ");
				strb.append(ev.getStatement() + " failed, cause: " + ev.getCause());
				strb.append("\n");
				i++;
			}
		}
		strb.append("]");
		return strb.toString();
	}
}
