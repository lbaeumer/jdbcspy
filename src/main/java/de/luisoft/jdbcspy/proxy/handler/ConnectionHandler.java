package de.luisoft.jdbcspy.proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyConnectionMetaData;
import de.luisoft.jdbcspy.proxy.Checkable;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.StatementFactory;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import de.luisoft.jdbcspy.proxy.Statistics;
import de.luisoft.jdbcspy.proxy.exception.ProxyException;
import de.luisoft.jdbcspy.proxy.exception.ResourceAlreadyClosedException;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;
import de.luisoft.jdbcspy.proxy.listener.ResourceEvent;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The connection handler.
 */
public class ConnectionHandler implements InvocationHandler, ConnectionStatistics {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ConnectionHandler.class);

	/** max statement count */
	private static final int MAX_STMT_COUNT = 100;

	/** the prepared statement */
	private Connection mConn;

	/** all generated statements */
	private List<Checkable> mStatements = new LinkedList<>();

	/** result set item count */
	private List<Integer> mResultSetItemCount = new ArrayList<>();

	/** stmt count */
	private int mStmtCount;

	/** total duration of iteration */
	private long mDuration;

	/** total size of iteration */
	private long mSize;

	/** the properties */
	private ClientProperties mProps;

	/** the listener list */
	private List<ExecutionListener> mListener;

	/** the listener list */
	private List mFailedListener;

	/** the connection listener list */
	private List<ConnectionListener> mConnectionListener;

	/** the caller */
	private String mCaller;

	/** is closed */
	private boolean mIsClosed;

	/** is closed */
	private int mDeletedStmts;

	/** the connection factory */
	private ProxyConnectionMetaData mMetaData;

	private Utils utils = new Utils();

	/**
	 * The Constructor.
	 * 
	 * @param props
	 *            the client properties
	 * @param theConn
	 *            the original connection
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 */
	public ConnectionHandler(ClientProperties props, Connection theConn, List<ExecutionListener> listener,
			List failedListener, ProxyConnectionMetaData metaData) {
		mConn = theConn;
		mMetaData = metaData;
		mProps = props;
		mListener = listener;
		mFailedListener = failedListener;
		mConnectionListener = new LinkedList<>();
		mCaller = Utils.getExecClass(this);
	}

	/**
	 * Add a connection listener.
	 * 
	 * @param listener
	 *            ConnectionListener
	 */
	public void addConnectionListener(ConnectionListener listener) {
		mConnectionListener.add(listener);
	}

	/**
	 * Remove a connection listener.
	 * 
	 * @param listener
	 *            ConnectionListener
	 */
	public void removeConnectionListener(ConnectionListener listener) {
		mConnectionListener.remove(listener);
	}

	/**
	 * @see InvocationHandler
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		try {
			// implement the toString method
			if (method.getName().equals("toString")) {
				return toString();
			}

			if (mTrace.isDebugEnabled()) {
				mTrace.debug("call " + getMethodSignature(method, args));
			}

			if ("close".equals(method.getName())) {
				return handleClose(proxy, method, args);
			}

			else if ("getItemCount".equals(method.getName())) {
				return new Integer(getItemCount());
			} else if ("getDuration".equals(method.getName())) {
				return new Long(getDuration());
			} else if ("getSize".equals(method.getName())) {
				return new Long(getSize());
			} else if ("getCaller".equals(method.getName())) {
				return getCaller();
			} else if ("getStatements".equals(method.getName())) {
				return getStatements();
			} else if ("dump".equals(method.getName())) {
				return dump();
			} else if ("getProxyConnectionMetaData".equals(method.getName())) {
				return getProxyConnectionMetaData();
			}

			else if (method.getName().startsWith("prepare")) {
				return handlePrepare(proxy, method, args);
			} else if (method.getName().startsWith("create")) {
				return handleCreate(proxy, method, args);
			} else if (method.getName().equals("getUnderlyingConnection")) {
				return mConn;
			}

			return method.invoke(mConn, args);
		} catch (InvocationTargetException e) {
			mTrace.error(getMethodSignature(method, args) + " failed for " + toString(), e.getCause());
			throw e.getCause();
		} catch (ProxyException e) {
			ResourceEvent event = new ResourceEvent(e, e.getOpenMethod(), Utils.getExecClass(proxy));

			for (Iterator it = mListener.iterator(); it.hasNext();) {
				ExecutionListener listener = (ExecutionListener) it.next();
				listener.resourceFailure(event);
			}
			if (mProps.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
				throw e;
			}
			return null;
		} catch (Exception e) {
			mTrace.error("unknown error in " + method.getName() + " failed for " + toString(), e);
			throw e.getCause();
		}
	}

	/**
	 * Get the method signature.
	 * 
	 * @param method
	 *            Method
	 * @param args
	 *            Object[]
	 * @return String
	 */
	private String getMethodSignature(Method method, Object[] args) {
		StringBuffer strb = new StringBuffer(method.getName() + "(");
		for (int i = 0; args != null && i < args.length; i++) {
			if (i != 0) {
				strb.append(", ");
			}
			strb.append(args[i]);
		}
		strb.append(")");
		return strb.toString();
	}

	/**
	 * Dump the connection.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		synchronized (mStatements) {
			return "Connection[#stmt=" + getItemCount() + "; #rs=" + printList(mResultSetItemCount) + "; duration="
					+ utils.getTimeString(getDuration())
					+ (getSize() > 0 ? "; size=" + utils.getSizeString(getSize()) : "") + ", opened in " + getCaller()
					+ "]";
		}
	}

	/**
	 * Print the list.
	 * 
	 * @param l
	 *            List
	 * @return String
	 */
	private String printList(List<Integer> l) {
		if (l == null || l.isEmpty()) {
			return "-";
		}
		StringBuffer strb = new StringBuffer();
		int i = 0;
		int sum = 0;
		boolean shortCut = false;
		for (Integer cnt : l) {
			sum += cnt.intValue();

			if (i == 5 && l.size() > 10) {
				strb.append("+...");
				shortCut = true;
			}

			if (!shortCut) {
				if (i > 0) {
					strb.append("+");
				}
				strb.append(cnt);
			}
			i++;
		}

		if (shortCut) {
			strb.append("=" + sum);
		}

		return strb.toString();
	}

	/**
	 * Handle the prepare method.
	 * 
	 * @param proxy
	 *            Object
	 * @param method
	 *            Method
	 * @param args
	 *            Object[]
	 * @return Object
	 * @throws Throwable
	 *             on error
	 */
	private Object handlePrepare(Object proxy, Method method, Object[] args) throws Throwable {

		String sql = (mProps.getBoolean(ClientProperties.DB_REMOVE_HINTS) ? Utils.removeHints(args[0].toString())
				: args[0].toString());

		Object ob = method.invoke(mConn, args);
		if (ob instanceof Statement) {
			Statement proxyStmt = StatementFactory.getInstance().getStatement(mProps, (Statement) ob, mMetaData, sql,
					mListener, mFailedListener, Utils.getExecClass(proxy));

			if (proxyStmt instanceof Checkable) {
				addStatement((Checkable) proxyStmt);
			}

			return proxyStmt;
		} else {
			mTrace.error("method failed " + ob + ";" + ob.getClass().getName());
		}
		return ob;
	}

	/**
	 * Handle Create method
	 * 
	 * @param proxy
	 *            Object
	 * @param method
	 *            Method
	 * @param args
	 *            Object[]
	 * @throws Throwable
	 *             on error
	 * @return Object
	 */
	private Object handleCreate(Object proxy, Method method, Object[] args) throws Throwable {
		Object ob = method.invoke(mConn, args);
		if (ob instanceof Statement) {
			Statement proxyStmt = StatementFactory.getInstance().getStatement(mProps, (Statement) ob, mMetaData, null,
					mListener, mFailedListener, Utils.getExecClass(proxy));

			if (proxyStmt instanceof Checkable) {
				addStatement((Checkable) proxyStmt);
			}
			return proxyStmt;
		} else {
			mTrace.error("method failed " + ob + ";" + ob.getClass().getName());
		}
		return ob;
	}

	/**
	 * Add a statement.
	 * 
	 * @param stmt
	 *            Checkable
	 */
	private void addStatement(Checkable stmt) {
		synchronized (mStatements) {
			int x = mStatements.size() - MAX_STMT_COUNT;
			if (x > 10) {
				Iterator<Checkable> it = mStatements.iterator();
				while (it.hasNext() && x > 0) {
					Checkable c = it.next();
					if (c.isClosed()) {
						StatementStatistics stat = (StatementStatistics) c;
						mResultSetItemCount.add(new Integer(stat.getItemCount()));
						mDuration += stat.getDuration();
						mSize += stat.getSize();
						mStmtCount++;
						it.remove();
						x--;
						mDeletedStmts++;
						if (x <= 0) {
							break;
						}
					}
				}
			}

			mStatements.add(stmt);
		}
	}

	/**
	 * Handle the close method.
	 * 
	 * @param proxy
	 *            the proxy Object
	 * @param method
	 *            the method
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             on error
	 * @return the return object
	 */
	private Object handleClose(Object proxy, Method method, Object[] args) throws Throwable {

		if (mTrace.isDebugEnabled()) {
			mTrace.debug("close the statement " + proxy);
		}

		if (!mProps.getBoolean(ClientProperties.DB_IGNORE_DOUBLE_CLOSED_OBJECTS) && mIsClosed) {
			String txt = "The connection " + this + " was already closed in " + Utils.getExecClass(proxy) + ".";
			ResourceAlreadyClosedException proxyExc = new ResourceAlreadyClosedException(txt);

			proxyExc.setOpenMethod("?");
			throw proxyExc;
		}

		synchronized (mStatements) {
			for (Iterator it = mStatements.iterator(); it.hasNext();) {
				Statistics c = (Statistics) it.next();
				mResultSetItemCount.add(new Integer(c.getItemCount()));
			}
		}

		Object ret = null;
		ProxyException proxyExc = null;
		try {
			ConnectionEvent event = new ConnectionEvent(this);
			for (Iterator it = mConnectionListener.iterator(); it.hasNext();) {
				ConnectionListener listener = (ConnectionListener) it.next();
				listener.closeConnection(event);
			}

			ret = method.invoke(mConn, args);

			synchronized (mStatements) {
				for (Iterator it = mStatements.iterator(); it.hasNext();) {
					Statistics c = (Statistics) it.next();
					mDuration += c.getDuration();
					mSize += c.getSize();
				}

				for (Iterator it = mStatements.iterator(); it.hasNext();) {
					Checkable c = (Checkable) it.next();
					if (mTrace.isDebugEnabled()) {
						mTrace.debug("check closed: " + c);
					}
					try {
						c.checkClosed();
					} catch (ProxyException exc) {
						proxyExc = exc;
					}
				}
			}
		} finally {
			mIsClosed = true;
			synchronized (mStatements) {
				mStmtCount += mStatements.size();
				mStatements.clear();
			}
		}

		boolean displayTime = mDuration > mProps.getInt(ClientProperties.DB_CONN_TOTAL_TIME_THRESHOLD);
		boolean displaySize = mDuration > mProps.getInt(ClientProperties.DB_CONN_TOTAL_SIZE_THRESHOLD);

		if (displayTime || displaySize) {
			mTrace.info("closed " + this + " in " + Utils.getExecClass(proxy));
		}

		if (proxyExc != null && mProps.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
			throw proxyExc;
		}

		return ret;
	}

	/**
	 * Get the total duration.
	 * 
	 * @return long
	 */
	@Override
	public long getDuration() {
		long dur = 0;
		synchronized (mStatements) {
			for (Iterator it = mStatements.iterator(); it.hasNext();) {
				Statistics c = (Statistics) it.next();
				dur += c.getDuration();
			}
		}
		return mDuration + dur;
	}

	/**
	 * Get the size.
	 * 
	 * @return long
	 */
	@Override
	public long getSize() {
		long size = 0;
		synchronized (mStatements) {
			for (Iterator it = mStatements.iterator(); it.hasNext();) {
				Statistics c = (Statistics) it.next();
				size += c.getSize();
			}
		}
		return mSize + size;
	}

	/**
	 * Get the item count.
	 * 
	 * @return int
	 */
	@Override
	public int getItemCount() {
		return mStmtCount + mStatements.size();
	}

	/**
	 * Get the statements.
	 * 
	 * @return the statements.
	 */
	@Override
	public List getStatements() {
		return new ArrayList(mStatements);
	}

	/**
	 * The caller of the connection.
	 * 
	 * @return String
	 */
	@Override
	public String getCaller() {
		return mCaller;
	}

	/**
	 * Get the underlying connection.
	 * 
	 * @return Connection
	 */
	public Connection getUnderlyingConnection() {
		return mConn;
	}

	/**
	 * Dump the connection.
	 * 
	 * @return String
	 */
	public String dump() {
		StringBuffer strb = new StringBuffer();
		strb.append(toString() + " {" + "\n");

		int i = mDeletedStmts + 1;
		strb.append("1 .. " + mDeletedStmts + ": ...\n");
		synchronized (mStatements) {
			for (Iterator it = mStatements.iterator(); it.hasNext(); i++) {
				Statement s = (Statement) it.next();
				strb.append(i).append(": ");
				strb.append(s.toString());
				strb.append("\n");
			}
		}
		strb.append("}");

		return strb.toString();
	}

	/**
	 * @see de.luisoft.jdbcspy.proxy.ConnectionStatistics#getProxyConnectionMetaData
	 */
	@Override
	public ProxyConnectionMetaData getProxyConnectionMetaData() {
		return mMetaData;
	}
}
