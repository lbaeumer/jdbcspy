package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The Execution Failed Listener.
 */
public class ExecutionFailedHistoryListener implements ExecutionFailedListener {

    /**
     * the list
     */
    private final List<ExecutionFailedEvent> mList = new ArrayList<>();

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#executionFailed
     */
    @Override
    public void executionFailed(ExecutionFailedEvent event) {
        synchronized (mList) {
            mList.add(event);
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
        synchronized (mList) {
            mList.clear();
        }
    }

    /**
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {
        if (mList.isEmpty()) {
            return null;
        }

        StringBuilder strb = new StringBuilder("[ExecutionFailedHistoryListener[\n");
        synchronized (mList) {
            int i = 1;
            for (ExecutionFailedEvent ev : mList) {
                strb.append("  ").append(i).append(": ");
                strb.append(ev.getStatement()).append(" failed, cause: ").append(ev.getCause());
                strb.append("\n");
                i++;
            }
        }
        strb.append("]");
        return strb.toString();
    }
}
