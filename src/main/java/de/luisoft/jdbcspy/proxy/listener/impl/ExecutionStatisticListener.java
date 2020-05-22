package de.luisoft.jdbcspy.proxy.listener.impl;

import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The Execution Statistic checker.
 */
public class ExecutionStatisticListener extends ExecutionAdapter {

    /**
     * the time map
     */
    private final Map<String, Integer> mTimeMap;

    /**
     * the length map
     */
    private final Map<String, Integer> mLengthMap;
    /**
     * the categories
     */
    private final String[] LENGTH_CAT_STRING = {"{<=20}", "{20-40}", "{40-70}", "{70-100}", "{100-150}", "{150-200}",
            "{200-250}", "{250-300}", "{350-400}", "{450-500}", "{500-600}", "{600-700}", "{700-800}", "{800-900}",
            "{900-1k}", "{1k-1200}", "{1200-1500}", "{1500-2k}", "{2k-2500}", "{2500-3k}", "{3k-4k}", "{4k-5k}",
            "{5k-10k}", "{>10k}"};
    /**
     * the categories
     */
    private final String[] TIME_CAT_STRING = {"{<=3ms}", "{3-5ms}", "{5-10ms}", "{10-100ms}", "{100-500ms}",
            "{500-1000ms}", "{1-3s}", "{3-5s}", "{5-10s}", "{10-20s}", "{20-30s}", "{30s-1m}", "{1-3m}", "{3-5m}",
            "{5-10m}", "{10-20m}", "{20-30m}", "{30-60m}", "{1h-2h}", "{2h-5h}", "{5h-10h}", "{10h-1d}", "{>1d}"};
    /**
     * the init date
     */
    private final Date mInitDate;
    private final Utils utils = new Utils();
    /**
     * the total statement count
     */
    private long mStmtCount;
    /**
     * the item count
     */
    private long mItemCount;
    /**
     * the duration
     */
    private long mDuration;
    /**
     * the total size
     */
    private long mSize;
    /**
     * the max length
     */
    private long mMaxLength;
    /**
     * the total length
     */
    private long mTotalLength;

    /**
     * Constructor.
     */
    public ExecutionStatisticListener() {
        mTimeMap = new HashMap<>();
        mLengthMap = new HashMap<>();
        mInitDate = new Date();
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
     */
    @Override
    public void startExecution(ExecutionEvent event) {
        int length = event.getStatementStatistics().getSQL().length();
        mTotalLength += length;
        if (length > mMaxLength) {
            mMaxLength = length;
        }
        mStmtCount++;

        String cat = getLengthCategory(length);
        synchronized (mLengthMap) {
            Integer cnt = mLengthMap.get(cat);
            if (cnt == null) {
                mLengthMap.put(cat, 1);
            } else {
                Integer newInt = cnt + 1;
                mLengthMap.put(cat, newInt);
            }
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#closeStatement
     */
    @Override
    public void closeStatement(CloseEvent event) {
        StatementStatistics stmt = event.getStatementStatistics();
        mDuration += stmt.getDuration();
        mItemCount += stmt.getItemCount();
        mSize += stmt.getSize();

        String cat = getCategory(stmt.getDuration());

        synchronized (mTimeMap) {
            Integer cnt = mTimeMap.get(cat);
            if (cnt == null) {
                mTimeMap.put(cat, 1);
            } else {
                Integer newInt = cnt + 1;
                mTimeMap.put(cat, newInt);
            }
        }
    }

    /**
     * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
     */
    @Override
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
     *
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

    /**
     * Get the category.
     *
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

    /**
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {

        StringBuilder map = new StringBuilder(
                "[ExecutionStatisticListener[" + "online since " + utils.getFormattedDate(mInitDate) + " ("
                        + Utils.getTimeString((System.currentTimeMillis() - mInitDate.getTime())) + ")\n  #stmt="
                        + mStmtCount + "; #rs=" + mItemCount + (mSize > 0 ? "; size=" + Utils.getSizeString(mSize) : "")
                        + "; duration=" + Utils.getTimeString(mDuration));

        if (mStmtCount != 0) {
            map.append("; avgDuration=").append(Utils.getTimeString(mDuration / mStmtCount)).append("; maxLength=").append(mMaxLength).append("; avgLength=").append(mTotalLength / mStmtCount).append(";\n  time=");

            boolean first = true;
            synchronized (mTimeMap) {
                for (String s : TIME_CAT_STRING) {
                    Integer count = mTimeMap.get(s);
                    if (count != null) {
                        if (!first) {
                            map.append(", ");
                        }
                        first = false;
                        map.append(s).append("=").append(count);
                    }
                }
            }

            map.append(";\n  length=");
            first = true;
            synchronized (mLengthMap) {
                for (String s : LENGTH_CAT_STRING) {
                    Integer count = mLengthMap.get(s);
                    if (count != null) {
                        if (!first) {
                            map.append(", ");
                        }
                        first = false;
                        map.append(s).append("=").append(count);
                    }
                }
            }
        }

        map.append("\n]]\n");

        return map.toString();
    }
}
