package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.ResultSetStatistics;
import de.luisoft.jdbcspy.proxy.exception.ProxyException;
import de.luisoft.jdbcspy.proxy.exception.ResourceAlreadyClosedException;
import de.luisoft.jdbcspy.proxy.exception.ResourceNotClosedException;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;
import de.luisoft.jdbcspy.proxy.listener.ResourceEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The result set handler.
 */
public class ResultSetInvocationHandler implements InvocationHandler, ResultSetStatistics {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger("jdbcspy.rs");
    /**
     * the original result set
     */
    private final ResultSet uResultSet;
    /**
     * the sql statement
     */
    private final String mSql;

    /**
     * the open method
     */
    private final String mOpenMethod;

    /**
     * is closed
     */
    private boolean mIsClosed;
    /**
     * total duration of iteration
     */
    private long mDuration;
    /**
     * total length
     */
    private long mSize;
    /**
     * the item count
     */
    private int mItemCount;

    /**
     * Constructor.
     *
     * @param rs         ResultSet
     * @param sql        String
     * @param openMethod the method
     */
    public ResultSetInvocationHandler(ResultSet rs, String sql, String openMethod) {

        uResultSet = rs;
        mSql = sql;
        mOpenMethod = openMethod;
    }

    /**
     * @see InvocationHandler
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();

        try {
            if (methodName.startsWith("get") && args != null && args.length > 0) {
                return handleGet(method, args);
            }

            if ("next".equals(methodName)) {
                return handleNext(method, args);
            }
            if ("close".equals(methodName)) {
                handleClose(proxy);
            }
            // Checkable Interface implementation
            else if ("checkClosed".equals(methodName)) {
                handleCheckClosed(proxy);
                return null;
            } else if ("isClosed".equals(method.getName())) {
                return mIsClosed;
            }

            // ResultSetStatitics Interface implementation
            else if ("getItemCount".equals(methodName)) {
                return mItemCount;
            } else if ("getDuration".equals(methodName)) {
                return mDuration;
            } else if ("getSize".equals(methodName)) {
                return mSize;
            }

            // remaining calls
            return method.invoke(uResultSet, args);
        } catch (InvocationTargetException e) {
            mTrace.log(Level.SEVERE, "result set access failed for " + mSql + " in " + methodName + getArgs(args), e.getCause());

            ExecutionFailedEvent event = new ExecutionFailedEvent(toString(), e.getCause());

            for (ExecutionFailedListener listener : ClientProperties.getInstance().getFailedListener()) {
                listener.executionFailed(event);
            }

            throw e.getCause();
        } catch (ProxyException e) {
            ResourceEvent event = new ResourceEvent(e, e.getOpenMethod(), Utils.getExecClass(proxy));

            for (ExecutionListener listener : ClientProperties.getInstance().getListener()) {
                listener.resourceFailure(event);
            }

            if (ClientProperties.getInstance().getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
                throw e;
            }

            return null;
        } catch (Exception e) {
            mTrace.log(Level.SEVERE, "result set access failed for " + mSql + " in " + methodName + getArgs(args), e);

            ExecutionFailedEvent event = new ExecutionFailedEvent(toString(), e);

            for (ExecutionFailedListener listener : ClientProperties.getInstance().getFailedListener()) {
                listener.executionFailed(event);
            }

            throw e;
        }
    }

    /**
     * Handle the close method.
     *
     * @param proxy the proxy
     * @throws ProxyException if a resource was not closed or double closed
     */
    private void handleClose(Object proxy) throws ProxyException {
        if (!ClientProperties.getInstance().getBoolean(ClientProperties.DB_IGNORE_DOUBLE_CLOSED_OBJECTS) && mIsClosed) {

            String txt = "The ResultSet opened in " + mOpenMethod + " was already closed in "
                    + Utils.getExecClass(proxy) + ".";

            ResourceAlreadyClosedException proxyExc = new ResourceAlreadyClosedException(txt);
            proxyExc.setOpenMethod(mOpenMethod);
            throw proxyExc;
        }

        // may be null if next hasn't been called
        boolean displayTime = mDuration > ClientProperties.getInstance().getInt(ClientProperties.DB_RESULTSET_TOTAL_TIME_THRESHOLD);
        boolean displaySize = mSize > ClientProperties.getInstance().getInt(ClientProperties.DB_RESULTSET_TOTAL_SIZE_THRESHOLD);

        if (displayTime || displaySize) {
            mTrace.info("iteration of resultset closed in " + Utils.getExecClass(proxy) + " took "
                    + Utils.getTimeString(mDuration) + ". "
                    + (mSize > 0 ? "(" + Utils.getSizeString(mSize) + ")" : ""));
        }

        mIsClosed = true;
    }

