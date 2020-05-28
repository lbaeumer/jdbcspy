package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.ProxyResultSet;
import de.luisoft.jdbcspy.proxy.ResultSetStatistics;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.Statistics;
import de.luisoft.jdbcspy.proxy.exception.ProxyException;
import de.luisoft.jdbcspy.proxy.exception.ResourceNotClosedException;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;
import de.luisoft.jdbcspy.proxy.listener.ResourceEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The statement handler.
 */
public abstract class AbstractStatementInvocationHandler implements InvocationHandler, StatementStatistics {

    /**
     * open state
     */
    protected static final int OPEN = 1;
    /**
     * executing state
     */
    protected static final int EXECUTING = 2;
    /**
     * executed state
     */
    protected static final int EXECUTED = 3;
    /**
     * closed state
     */
    protected static final int CLOSED = 4;
    /**
     * the logger object for tracing
     */
    protected final Logger mTrace = Logger.getLogger("jdbcspy.stmt");
    /**
     * the prepared statement
     */
    private final Statement uStatement;
    /**
     * the sql command
     */
    private final String mSql;
    /**
     * the generated result sets
     */
    private final Set<Object> mResultSets = new HashSet<>();

    /**
     * the open method
     */
    private final String mOpenMethod;
    private final Utils utils = new Utils();
    /**
     * result set item count
     */
    protected int mResultSetItemCount;
    /**
     * the sql command that is passed to Statement.executeQuery
     */
    private String mDirectSql;
    /**
     * is the statement closed
     */
    private int mState;
    /**
     * just the execute time
     */
    private long mExecTime;
    /**
     * total duration of iteration
     */
    private long mDuration;
    /**
     * total size of iteration
     */
    private long mSize;
    /**
     * the execution listener
     */
    private List<ExecutionListener> mExecListeners;
    /**
     * the execution failed listener
     */
    private List<ExecutionFailedListener> mExecFailedListeners;
    /**
     * get execute caller
     */
    private String mExecCaller;
    /**
     * the execution start time
     */
    private long mExecStartTime;

    /**
     * Constructor.
     *
     * @param theStmt the original statement
     * @param theSql  the sql string
     * @param method  the method
     */
    public AbstractStatementInvocationHandler(Statement theStmt,
                                              String theSql, String method) {
        uStatement = theStmt;
        mSql = theSql;
        mOpenMethod = method;
        mState = OPEN;
    }

    public void setExecutionListener(List<ExecutionListener> listener) {
        mExecListeners = listener;
    }

    public void setExecutionFailedListener(List<ExecutionFailedListener> listener) {
        mExecFailedListeners = listener;
    }

