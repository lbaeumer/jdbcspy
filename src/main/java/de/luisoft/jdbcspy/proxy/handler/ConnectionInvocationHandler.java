package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.Checkable;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.ProxyConnectionMetaData;
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The connection handler.
 */
public class ConnectionInvocationHandler implements InvocationHandler, ConnectionStatistics {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(ConnectionInvocationHandler.class.getName());

    /**
     * max statement count
     */
    private static final int MAX_STMT_COUNT = 100;

    /**
     * the underlying connection
     */
    private final Connection mConn;

    /**
     * all generated statements
     */
    private final List<Checkable> mStatements = new LinkedList<>();

    /**
     * result set item count
     */
    private final List<Integer> mResultSetItemCount = new ArrayList<>();

    /**
     * the connection listener list
     */
    private final List<ConnectionListener> mConnectionListener;
    /**
     * the caller
     */
    private final String mCaller;
    /**
     * the connection factory
     */
    private final ProxyConnectionMetaData mMetaData;
    private final Utils utils = new Utils();
    /**
     * stmt count
     */
    private int mStmtCount;
    /**
     * total duration of iteration
     */
    private long mDuration;
    /**
     * total size of iteration
     */
    private long mSize;
    /**
     * is closed
     */
    private boolean mIsClosed;
    /**
     * is closed
     */
    private int mDeletedStmts;