    /**
     * Handle check closed.
     *
     * @param proxy the proxy
     * @throws ProxyException if a resource was not closed or double closed
     */
    private void handleCheckClosed(Object proxy) throws ProxyException {
        if (!mIsClosed && !ClientProperties.getInstance().getBoolean(ClientProperties.DB_IGNORE_NOT_CLOSED_OBJECTS)) {

            String txt = "The ResultSet opened in " + mOpenMethod + " was not closed in " + Utils.getExecClass(proxy)
                    + ".";

            boolean displayMsg = ClientProperties.getInstance().getBoolean(ClientProperties.DB_DISPLAY_ENTITY_BEANS);

            boolean cmp = false;
            if (!displayMsg) {
                StackTraceElement[] el = new Exception().getStackTrace();
                for (StackTraceElement stackTraceElement : el) {
                    if (stackTraceElement.getClassName().startsWith("org.jboss.ejb.EntityContainer")) {
                        cmp = true;
                        break;
                    }
                }
            }

            if (displayMsg || !cmp) {

                ResourceNotClosedException proxyExc = new ResourceNotClosedException(txt);
                proxyExc.setOpenMethod(mOpenMethod);
                throw proxyExc;
            }
        }
    }

    /**
     * Handle the next method.
     *
     * @param method the method
     * @param args   the arguments
     * @return Object the result object
     * @throws Throwable on error
     */
    private Object handleNext(Method method, Object[] args) throws Throwable {

        long startTime = System.currentTimeMillis();
        try {

            Boolean b = (Boolean) method.invoke(uResultSet, args);
            if (b) {
                mItemCount++;
            }
            return b;
        } finally {
            long dur = (System.currentTimeMillis() - startTime);

            mDuration += dur;
            if (dur > ClientProperties.getInstance().getInt(ClientProperties.DB_RESULTSET_NEXT_TIME_THRESHOLD)) {
                String txt = "finished next in " + dur + "ms. (loop " + mItemCount + ")";

                mTrace.info(txt);
            }
        }
    }

    /**
     * Handle the get method.
     *
     * @param method the method
     * @param args   the arguments
     * @return Object the result object
     * @throws Throwable on error
     */
    private Object handleGet(Method method, Object[] args) throws Throwable {

        Object ret;
        try {
            ret = method.invoke(uResultSet, args);
            if (ClientProperties.getInstance().getBoolean(ClientProperties.DB_ENABLE_SIZE_EVALUATION)) {
                if (ret instanceof String) {
                    mSize += 2 * ((String) ret).length();
                } else if (ret instanceof Integer || ret instanceof Float) {
                    mSize += 4;
                } else if (ret instanceof Boolean || ret instanceof Byte) {
                    mSize += 1;
                } else if (ret instanceof Long || ret instanceof Date || ret instanceof Time || ret instanceof Double
                        || ret instanceof Timestamp) {
                    mSize += 8;
                } else if (ret instanceof Short) {
                    mSize += 2;
                } else if (ret instanceof byte[]) {
                    mSize += ((byte[]) ret).length;
                } else if (ret != null) {
                    mTrace.log(Level.SEVERE, "unknown return type: " + ret.getClass(), new RuntimeException());
                }
            }

            return ret;
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    /**
     * Get the total duration.
     *
     * @return long
     */
    @Override
    public long getDuration() {
        return mDuration;
    }

    /**
     * Get the size.
     *
     * @return long
     */
    @Override
    public long getSize() {
        return mSize;
    }

    /**
     * Get the item count.
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return mItemCount;
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
}
