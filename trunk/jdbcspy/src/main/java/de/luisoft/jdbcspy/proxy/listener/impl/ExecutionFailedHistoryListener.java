package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;

/**
 * The Execution Failed Listener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionRepeatCountListener.java 686 2006-05-08 09:59:04Z lbaeumer $
 */
public class ExecutionFailedHistoryListener implements ExecutionFailedListener {

    /** the list */
    private List mList = new ArrayList();

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#executionFailed
     */
    public void executionFailed(ExecutionFailedEvent event) {
        synchronized (mList) {
            mList.add(event);
        }
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener#clearStatistics
     */
    public void clearStatistics() {
        synchronized (mList) {
            mList.clear();
        }
    };

    /**
     * @see java.lang.Object#toString
     */
    public String toString() {
        if (mList.isEmpty()) {
            return null;
        }

        StringBuffer strb = new StringBuffer("[ExecutionFailedHistoryListener[\n");
        synchronized (mList) {
            int i = 1;
            for (Iterator it = mList.iterator(); it.hasNext(); ) {
                ExecutionFailedEvent ev = (ExecutionFailedEvent) it.next();
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