    /**
     * The Constructor.
     *
     * @param theConn the original connection
     */
    public ConnectionInvocationHandler(Connection theConn,
                                       ProxyConnectionMetaData metaData) {
        mConn = theConn;
        mMetaData = metaData;
        mConnectionListener = new LinkedList<>();
        mCaller = Utils.getExecClass(this);
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

            mTrace.info("call " + mConn.getClass() + "." + getMethodSignature(method, args));

            if ("close".equals(method.getName())) {
                return handleClose(proxy, method, args);
            } else if ("getItemCount".equals(method.getName())) {
                return getItemCount();
            } else if ("getDuration".equals(method.getName())) {
                return getDuration();
            } else if ("getSize".equals(method.getName())) {
                return getSize();
            } else if ("getCaller".equals(method.getName())) {
                return getCaller();
            } else if ("getStatements".equals(method.getName())) {
                return getStatements();
            } else if ("dump".equals(method.getName())) {
                return dump();
            } else if ("getProxyConnectionMetaData".equals(method.getName())) {
                return getProxyConnectionMetaData();
            } else if (method.getName().startsWith("prepare")) {
                return handlePrepare(proxy, method, args);
            } else if (method.getName().startsWith("create")) {
                return handleCreate(proxy, method, args);
            } else if (method.getName().equals("getUnderlyingConnection")) {
                return mConn;
            }

            return method.invoke(mConn, args);
        } catch (InvocationTargetException e) {
            mTrace.log(Level.SEVERE, getMethodSignature(method, args) + " failed for " + toString(), e.getCause());
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
            mTrace.log(Level.SEVERE, "unknown error in " + mConn.getClass()
                    + "." + method.getName() + " failed for " + toString(), e);
            throw e.getCause();
        }
    }

    /**
     * Get the method signature.
     *
     * @param method Method
     * @param args   Object[]
     * @return String
     */
    private String getMethodSignature(Method method, Object[] args) {
        StringBuilder strb = new StringBuilder(method.getName() + "(");
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
     * Print the list.
     *
     * @param l List
     * @return String
     */
    private String printList(List<Integer> l) {
        if (l == null || l.isEmpty()) {
            return "-";
        }
        StringBuilder strb = new StringBuilder();
        int i = 0;
        int sum = 0;
        boolean shortCut = false;
        for (Integer cnt : l) {
            sum += cnt;

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
            strb.append("=").append(sum);
        }

        return strb.toString();
    }

    /**
     * Handle the prepare method.
     *
     * @param proxy  Object
     * @param method Method
     * @param args   Object[]
     * @return Object
     * @throws Throwable on error
     */
    private Object handlePrepare(Object proxy, Method method, Object[] args) throws Throwable {

        String sql = (ClientProperties.getInstance().getBoolean(ClientProperties.DB_REMOVE_HINTS) ? Utils.removeHints(args[0].toString())
                : args[0].toString());

        Object ob = method.invoke(mConn, args);
        if (ob instanceof Statement) {
            Statement proxyStmt = StatementFactory.getInstance().getStatement(ClientProperties.getInstance(), (Statement) ob, mMetaData, sql,
                    Utils.getExecClass(proxy));

            if (proxyStmt instanceof Checkable) {
                addStatement((Checkable) proxyStmt);
            }

            return proxyStmt;
        } else {
            mTrace.severe("method failed " + ob + ";" + ob.getClass().getName());
        }
        return ob;
    }

    /**
     * Handle Create method
     *
     * @param proxy  Object
     * @param method Method
     * @param args   Object[]
     * @return Object
     * @throws Throwable on error
     */
    private Object handleCreate(Object proxy, Method method, Object[] args) throws Throwable {
        Object ob = method.invoke(mConn, args);
        if (ob instanceof Statement) {
            Statement proxyStmt = StatementFactory.getInstance().getStatement(ClientProperties.getInstance(), (Statement) ob, mMetaData, null,
                    Utils.getExecClass(proxy));

            if (proxyStmt instanceof Checkable) {
                addStatement((Checkable) proxyStmt);
            }
            return proxyStmt;
        } else {
            mTrace.severe("method failed " + ob + ";" + ob.getClass().getName());
        }
        return ob;
    }

    /**
     * Add a statement.
     *
     * @param stmt Checkable
     */
    private void addStatement(Checkable stmt) {
        synchronized (mStatements) {
            int x = mStatements.size() - MAX_STMT_COUNT;
            if (x > 10) {
                Iterator<Checkable> it = mStatements.iterator();
                while (it.hasNext()) {
                    Checkable c = it.next();
                    if (c.isClosed()) {
                        StatementStatistics stat = (StatementStatistics) c;
                        mResultSetItemCount.add(stat.getItemCount());
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
     * @param proxy  the proxy Object
     * @param method the method
     * @param args   the arguments
     * @return the return object
     * @throws Throwable on error
     */
    private Object handleClose(Object proxy, Method method, Object[] args) throws Throwable {

        if (!ClientProperties.getInstance().getBoolean(ClientProperties.DB_IGNORE_DOUBLE_CLOSED_OBJECTS) && mIsClosed) {
            String txt = "The connection " + this + " was already closed in " + Utils.getExecClass(proxy) + ".";
            ResourceAlreadyClosedException proxyExc = new ResourceAlreadyClosedException(txt);

            proxyExc.setOpenMethod("?");
            throw proxyExc;
        }

        synchronized (mStatements) {
            for (Checkable mStatement : mStatements) {
                Statistics c = (Statistics) mStatement;
                mResultSetItemCount.add(c.getItemCount());
            }
        }

        Object ret;
        try {
            ConnectionEvent event = new ConnectionEvent(this);
            for (ConnectionListener listener : mConnectionListener) {
                listener.closeConnection(event);
            }

            ret = method.invoke(mConn, args);

            synchronized (mStatements) {
                for (Checkable mStatement : mStatements) {
                    Statistics c = (Statistics) mStatement;
                    mDuration += c.getDuration();
                    mSize += c.getSize();
                }

                for (Checkable c : mStatements) {
                    c.checkClosed();
                }
            }

        } finally {
            mIsClosed = true;
            synchronized (mStatements) {
                mStmtCount += mStatements.size();
                mStatements.clear();
            }
        }

        boolean displayTime = mDuration > ClientProperties.getInstance().getInt(ClientProperties.DB_CONN_TOTAL_TIME_THRESHOLD);
        boolean displaySize = mDuration > ClientProperties.getInstance().getInt(ClientProperties.DB_CONN_TOTAL_SIZE_THRESHOLD);
        boolean displayAfterClose = ClientProperties.getInstance().getBoolean(ClientProperties.DB_DUMP_AFTER_CLOSE_CONNECTION);

        if (displayTime || displaySize || displayAfterClose) {
            mTrace.info("closed connection " + this + " in " + Utils.getExecClass(proxy));
        }

        return ret;
    }

    /**
     * Add a connection listener.
     *
     * @param listener ConnectionListener
     */
    public void addConnectionListener(ConnectionListener listener) {
        mConnectionListener.add(listener);
    }

    /**
     * Remove a connection listener.
     *
     * @param listener ConnectionListener
     */
    public void removeConnectionListener(ConnectionListener listener) {
        mConnectionListener.remove(listener);
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
            for (Checkable mStatement : mStatements) {
                Statistics c = (Statistics) mStatement;
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
            for (Checkable mStatement : mStatements) {
                Statistics c = (Statistics) mStatement;
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
    public List<Checkable> getStatements() {
        return new ArrayList<>(mStatements);
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
    public Object getUnderlyingConnection() {
        return mConn;
    }

    /**
     * Dump the connection.
     *
     * @return String
     */
    public String dump() {
        StringBuilder strb = new StringBuilder();
        strb.append(toString()).append(" {").append("\n");

        int i = mDeletedStmts + 1;
        strb.append("1 .. ").append(mDeletedStmts).append(": ...\n");
        synchronized (mStatements) {
            for (Checkable s : mStatements) {
                strb.append(i).append(": ");
                strb.append(s.toString());
                strb.append("\n");
                i++;
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

    /**
     * Dump the connection.
     *
     * @return String
     */
    @Override
    public String toString() {
        synchronized (mStatements) {
            return "Connection[#stmt=" + getItemCount() + "; #rs=" + printList(mResultSetItemCount) + "; duration="
                    + Utils.getTimeString(getDuration())
                    + (getSize() > 0 ? "; size=" + Utils.getSizeString(getSize()) : "") + ", opened in " + getCaller()
                    + "]";
        }
    }
}
