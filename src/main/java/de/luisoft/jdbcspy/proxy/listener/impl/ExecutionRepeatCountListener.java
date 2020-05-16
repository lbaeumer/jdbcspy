package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * The Execution Repeat checker.
 */
public class ExecutionRepeatCountListener extends ExecutionAdapter {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(ExecutionRepeatCountListener.class.getName());
    /**
     * max print size
     */
    private static final int MAX_PRINT_SIZE = 10;
    /**
     * the time map
     */
    private final Map<String, Integer> mMap;
    private int repeatCountStmtSize;
    private int repeatCountThreshold;

    /**
     * Constructor.
     */
    public ExecutionRepeatCountListener() {
        mMap = new HashMap<>();
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    @Override
    public void startExecution(ExecutionEvent event) {
        if (repeatCountStmtSize == 0) {
            return;
        }

        String stmt = event.getStatementStatistics().getSQL();

        Integer count;
        int size;
        synchronized (mMap) {
            count = mMap.get(stmt);
            if (count != null) {
                mMap.put(stmt, count + 1);
                if ((count + 1) % repeatCountThreshold == 0) {
                    mTrace.warning(
                            "The statement " + stmt + " in method " + event.getStatementStatistics().getExecuteCaller()
                                    + " has been executed " + (count + 1) + " times ");
                }
            } else {
                mMap.put(stmt, 1);
            }
            size = mMap.size();
        }

        if (size > repeatCountStmtSize) {
            // clear some entries
            int smallest2n = Integer.MAX_VALUE;
            int smallest = Integer.MAX_VALUE;
            int cnt = 0;
            synchronized (mMap) {
                Collection<Integer> c = mMap.values();
                for (Integer i : c) {
                    if (i < smallest) {
                        smallest = i;
                        cnt = 1;
                    } else if (i == smallest) {
                        cnt++;
                    } else if (i < smallest2n) {
                        smallest2n = i;
                    }
                }

                // delete at least 15% and maximum 50% of all entries
                if (cnt < 0.85f * mMap.size()) {
                    smallest = smallest2n;
                }

                int maxDelete = mMap.size() / 2;
                for (Iterator it = mMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry entry = (Map.Entry) it.next();
                    cnt = (Integer) entry.getValue();
                    if (cnt == smallest) {
                        it.remove();
                        maxDelete--;
                        if (maxDelete == 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    @Override
    public void clearStatistics() {
        if (repeatCountStmtSize == 0) {
            return;
        }

        synchronized (mMap) {
            mMap.clear();
        }
    }

    /**
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {
        if (repeatCountStmtSize == 0) {
            return null;
        }

        StringBuilder strb = new StringBuilder("[ExecutionRepeatCountListener[\n");
        Set<Map.Entry<String, Integer>> s = new TreeSet<>((e1, e2) -> {
            int c = e2.getValue() - e1.getValue();
            if (c != 0) {
                return c;
            }
            return e2.getKey().compareTo(e1.getKey());
        });

        synchronized (mMap) {
            s.addAll(mMap.entrySet());
        }

        int count = MAX_PRINT_SIZE;
        for (Map.Entry<String, Integer> e : s) {
            strb.append(MAX_PRINT_SIZE - count + 1).append(": #=").append(e.getValue()).append(": \"").append(e.getKey()).append("\"\n");
            count--;
            if (count <= 0) {
                break;
            }
        }
        strb.append("]]\n");
        return strb.toString();
    }
}