    /**
     * @see InvocationHandler
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try {
            if ("toString".equals(method.getName())) {
                return toString();
            }

            if (mTrace.isLoggable(Level.FINE)) {
                mTrace.fine("call method: " + uStatement.getClass() + "." + Utils.getMethodSignature(method, args));
            }

            if ("close".equals(method.getName())) {
                return handleClose(proxy, method, args, true);
            } else if ("checkClosed".equals(method.getName())) {
                handleCheckClosed(proxy);
                return null;
            } else if ("isClosed".equals(method.getName())) {
                return mState == CLOSED;
            } else if ("getExecutionStartTime".equals(method.getName())) {
                return getExecutionStartTime();
            } else if ("getExecutionTime".equals(method.getName())) {
                return getExecutionTime();
            } else if ("getExecuteCaller".equals(method.getName())) {
                return getExecuteCaller();
            } else if ("getSQL".equals(method.getName())) {
                return getSQL();
            } else if ("getDuration".equals(method.getName())) {
                return getDuration();
            } else if ("getSize".equals(method.getName())) {
                return getSize();
            } else if ("getItemCount".equals(method.getName())) {
                return getItemCount();
            } else if ("endTx".equals(method.getName())) {
                handleClose(proxy, null, null, false);
                return true;
            } else {
                handle(method, args);
            }

            boolean exe = method.getName().startsWith("execute");
            if (exe || method.getName().startsWith("getResult")) {
                if (exe && args != null && args.length == 1 && ((String) args[0]).startsWith("dbproxy ")) {
                    return handleDbProxy(method.getName(), ((String) args[0]).substring(8));
                } else {
                    return handleTimedMethod(proxy, method, args);
                }
            }

            // all other calls
            return method.invoke(uStatement, args);
        } catch (InvocationTargetException e) {

            String txt = "execution " + method.getName() + getArgs(args) + " failed for " + getSQL() +
                    " in method " + Utils.getExecClass(proxy);
            mTrace.log(Level.SEVERE, txt, e);

            ExecutionFailedEvent event = new ExecutionFailedEvent(toString(), e.getCause());

            for (ExecutionFailedListener listener : mExecFailedListeners) {
                listener.executionFailed(event);
            }

            throw e.getCause();
        } catch (ProxyException e) {

            ResourceEvent event = new ResourceEvent(e, e.getOpenMethod(), Utils.getExecClass(proxy));

            for (ExecutionListener listener : mExecListeners) {
                listener.resourceFailure(event);
            }
            if (ClientProperties.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
                throw e;
            }
            return null;
        } catch (Exception e) {
            mTrace.log(Level.SEVERE, "statement access failed for " + method.getName() + getArgs(args), e);

            ExecutionFailedEvent event = new ExecutionFailedEvent(toString(), e);

            for (ExecutionFailedListener listener : mExecFailedListeners) {
                listener.executionFailed(event);
            }

            throw e;
        }
    }

    private Object handleDbProxy(String method, String cmd) {
        mTrace.info("execute dbproxy command '" + cmd + "'");
        try {
            if (cmd.startsWith("get ")) {
                String key = cmd.substring(4);
                Object value = ClientProperties.getProperty(key);
                mTrace.info("Proxy property " + key + "=" + value);
                if (value == null) {
                    return Boolean.FALSE;
                }
            } else if (cmd.startsWith("set ")) {
                cmd = cmd.substring(4);
                int pos = cmd.indexOf(" ");
                String key = cmd.substring(0, pos);
                String value = cmd.substring(pos + 1);
                if (ClientProperties.getBooleanKeys().contains(key)) {
                    ClientProperties.setProperty(key, Boolean.valueOf(value));
                } else if (ClientProperties.getIntKeys().contains(key)) {
                    ClientProperties.setProperty(key, Integer.valueOf(value));
                } else if (ClientProperties.getListKeys().contains(key)) {
                    ClientProperties.setProperty(key, value);
                } else {
                    mTrace.info("key " + key + " does not exist.");
                    return Boolean.FALSE;
                }
            }

            if (method.equals("executeQuery")) {
                return null;
            } else {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            if (method.equals("executeQuery")) {
                return null;
            } else {
                return Boolean.FALSE;
            }
        }
    }

    protected void handle(Method method, Object[] args) throws SQLException {
    }

    /**
     * Handle the close method.
     *
     * @param proxy  the proxy
     * @param method the method
     * @param args   the arguments
     * @return the return value
     * @throws Throwable on error
     */
    protected Object handleClose(Object proxy, Method method, Object[] args, boolean checkClosed) throws Throwable {

        if (mState == CLOSED) {
            return false;
        }

        mState = CLOSED;
        Object ret = null;

        try {
            if (method != null) {
                ret = method.invoke(uStatement, args);
            }

            synchronized (mResultSets) {
                if (checkClosed) {
                    for (Object o : mResultSets) {
                        ProxyResultSet c = (ProxyResultSet) o;
                        c.checkClosed();
                    }
                }

                for (Object o : mResultSets) {
                    Statistics c = (Statistics) o;
                    mDuration += c.getDuration();
                    mSize += c.getSize();
                    mResultSetItemCount += c.getItemCount();
                }
            }

        } finally {
            synchronized (mResultSets) {
                mResultSets.clear();
            }

            CloseEvent event = new CloseEvent(this);

            for (ExecutionListener listener : mExecListeners) {
                listener.closeStatement(event);
            }
        }

        boolean displayStmt = mDuration >= ClientProperties.getInt(ClientProperties.DB_STMT_TOTAL_TIME_THRESHOLD)
                || mSize >= ClientProperties.getInt(ClientProperties.DB_STMT_TOTAL_SIZE_THRESHOLD);

        if (displayStmt) {
            mTrace.info(
                    (method == null ? "implicitly " : "")
                            + "closed statement " + this + " in "
                            + Utils.getExecClass(proxy));
        }

        return ret;
    }

