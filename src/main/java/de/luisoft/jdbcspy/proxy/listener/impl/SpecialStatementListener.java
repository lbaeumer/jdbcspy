package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The Special statement listener.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionTimeListener.java 642 2006-01-22 13:34:00Z lui $
 */
public class SpecialStatementListener extends ExecutionAdapter {

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(SpecialStatementListener.class);

    /** the time map */
    private Map mRunningStmts = new HashMap();

    /** the history map */
    private Map mHistoryMap = new HashMap();

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    public void startExecution(ExecutionEvent event) {
        StatementStatistics stmt = event.getStatementStatistics();
        String regExp = Utils.isHistoryTrace(stmt.getSQL());
        if (regExp != null) {
            synchronized (mRunningStmts) {
                mRunningStmts.put(stmt, regExp);
            }
        }
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
     */
    public void closeStatement(CloseEvent event) {
        String regExp = null;
        synchronized (mRunningStmts) {
            regExp = (String) mRunningStmts.remove(event.getStatementStatistics());
        }
        if (regExp != null) {
            StatementStatistics stmt = event.getStatementStatistics();

            mTrace.info(stmt.toString());

            synchronized (mHistoryMap) {
                List l = (List) mHistoryMap.get(regExp);
                if (l == null) {
                    l = new ArrayList();
                    mHistoryMap.put(regExp, l);
                }
                l.add(stmt.toString());
                if (l.size() > 200) {
                    l.remove(0);
                }
            }
        }
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    public void clearStatistics() {
        synchronized (mRunningStmts) {
            mRunningStmts.clear();
        }
        synchronized (mHistoryMap) {
            mHistoryMap.clear();
        }
    }

    /**
     * @see java.lang.Object#toString
     */
    public String toString() {
        if (mHistoryMap.isEmpty()) {
            return null;
        }

        StringBuffer strb = new StringBuffer("[SpecialStatementListener[");
        synchronized (mHistoryMap) {

            for (Iterator regExpIt = mHistoryMap.entrySet().iterator(); regExpIt.hasNext(); ) {
                Map.Entry e = (Map.Entry) regExpIt.next();
                String regExp = (String) e.getKey();
                List stmts = (List) e.getValue();

                strb.append("\nhistory list for (" + regExp + "):\n");

                int i = 1;
                for (Iterator it = stmts.iterator(); it.hasNext(); i++) {
                    strb.append(i + ": ");
                    strb.append(it.next());
                    strb.append("\n");
                }
            }
            strb.append("]]\n");
        }

        strb.append("]\n");
        return strb.toString();
    }
}
