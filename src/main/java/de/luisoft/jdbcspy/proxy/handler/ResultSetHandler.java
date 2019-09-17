package de.luisoft.jdbcspy.proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

/**
 * The result set handler.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ResultSetHandler.java 885 2007-03-18 20:46:41Z lui $
 */
public class ResultSetHandler
    implements InvocationHandler, ResultSetStatistics {

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(ResultSetHandler.class);

    /** the original result set */
    private ResultSet mRs;

    /** is closed */
    private boolean mIsClosed;

    /** the sql statement */
    private String mSql;

    /** total duration of iteration */
    private long mDuration;

    /** total length */
    private long mSize;

    /** the item count */
    private int mItemCount;

    /** the properties */
    private ClientProperties mProps;

    /** ignore the size evaluation */
    private final boolean mPropEnableSizeEvaluation;

    /** the execution listener */
    private List mExecListeners;

    /** the execution failed listener */
    private List mExecFailedListeners;

    /** the open method */
    private String mOpenMethod;

    /**
     * Constructor.
     * @param props the client properties
     * @param rs ResultSet
     * @param sql String
     * @param listener the execution listener
     * @param failedListener the failed listener
     * @param openMethod the method
     */
    public ResultSetHandler(ClientProperties props,
    		ResultSet rs,
    		String sql,
            List listener,
            List failedListener,
            String openMethod) {

        mRs = rs;
        mSql = sql;
        mProps = props;
        mExecListeners = listener;
        mExecFailedListeners = failedListener;
        mPropEnableSizeEvaluation = mProps.getBoolean(
            ClientProperties.DB_ENABLE_SIZE_EVALUATION);
        mOpenMethod = openMethod;
    }

    /**
     * @see InvocationHandler
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable {

        String methodName = method.getName();

        if (mTrace.isDebugEnabled()) {
            mTrace.debug("call method: " + methodName);
        }

        try {
            if (methodName.startsWith("get")
                && args != null && args.length > 0) {
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
            }
            else if ("isClosed".equals(method.getName())) {
                return Boolean.valueOf(mIsClosed);
            }

            // ResultSetStatitics Interface implementation
            else if ("getItemCount".equals(methodName)) {
                return new Integer(mItemCount);
            }
            else if ("getDuration".equals(methodName)) {
                return new Long(mDuration);
            }
            else if ("getSize".equals(methodName)) {
                return new Long(mSize);
            }

            // remaining calls
            return method.invoke(mRs, args);
        }
        catch (InvocationTargetException e) {
            mTrace.error("result set access failed for " + mSql
                         + " in " + methodName + getArgs(args),
                         e.getCause());

            ExecutionFailedEvent event = new ExecutionFailedEvent(toString(),
                e.getCause());

            for (Iterator it = mExecFailedListeners.iterator();
            	it.hasNext(); ) {
                ExecutionFailedListener listener =
                	(ExecutionFailedListener) it.next();
                listener.executionFailed(event);
            }

            throw e.getCause();
        }
        catch (ProxyException e) {
            ResourceEvent event = new ResourceEvent(e, e.getOpenMethod(),
                Utils.getExecClass(proxy));

            for (Iterator it = mExecListeners.iterator(); it.hasNext(); ) {
                ExecutionListener listener = (ExecutionListener) it.next();
                listener.resourceFailure(event);
            }

            if (mProps.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
                throw e;
            }

            return null;
        }
        catch (Exception e) {
            mTrace.error("result set access failed for " + mSql
                         + " in " + methodName + getArgs(args), e);

            ExecutionFailedEvent event = new ExecutionFailedEvent(
            		toString(), e);

            for (Iterator it = mExecFailedListeners.iterator();
            	it.hasNext(); ) {
                ExecutionFailedListener listener =
                    (ExecutionFailedListener) it.next();
                listener.executionFailed(event);
            }

            throw e;
        }
    }

    /**
     * Handle the close method.
     * @param proxy the proxy
     * @throws ProxyException if a resource was not closed or double closed
     */
    private void handleClose(Object proxy) throws ProxyException {
        if (mTrace.isDebugEnabled()) {
            mTrace.debug("close the resultset " + mSql);
        }

        if (!mProps.getBoolean(
        		ClientProperties.DB_IGNORE_DOUBLE_CLOSED_OBJECTS)
            && mIsClosed) {

            String txt = "The ResultSet opened in " + mOpenMethod
                         + " was already closed in "
                         + Utils.getExecClass(proxy) + ".";

            ResourceAlreadyClosedException proxyExc = new
                ResourceAlreadyClosedException(txt);
            proxyExc.setOpenMethod(mOpenMethod);
            throw proxyExc;
        }

        // may be null if next hasn't been called
        boolean displayTime = mDuration > mProps.getInt(
            ClientProperties.DB_RESULTSET_TOTAL_TIME_THRESHOLD);
        boolean displaySize = mSize > mProps.getInt(
            ClientProperties.DB_RESULTSET_TOTAL_SIZE_THRESHOLD);

        if (displayTime || displaySize) {
            mTrace.info("iteration of resultset closed in "
                        + Utils.getExecClass(proxy)
                        + " took "
                        + Utils.getTimeString(mDuration) + ". "
                        + (mSize > 0
                        		? "(" + Utils.getSizeString(mSize) + ")"
                        		: ""));
        }

        mIsClosed = true;
    }

    /**
     * Handle check closed.
     * @param proxy the proxy
     * @throws ProxyException if a resource was not closed or double closed
     */
    private void handleCheckClosed(Object proxy)
        throws ProxyException {
        if (!mIsClosed
            && !mProps.getBoolean(
            		ClientProperties.DB_IGNORE_NOT_CLOSED_OBJECTS)) {

            String txt = "The ResultSet opened in " + mOpenMethod
                         + " was not closed in "
                         + Utils.getExecClass(proxy) + ".";
            if (mTrace.isDebugEnabled()) {
                mTrace.debug(txt);
            }

            boolean displayMsg = mProps.getBoolean(ClientProperties.
                DB_DISPLAY_ENTITY_BEANS);

            boolean cmp = false;
            if (!displayMsg) {
                StackTraceElement el[] = new Exception().getStackTrace();
                for (int i = 0; i < el.length; i++) {
                    if (el[i].getClassName().startsWith(
                        "org.jboss.ejb.EntityContainer")) {
                        cmp = true;
                        break;
                    }
                }
            }

            if (displayMsg || !cmp) {

                ResourceNotClosedException proxyExc
                    = new ResourceNotClosedException(txt);
                proxyExc.setOpenMethod(mOpenMethod);
                throw proxyExc;
            }
        }
    }

    /**
     * Handle the next method.
     * @param method the method
     * @param args the arguments
     * @return Object the result object
     * @throws Throwable on error
     */
    private Object handleNext(Method method, Object[] args)
        throws Throwable {

        long startTime = System.currentTimeMillis();
        try {

            Boolean b = (Boolean) method.invoke(mRs, args);
            if (b.booleanValue()) {
                mItemCount++;
            }
            return b;
        }
        finally {
            long dur = (System.currentTimeMillis() - startTime);

            mDuration += dur;
            if (dur > mProps.getInt(
                ClientProperties.DB_RESULTSET_NEXT_TIME_THRESHOLD)) {
                String txt = "finished next in " + dur
                             + "ms. (loop " + mItemCount + ")";

                mTrace.info(txt);
            }
        }
    }

    /**
     * Handle the get method.
     * @param method the method
     * @param args the arguments
     * @return Object the result object
     * @throws Throwable on error
     */
    private Object handleGet(Method method, Object[] args)
        throws Throwable {

        Object ret = null;
        try {
            ret = method.invoke(mRs, args);
            if (mPropEnableSizeEvaluation) {
                if (ret instanceof String) {
                    mSize += 2 * ((String) ret).length();
                }
                else if (ret instanceof Integer || ret instanceof Float) {
                    mSize += 4;
                }
                else if (ret instanceof Boolean || ret instanceof Byte) {
                    mSize += 1;
                }
                else if (ret instanceof Long
                         || ret instanceof Date
                         || ret instanceof Time
                         || ret instanceof Double
                         || ret instanceof Timestamp) {
                    mSize += 8;
                }
                else if (ret instanceof Short) {
                    mSize += 2;
                }
                else if (ret instanceof byte[]) {
                    mSize += ((byte[]) ret).length;
                }
                else if (ret != null) {
                    mTrace.warn("unknown return type: " + ret.getClass(),
                        new RuntimeException());
                }
            }

            return ret;
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get the total duration.
     * @return long
     */
    public long getDuration() {
        return mDuration;
    }

    /**
     * Get the size.
     * @return long
     */
    public long getSize() {
        return mSize;
    }

    /**
     * Get the item count.
     * @return int
     */
    public int getItemCount() {
        return mItemCount;
    }

    /**
     * Get arguments.
     * @param args Object[]
     * @return String
     */
    private String getArgs(Object[] args) {
        StringBuffer strb = new StringBuffer("(");
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