    /**
     * Handle the execute method.
     *
     * @param proxy  the proxy
     * @param method the method
     * @param args   the arguments
     * @return Object the return object
     * @throws Throwable on error
     */
    private Object handleTimedMethod(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;
        long start;
        ExecutionEvent event = null;

        try {
            if (method.getName().startsWith("execute") && args != null && args.length > 0) {
                args[0] = (ClientProperties.getBoolean(ClientProperties.DB_REMOVE_HINTS) ? Utils.removeHints(args[0].toString())
                        : args[0].toString());
                mDirectSql = (String) args[0];
            }

            mExecCaller = Utils.getExecClass(proxy);
            mExecStartTime = System.currentTimeMillis();
            mState = EXECUTING;

            event = new ExecutionEvent(this);

            for (ExecutionListener listener : mExecListeners) {
                listener.startExecution(event);
            }
        } catch (RuntimeException e) {
            mTrace.log(Level.SEVERE, "failed ", e);
        }

        Object retObject;
        start = System.currentTimeMillis();
        long dur = 0;
        try {
            result = method.invoke(uStatement, args);

            dur = (System.currentTimeMillis() - start);
            mState = EXECUTED;
            retObject = result;

            if (result instanceof ResultSet) {
                ResultSet proxyRs = getResultSetProxy((ResultSet) result, getSQL(), Utils.getExecClass(proxy));

                if (proxyRs instanceof ProxyResultSet) {
                    synchronized (mResultSets) {
                        mResultSets.add(proxyRs);
                    }
                }

                retObject = proxyRs;
            } else if ("executeUpdate".equals(method.getName())) {
                Integer upd = (Integer) result;
                mResultSetItemCount += upd;
            }
        } finally {
            mState = EXECUTED;

            mDuration += dur;
            mExecTime += dur;

            for (ExecutionListener listener : mExecListeners) {
                listener.endExecution(event);
            }
        }

        boolean infoLevel = dur >= ClientProperties.getInt(ClientProperties.DB_STMT_EXECUTE_TIME_THRESHOLD);

        if (!infoLevel) {
            infoLevel = (Utils.isTrace(getSQL()) != null);
        }

        if (infoLevel) {
            mTrace.info(getPrintString(method.getName(), result, dur, mExecCaller));
        } else if (mTrace.isLoggable(Level.FINE)) {
            mTrace.fine(getPrintString(method.getName(), result, dur, mExecCaller));
        }

        return retObject;
    }

    /**
     * Get the print string.
     *
     * @param method     Method
     * @param result     Object
     * @param dur        long
     * @param methodCall the method call
     * @return String
     */
    private String getPrintString(String method, Object result, long dur, String methodCall) {

        StringBuilder txt = new StringBuilder(
                "finished " + method + " in " + Utils.getTimeString(dur) + " (" + getSQL() + ")");

        if (result instanceof Boolean) {
            txt.append(": ");
            txt.append(result);
        } else if (result instanceof Number) {
            txt.append(": #ret=");
            txt.append(result);
        } else if (result instanceof int[]) {
            int[] res = (int[]) result;
            txt.append(": [");
            if (res.length == 1) {
                txt.append(res[0]);
            } else if (res.length > 1) {
                boolean allSame = true;
                int r = res[0];
                for (int i = 1; i < res.length; i++) {
                    if (res[i] != r) {
                        allSame = false;
                        break;
                    }
                }

                if (!allSame) {
                    for (int i = 0; i < res.length; i++) {
                        if (i != 0) {
                            txt.append(", ");
                        }
                        txt.append(res[i]);
                    }
                } else {
                    txt.append(r).append(",... #=").append(res.length);
                }
            }
            txt.append("]");
        }

        txt.append(" in method ").append(methodCall);

        return txt.toString();
    }

    /**
     * Get a ResultSet proxy.
     *
     * @param rs         the original ResultSet
     * @param sql        the sql command
     * @param openMethod the open method
     * @return ResultSet
     */
    private ResultSet getResultSetProxy(ResultSet rs, String sql, String openMethod) {

        InvocationHandler handler = new ResultSetInvocationHandler(rs, sql, openMethod);

        return (ResultSet) Proxy.newProxyInstance(ProxyResultSet.class.getClassLoader(),
                new Class[]{ResultSet.class, ProxyResultSet.class, ResultSetStatistics.class}, handler);
    }

