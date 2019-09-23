package de.luisoft.jdbcspy.proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyConnectionMetaData;
import de.luisoft.jdbcspy.proxy.Checkable;
import de.luisoft.jdbcspy.proxy.ResultSetStatistics;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.Statistics;
import de.luisoft.jdbcspy.proxy.exception.ProxyException;
import de.luisoft.jdbcspy.proxy.exception.ResourceAlreadyClosedException;
import de.luisoft.jdbcspy.proxy.exception.ResourceNotClosedException;
import de.luisoft.jdbcspy.proxy.listener.CloseEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedEvent;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;
import de.luisoft.jdbcspy.proxy.listener.ResourceEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The statement handler.
 */
public abstract class AbstractStatementHandler implements InvocationHandler, StatementStatistics {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(AbstractStatementHandler.class);

	/** open state */
	protected static final int OPEN = 1;
	/** executing state */
	protected static final int EXECUTING = 2;
	/** executed state */
	protected static final int EXECUTED = 3;
	/** closed state */
	protected static final int CLOSED = 4;

	/** the prepared statement */
	private Statement mStmt;

	/** the sql command */
	private String mSql;

	/** the sql command that is passed to Statement.executeQuery */
	private String mDirectSql;

	/** the generated result sets */
	private Set<Object> mResultSets = new HashSet<>();

	/** result set item count */
	protected int mResultSetItemCount;

	/** is the statement closed */
	private int mState;

	/** just the execute time */
	private long mExecTime;

	/** total duration of iteration */
	private long mDuration;

	/** total size of iteration */
	private long mSize;

	/** the properties */
	private ClientProperties mProps;

	/** the execution listener */
	private List<ExecutionListener> mExecListeners;

	/** the execution failed listener */
	private List<ExecutionFailedListener> mExecFailedListeners;

	/** the open method */
	private String mOpenMethod;

	/** get execute caller */
	private String mExecCaller;

	/** the execution start time */
	private long mExecStartTime;

	private ProxyConnectionMetaData mMetaData;

	private Utils utils = new Utils();

