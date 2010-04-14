package de.luisoft.db.proxy.listener.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.db.ClientProperties;
import de.luisoft.db.proxy.listener.ExecutionAdapter;
import de.luisoft.db.proxy.listener.ExecutionEvent;

/**
 * The Execution Repeat checker.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionRepeatCountListener.java 915 2007-10-04 19:04:02Z lui $
 */
public class ExecutionRepeatCountListener extends ExecutionAdapter {

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(ExecutionRepeatCountListener.class);

    /** the time map */
    private Map mMap;

    /** max print size */
    private static final int MAX_PRINT_SIZE = 10;

    /**
     * Constructor.
     */
    public ExecutionRepeatCountListener() {
        mMap = new HashMap();
    }

    private int repeatCountStmtSize;
    private int repeatCountThreshold;

    public int getRepeatCountStmtSize() {
		return repeatCountStmtSize;
	}

	public void setRepeatCountStmtSize(final int repeatCountStmtSize) {
		this.repeatCountStmtSize = repeatCountStmtSize;
        mMap = new HashMap((int) (1.35 * repeatCountStmtSize));
	}

	public int getRepeatCountThreshold() {
		return repeatCountThreshold;
	}

	public void setRepeatCountThreshold(int repeatCountThreshold) {
		this.repeatCountThreshold = repeatCountThreshold;
	}

	/**
     * @see de.luisoft.db.proxy.listener.ExecutionListener#startExecution
     */
    public void startExecution(ExecutionEvent event) {
        if (repeatCountStmtSize == 0) {
            return;
        }

        String stmt = event.getStatementStatistics().getSQL();

        Integer count;
        int size = 0;
        synchronized (mMap) {
            count = (Integer) mMap.get(stmt);
            if (count != null) {
                mMap.put(stmt, new Integer(count.intValue() + 1));
                if ((count.intValue() + 1) % repeatCountThreshold == 0) {
                    mTrace.warn("The statement " + stmt
                        + " in method "
                        + event.getStatementStatistics().getExecuteCaller()
                        + " has been executed "
                        + (count.intValue() + 1) + " times ");
                }
            }
            else {
                mMap.put(stmt, new Integer(1));
            }
            size = mMap.size();
        }

        if (size > repeatCountStmtSize) {
            // clear some entries
            int smallest2n = Integer.MAX_VALUE;
            int smallest = Integer.MAX_VALUE;
            int cnt = 0;
            synchronized (mMap) {
                Collection c = mMap.values();
                for (Iterator it = c.iterator(); it.hasNext(); ) {
                    int i = ((Integer) it.next()).intValue();
                    if (i < smallest) {
                        smallest = i;
                        cnt = 1;
                    }
                    else if (i == smallest) {
                        cnt++;
                    }
                    else if (i < smallest2n) {
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
                    cnt = ((Integer) entry.getValue()).intValue();
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
    };

    /**
     * @see de.luisoft.db.proxy.listener.ExecutionListener#clearStatistics
     */
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
    public String toString() {
        if (repeatCountStmtSize == 0) {
            return null;
        }

        StringBuffer strb = new StringBuffer("[ExecutionRepeatCountListener[\n");
        Set s = new TreeSet(new Comparator() {
            public int compare(Object o1, Object o2) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;
                int c = ((Integer) e2.getValue()).intValue()
                        - ((Integer) e1.getValue()).intValue();
                if (c != 0) {
                    return c;
                }
                return ((String) e2.getKey()).compareTo((String) e1.getKey());
            }
        });

        synchronized (mMap) {
            s.addAll(mMap.entrySet());
        }

        int count = MAX_PRINT_SIZE;
        for (Iterator it = s.iterator(); it.hasNext() && count > 0; ) {
            Map.Entry e = (Map.Entry) it.next();
            strb.append((MAX_PRINT_SIZE - count + 1)
                        + ": #=" + e.getValue() + ": \"" + e.getKey() + "\"\n");
            count--;
        }
        strb.append("]]\n");
        return strb.toString();
    }
}