    /**
     * Handle the toString method.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "\"" + getSQL() + "\""
                + (mState != OPEN
                ? " (" + Utils.getTimeString(getExecutionTime())
                + (mState != EXECUTING ? " + "
                + Utils.getTimeString(getDuration() - getExecutionTime()) : "")
                + "; #=" + getItemCount() + (getSize() > 0
                ? "; size=" + Utils.getSizeString(getSize())
                : "")
                + ") "
                : " ")
                + (mState != OPEN
                ? (mState == EXECUTING ? "executing" : "executed") + " since "
                + utils.MILLI_TIME_FORMATTER.format(new Date(getExecutionStartTime())) + " in "
                + getExecuteCaller()
                : " not executed");
    }

    /**
     * Handle checkClosed method.
     *
     * @param proxy the proxy
     * @throws ProxyException if a resource was not closed or double closed
     */
    private void handleCheckClosed(Object proxy) throws ProxyException {

        if (!ClientProperties.getBoolean(ClientProperties.DB_IGNORE_NOT_CLOSED_OBJECTS) && mState != CLOSED) {

            String txt = "The statement \"" + getSQL() + "\" opened in " + mOpenMethod + " (connection closed in "
                    + Utils.getExecClass(proxy) + ") was not closed.";
            ResourceNotClosedException proxyExc = new ResourceNotClosedException(txt);

            proxyExc.setOpenMethod(mOpenMethod);
            throw proxyExc;
        }
    }

    /**
     * return the execute caller.
     *
     * @return String
     */
    @Override
    public String getExecuteCaller() {
        return mExecCaller;
    }

    /**
     * Get the execution start time.
     *
     * @return long
     */
    @Override
    public long getExecutionStartTime() {
        return mExecStartTime;
    }

    /**
     * Get the execution time.
     *
     * @return long
     */
    @Override
    public long getExecutionTime() {
        if (mState == EXECUTING) {
            return (System.currentTimeMillis() - mExecStartTime);
        } else {
            return mExecTime;
        }
    }

    /**
     * Get the SQL Code.
     *
     * @return the sql code
     */
    @Override
    public String getSQL() {
        String sql;
        if (mSql == null) {
            sql = (mDirectSql != null ? mDirectSql : "");
        } else {
            sql = mSql;
        }

        int maxLen = ClientProperties.getInt(ClientProperties.DB_DISPLAY_SQL_STRING_MAXLEN);
        if (maxLen > 0 && sql.length() > maxLen) {

            sql = sql.substring(0, maxLen) + "...";
        }
        return sql;
    }

    /**
     * Get the total duration.
     *
     * @return long
     */
    @Override
    public long getDuration() {
        if (mState == EXECUTING) {
            return (System.currentTimeMillis() - mExecStartTime);
        } else if (mState == EXECUTED) {
            long l = 0;
            synchronized (mResultSets) {
                for (Object mResultSet : mResultSets) {
                    Statistics c = (Statistics) mResultSet;
                    l += c.getDuration();
                }
            }
            return l + mDuration;
        }

        return mDuration;
    }

    /**
     * Get the size.
     *
     * @return long
     */
    @Override
    public long getSize() {
        if (mState != CLOSED) {
            long l = 0;
            synchronized (mResultSets) {
                for (Object mResultSet : mResultSets) {
                    Statistics c = (Statistics) mResultSet;
                    l += c.getSize();
                }
            }
            return l + mSize;
        }
        return mSize;
    }

    /**
     * Get the item count.
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        if (mState != CLOSED) {
            int l = 0;
            synchronized (mResultSets) {
                for (Object mResultSet : mResultSets) {
                    Statistics c = (Statistics) mResultSet;
                    l += c.getItemCount();
                }
            }
            return l;
        }
        return mResultSetItemCount;
    }

    protected int getState() {
        return mState;
    }

    /**
     * Get arguments.
     *
     * @param args Object[]
     * @return String
     */
    private String getArgs(Object[] args) {
        StringBuilder strb = new StringBuilder("(");
        for (int i = 0; args != null && i < args.length; i++) {
            if (i > 0) {
                strb.append(",");
            }
            strb.append(args[i]);
        }
        strb.append(")");
        return strb.toString();
    }

    public String dump() {
        return toString();
    }
}