	/**
	 * Constructor.
	 * 
	 * @param props
	 *            the client properties
	 * @param theStmt
	 *            the original statement
	 * @param theSql
	 *            the sql string
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @param method
	 *            the method
	 */
	public AbstractStatementHandler(ClientProperties props, Statement theStmt, ProxyConnectionMetaData metaData,
			String theSql, String method) {
		mStmt = theStmt;
		mSql = theSql;
		mProps = props;
		mOpenMethod = method;
		mState = OPEN;
		mMetaData = metaData;
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

			if (mTrace.isDebugEnabled()) {
				mTrace.debug("call method: " + method.getName() + "(" + (args != null ? "#=" + args.length : "") + ")");
			}

			if ("close".equals(method.getName())) {
				return handleClose(proxy, method, args);
			} else if ("checkClosed".equals(method.getName())) {
				handleCheckClosed(proxy);
				return null;
			} else if ("isClosed".equals(method.getName())) {
				return Boolean.valueOf(mState == CLOSED);
			} else if ("getExecutionStartTime".equals(method.getName())) {
				return new Long(getExecutionStartTime());
			} else if ("getExecutionTime".equals(method.getName())) {
				return new Long(getExecutionTime());
			} else if ("getExecuteCaller".equals(method.getName())) {
				return getExecuteCaller();
			} else if ("getSQL".equals(method.getName())) {
				return getSQL();
			} else if ("getDuration".equals(method.getName())) {
				return new Long(getDuration());
			} else if ("getSize".equals(method.getName())) {
				return new Long(getSize());
			} else if ("getItemCount".equals(method.getName())) {
				return new Integer(getItemCount());
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
			return method.invoke(mStmt, args);
		} catch (InvocationTargetException e) {

			StringBuffer txt = new StringBuffer("execution " + method.getName() + getArgs(args) + " failed for ");
			txt.append(getSQL());
			txt.append(" in method " + Utils.getExecClass(proxy));
			mTrace.error(txt.toString(), e.getCause());

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
			if (mProps.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
				throw e;
			}
			return null;
		} catch (Exception e) {
			mTrace.error("statement access failed for " + method.getName() + getArgs(args), e);

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
				Object value = mMetaData.getProperty(key);
				mTrace.info("Proxy property " + key + "=" + value);
				if (value == null) {
					return Boolean.FALSE;
				}
			} else if (cmd.startsWith("set ")) {
				cmd = cmd.substring(4);
				int pos = cmd.indexOf(" ");
				String key = cmd.substring(0, pos);
				String value = cmd.substring(pos + 1);
				if (mMetaData.getBooleanKeys().contains(key)) {
					mMetaData.setProperty(key, Boolean.valueOf(value));
				} else if (mMetaData.getIntKeys().contains(key)) {
					mMetaData.setProperty(key, Integer.valueOf(value));
				} else if (mMetaData.getListKeys().contains(key)) {
					mMetaData.setProperty(key, value);
				} else {
					mTrace.info("key " + key + " does not exist.");
					return Boolean.FALSE;
				}
			} else if (cmd.equals("dump")) {
				mTrace.info("Proxy Statistics: " + mMetaData.dumpStatistics());
			} else if (cmd.equals("enable")) {
				mTrace.info("Proxy enabled.");
				mMetaData.enableProxy(true);
			} else if (cmd.equals("disable")) {
				mTrace.info("Proxy disabled.");
				mMetaData.enableProxy(false);
			} else if (cmd.equals("clear")) {
				mTrace.info("Proxy Statistics cleared.");
				mMetaData.clearStatistics();
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
	};

	/**
	 * Handle the close method.
	 * 
	 * @param proxy
	 *            the proxy
	 * @param method
	 *            the method
	 * @param args
	 *            the arguments
	 * @return the return value
	 * @throws Throwable
	 *             on error
	 */
	protected Object handleClose(Object proxy, Method method, Object[] args) throws Throwable {

		if (mTrace.isDebugEnabled()) {
			mTrace.debug("close the statement " + getSQL() + " with " + mResultSets.size() + " result sets");
		}

		if (!mProps.getBoolean(ClientProperties.DB_IGNORE_DOUBLE_CLOSED_OBJECTS) && mState == CLOSED) {
			String txt = "The Statement " + getSQL() + " opened in " + mOpenMethod + " was already closed in "
					+ Utils.getExecClass(proxy) + ".";
			ResourceAlreadyClosedException proxyExc = new ResourceAlreadyClosedException(txt);

			proxyExc.setOpenMethod(mOpenMethod);
			throw proxyExc;
		}

		mState = CLOSED;
		Object ret = null;

		boolean displayStmt = false;
		ProxyException proxyExc = null;

		try {
			ret = method.invoke(mStmt, args);
			synchronized (mResultSets) {
				for (Object o : mResultSets) {
					Checkable c = (Checkable) o;
					try {
						c.checkClosed();
					} catch (ProxyException exc) {
						proxyExc = exc;
					}
				}

				for (Object o : mResultSets) {
					Statistics c = (Statistics) o;
					mDuration += c.getDuration();
					mSize += c.getSize();
					mResultSetItemCount += c.getItemCount();
				}
			}

			displayStmt = mDuration > mProps.getInt(ClientProperties.DB_STMT_TOTAL_TIME_THRESHOLD)
					|| mSize > mProps.getInt(ClientProperties.DB_STMT_TOTAL_SIZE_THRESHOLD);
		} finally {
			synchronized (mResultSets) {
				mResultSets.clear();
			}

			CloseEvent event = new CloseEvent(this);

			for (ExecutionListener listener : mExecListeners) {
				listener.closeStatement(event);
			}
		}

		if (displayStmt) {
			mTrace.info("execution finished: " + toString());
		}

		if (proxyExc != null && mProps.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
			throw proxyExc;
		}

		return ret;
	}

	/**
	 * Handle the execute method.
	 * 
	 * @param proxy
	 *            the proxy
	 * @param method
	 *            the method
	 * @param args
	 *            the arguments
	 * @return Object the return object
	 * @throws Throwable
	 *             on error
	 */
	private Object handleTimedMethod(Object proxy, Method method, Object[] args) throws Throwable {

		Object result = null;
		long start = 0;
		ExecutionEvent event = null;

		try {
			if (method.getName().startsWith("execute") && args != null && args.length > 0) {
				if (mTrace.isDebugEnabled()) {
					mTrace.debug("execute(" + args[0] + ")");
				}
				args[0] = (mProps.getBoolean(ClientProperties.DB_REMOVE_HINTS) ? Utils.removeHints(args[0].toString())
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
			mTrace.error("failed ", e);
		}

		Object retObject = null;
		start = System.currentTimeMillis();
		long dur = 0;
		try {
			result = method.invoke(mStmt, args);

			dur = (System.currentTimeMillis() - start);
			mState = EXECUTED;
			retObject = result;

			if (result instanceof ResultSet) {
				if (mTrace.isDebugEnabled()) {
					mTrace.debug("create a proxy result set in method " + method.getName());
				}
				ResultSet proxyRs = getResultSetProxy((ResultSet) result, getSQL(), Utils.getExecClass(proxy));

				if (proxyRs instanceof Checkable) {
					synchronized (mResultSets) {
						mResultSets.add(proxyRs);
					}
				}

				retObject = proxyRs;
			} else if ("executeUpdate".equals(method.getName())) {
				Integer upd = (Integer) result;
				mResultSetItemCount += upd.intValue();
			}
		} finally {
			mState = EXECUTED;

			mDuration += dur;
			mExecTime += dur;

			for (ExecutionListener listener : mExecListeners) {
				listener.endExecution(event);
			}
		}

		boolean infoLevel = dur > mProps.getInt(ClientProperties.DB_STMT_EXECUTE_TIME_THRESHOLD);

		if (!infoLevel) {
			infoLevel = (Utils.isTrace(getSQL()) != null);
		}

		if (infoLevel) {
			mTrace.info(getPrintString(method.getName(), result, dur, mExecCaller));
		} else if (mTrace.isDebugEnabled()) {
			mTrace.debug(getPrintString(method.getName(), result, dur, mExecCaller));
		}

		return retObject;
	}

	/**
	 * Get the print string.
	 * 
	 * @param method
	 *            Method
	 * @param result
	 *            Object
	 * @param dur
	 *            long
	 * @param methodCall
	 *            the method call
	 * @return String
	 */
	private String getPrintString(String method, Object result, long dur, String methodCall) {

		StringBuffer txt = new StringBuffer(
				"finished " + method + " in " + utils.getTimeString(dur) + " (" + getSQL() + ")");

		if (result instanceof Boolean) {
			txt.append(": ");
			txt.append(result);
		} else if (result instanceof Number) {
			txt.append(": #ret=");
			txt.append(result);
		} else if (result instanceof int[]) {
			int res[] = (int[]) result;
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
					txt.append(r + ",... #=" + res.length);
				}
			}
			txt.append("]");
		}

		txt.append(" in method " + methodCall);

		return txt.toString();
	}

	/**
	 * Get a ResultSet proxy.
	 * 
	 * @param rs
	 *            the original ResultSet
	 * @param sql
	 *            the sql command
	 * @param openMethod
	 *            the open method
	 * @return ResultSet
	 */
	private ResultSet getResultSetProxy(ResultSet rs, String sql, String openMethod) {

		InvocationHandler handler = new ResultSetHandler(mProps, rs, sql, mExecListeners, mExecFailedListeners,
				openMethod);
		ResultSet proxyRs = (ResultSet) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
				new Class[] { ResultSet.class, Checkable.class, ResultSetStatistics.class }, handler);

		return proxyRs;
	}

