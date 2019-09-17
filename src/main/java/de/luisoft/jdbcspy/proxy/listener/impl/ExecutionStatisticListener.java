package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The Execution Statistic checker.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ExecutionStatisticListener.java 766 2006-06-19 18:41:37Z lui $
 */
public class ExecutionStatisticListener extends ExecutionAdapter {

    /** the time map */
    private final Map mTimeMap;

    /** the length map */
    private final Map mLengthMap;

    /** the total statement count */
    private long mStmtCount;

    /** the item count */
    private long mItemCount;

    /** the duration */
    private long mDuration;

    /** the total size */
    private long mSize;

    /** the max length */
    private long mMaxLength;

    /** the total length */
    private long mTotalLength;

    /** the init date */
    private Date mInitDate;

    /**
     * Constructor.
     */
    public ExecutionStatisticListener() {
        mTimeMap = new HashMap();
        mLengthMap = new HashMap();
        mInitDate = new Date();
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    public void startExecution(ExecutionEvent event) {
        int length = event.getStatementStatistics().getSQL().length();
        mTotalLength += length;
        if (length > mMaxLength) {
            mMaxLength = length;
        }
        mStmtCount++;

        String cat = getLengthCategory(length);
        synchronized (mLengthMap) {
            Integer cnt = (Integer) mLengthMap.get(cat);
            if (cnt == null) {
                mLengthMap.put(cat, new Integer(1));
            }
            else {
                Integer newInt = new Integer(cnt.intValue() + 1);
                mLengthMap.put(cat, newInt);
            }
        }
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
     */
    public void closeStatement(CloseEvent event) {
        StatementStatistics stmt = event.getStatementStatistics();
        mDuration += stmt.getDuration();
        mItemCount += stmt.getItemCount();
        mSize += stmt.getSize();

        String cat = getCategory(stmt.getDuration());

        synchronized (mTimeMap) {
            Integer cnt = (Integer) mTimeMap.get(cat);
            if (cnt == null) {
                mTimeMap.put(cat, new Integer(1));
            }
            else {
                Integer newInt = new Integer(cnt.intValue() + 1);
                mTimeMap.put(cat, newInt);
            }
        }
    };

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    public void clearStatistics() {
        synchronized (mTimeMap) {
            mTimeMap.clear();
        }
        synchronized (mLengthMap) {
            mLengthMap.clear();
        }
        mStmtCount = 0;
        mItemCount = 0;
        mDuration = 0;
        mSize = 0;
        mMaxLength = 0;
        mTotalLength = 0;
    }

    /**
     * Get the length category.
     * @param length long
     * @return String
     */
    private String getLengthCategory(int length) {
        int idx = 0;
        if (length <= 20) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 40) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 70) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 100) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 150) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 200) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 250) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 300) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 400) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 500) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 600) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 700) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 800) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 900) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 1000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 1200) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 1500) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 2000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 2500) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 3000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 4000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 5000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;
        if (length <= 10000) {
            return LENGTH_CAT_STRING[idx];
        }
        idx++;

        return LENGTH_CAT_STRING[idx];
    }

    /** the categories */
    private final String[] LENGTH_CAT_STRING = {
        "{<=20}", "{20-40}", "{40-70}", "{70-100}", "{100-150}", "{150-200}",
        "{200-250}", "{250-300}", "{350-400}", "{450-500}", "{500-600}",
        "{600-700}", "{700-800}", "{800-900}", "{900-1k}", "{1k-1200}",
        "{1200-1500}", "{1500-2k}", "{2k-2500}", "{2500-3k}", "{3k-4k}"
        ,"{4k-5k}", "{5k-10k}", "{>10k}"
    };

    /**
     * Get the category.
     * @param execTime long
     * @return String
     */
    private String getCategory(long execTime) {
        int idx = 0;
        if (execTime <= 3) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 5) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 10) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 100) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 500) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 1000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 3000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 5000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 10000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 20000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 30000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 3 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 5 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 10 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 20 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 30 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 60 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 120 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 300 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 600 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;
        if (execTime <= 24 * 60 * 60000) {
            return TIME_CAT_STRING[idx];
        }
        idx++;

        return TIME_CAT_STRING[idx];
    }

    /** the categories */
    private final String[] TIME_CAT_STRING = {
        "{<=3ms}", "{3-5ms}", "{5-10ms}", "{10-100ms}", "{100-500ms}",
        "{500-1000ms}",
        "{1-3s}", "{3-5s}", "{5-10s}", "{10-20s}", "{20-30s}",
        "{30s-1m}", "{1-3m}", "{3-5m}", "{5-10m}", "{10-20m}",
        "{20-30m}", "{30-60m}", "{1h-2h}", "{2h-5h}", "{5h-10h}",
        "{10h-1d}", "{>1d}"
    };

    /**
     * @see java.lang.Object#toString
     */
    public String toString() {

        StringBuffer map = new StringBuffer("[ExecutionStatisticListener["
            + "online since " + Utils.getFormattedDate(mInitDate) + " ("
            + Utils.getTimeString((System.currentTimeMillis() - mInitDate.getTime()))
            + ")\n#stmt="
            + mStmtCount + "; #rs=" + mItemCount
            + (mSize > 0 ? "; size=" + Utils.getSizeString(mSize) : "")
            + "; duration="
            + Utils.getTimeString(mDuration));

        if (mStmtCount != 0) {
            map.append("; avgDuration="
                       + Utils.getTimeString(mDuration / mStmtCount)
                       + "; maxLength=" + mMaxLength
                       + "; avgLength=" + (mTotalLength / mStmtCount)
                       + ";\ntime=");

            boolean first = true;
            synchronized (mTimeMap) {
                for (int i = 0; i < TIME_CAT_STRING.length; i++) {
                    Integer count = (Integer) mTimeMap.get(TIME_CAT_STRING[i]);
                    if (count != null) {
                        if (!first) {
                            map.append(", ");
                        }
                        first = false;
                        map.append(TIME_CAT_STRING[i] + "=" + count);
                    }
                }
            }

            map.append(";\nlength=");
            first = true;
            synchronized (mLengthMap) {
                for (int i = 0; i < LENGTH_CAT_STRING.length; i++) {
                    Integer count = (Integer) mLengthMap.get(LENGTH_CAT_STRING[i]);
                    if (count != null) {
                        if (!first) {
                            map.append(", ");
                        }
                        first = false;
                        map.append(LENGTH_CAT_STRING[i] + "=" + count);
                    }
                }
            }
        }

        map.append("\n]]\n");

        return map.toString();
    }
}