	/**
	 * Handle the toString method.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "\"" + getSQL() + "\"" + (mState != OPEN ? " (" + utils.getTimeString(getExecutionTime())
				+ (mState != EXECUTING ? " + " + utils.getTimeString(getDuration() - getExecutionTime()) : "") + "; #="
				+ getItemCount() + (getSize() > 0 ? "; size=" + utils.getSizeString(getSize()) : "") + ") " : " ")
				+ (mState != OPEN ? (mState == EXECUTING ? "executing" : "executed") + " since "
						+ utils.MILLI_TIME_FORMATTER.format(new Date(getExecutionStartTime())) + " in "
						+ getExecuteCaller() : " not executed");
	}

	/**
	 * Handle checkClosed method.
	 * 
	 * @param proxy
	 *            the proxy
	 * @throws ProxyException
	 *             if a resource was not closed or double closed
	 */
	private void handleCheckClosed(Object proxy) throws ProxyException {

		if (!mProps.getBoolean(ClientProperties.DB_IGNORE_NOT_CLOSED_OBJECTS) && mState != CLOSED) {

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
		String sql = null;
		if (mSql == null) {
			sql = (mDirectSql != null ? mDirectSql : "");
		} else {
			sql = mSql;
		}

		int maxLen = mProps.getInt(ClientProperties.DB_DISPLAY_SQL_STRING_MAXLEN);
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
				for (Iterator it = mResultSets.iterator(); it.hasNext();) {
					Statistics c = (Statistics) it.next();
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
				for (Iterator it = mResultSets.iterator(); it.hasNext();) {
					Statistics c = (Statistics) it.next();
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
				for (Iterator it = mResultSets.iterator(); it.hasNext();) {
					Statistics c = (Statistics) it.next();
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
	 * @param args
	 *            Object[]
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
