package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.ProxyStatement;
import de.luisoft.jdbcspy.proxy.StatementFactory;
import de.luisoft.jdbcspy.proxy.Statistics;
import de.luisoft.jdbcspy.proxy.exception.ProxyException;
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
    private static final Logger mTrace = Logger.getLogger("jdbcspy.connection");

    /**
     * max statement count
     */
    private static final int MAX_STMT_COUNT = 100;

    /**
     * the underlying connection
     */
    private final Connection uConnection;

    /**
     * all generated statements
     */
    private final List<ProxyStatement> mStatements = new LinkedList<>();
    /**
     * the connection listener list
     */
    private final List<ConnectionListener> mConnectionListener;
    /**
     * the caller
     */
    private final String mCaller;
    private int itemCount;
    /**
     * is closed
     */
    private int mDeletedStmts;

    /**
     * The Constructor.
     *
     * @param theConn the original connection
     */
    public ConnectionInvocationHandler(Connection theConn) {
        uConnection = theConn;
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

            if (mTrace.isLoggable(Level.FINE)) {
                mTrace.fine("call " + uConnection.getClass() + "." + Utils.getMethodSignature(method, args));
            }

            if ("close".equals(method.getName())) {
                return handleClose(proxy, method, args, true);
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
            } else if (method.getName().startsWith("prepare")) {
                return handlePrepare(proxy, method, args);
            } else if (method.getName().startsWith("create")) {
                return handleCreate(proxy, method, args);
            } else if (method.getName().equals("endTx")) {
                synchronized (mStatements) {
                    mTrace.fine("now closing " + mStatements.size() + " statements");
                    for (ProxyStatement s : mStatements) {
                        try {
                            mTrace.fine("endtx " + s);
                            s.endTx();
                        } catch (Exception e) {
                            mTrace.log(Level.WARNING, "fail " + e, e);
                        }
                    }
                }
                handleClose(proxy, null, null, false);
                return null;
            } else if (method.getName().equals("getUnderlyingConnection")) {
                return uConnection;
            }

            return method.invoke(uConnection, args);
        } catch (InvocationTargetException e) {
            mTrace.log(Level.SEVERE, Utils.getMethodSignature(method, args) + " failed for " + toString(), e.getCause());
            throw e.getCause();
        } catch (ProxyException e) {
            ResourceEvent event = new ResourceEvent(e, e.getOpenMethod(), Utils.getExecClass(proxy));

            for (ExecutionListener listener : ClientProperties.getListener()) {
                listener.resourceFailure(event);
            }
            if (ClientProperties.getBoolean(ClientProperties.DB_THROW_WARNINGS)) {
                throw e;
            }
            return null;
        } catch (Exception e) {
            mTrace.log(Level.SEVERE, "unknown error in " + uConnection.getClass()
                    + "." + method.getName() + " failed for " + toString(), e);
            throw new RuntimeException("failed " + e, e);
        }
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

        String sql = (ClientProperties.getBoolean(ClientProperties.DB_REMOVE_HINTS) ? Utils.removeHints(args[0].toString())
                : args[0].toString());

        Object ob = method.invoke(uConnection, args);
        if (ob instanceof Statement) {
            Statement proxyStmt = StatementFactory.getInstance().getStatement((Statement) ob, sql,
                    Utils.getExecClass(proxy));

            if (proxyStmt instanceof ProxyStatement) {
                addStatement((ProxyStatement) proxyStmt);
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
        Object ob = method.invoke(uConnection, args);
        if (ob instanceof Statement) {
            Statement proxyStmt = StatementFactory.getInstance().getStatement((Statement) ob, null,
                    Utils.getExecClass(proxy));

            if (proxyStmt instanceof ProxyStatement) {
                addStatement((ProxyStatement) proxyStmt);
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
    private void addStatement(ProxyStatement stmt) {
        synchronized (mStatements) {
            int x = mStatements.size() - MAX_STMT_COUNT;
            if (x > 10) {
                Iterator<ProxyStatement> it = mStatements.iterator();
                while (it.hasNext()) {
                    ProxyStatement c = it.next();
                    if (c.isClosed()) {
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
            itemCount = mStatements.size();
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
    private Object handleClose(Object proxy, Method method, Object[] args,
                               boolean checkClosed) throws Throwable {

        Object ret = null;
        try {
            ConnectionEvent event = new ConnectionEvent(this);
            for (ConnectionListener listener : mConnectionListener) {
                listener.closeConnection(event);
            }

            if (method != null) {
                ret = method.invoke(uConnection, args);
            }

            int duration = 0;
            int size = 0;
            synchronized (mStatements) {
                for (ProxyStatement mStatement : mStatements) {
                    Statistics c = (Statistics) mStatement;
                    duration += c.getDuration();
                    size += c.getSize();
                }

                if (checkClosed) {
                    for (ProxyStatement c : mStatements) {
                        c.checkClosed();
                    }
                }
            }

            // print out
            boolean displayTime = duration >= ClientProperties.getInt(ClientProperties.DB_CONN_TOTAL_TIME_THRESHOLD);
            boolean displaySize = size >= ClientProperties.getInt(ClientProperties.DB_CONN_TOTAL_SIZE_THRESHOLD);
            boolean verbose = ClientProperties.getBoolean(ClientProperties.VERBOSE);

            if (displayTime || displaySize) {
                Level l = (mStatements.size() > 0 ? Level.INFO : Level.FINE);
                if (!verbose) {
                    mTrace.log(l, (method == null ? "implicitly " : "")
                            + "closed connection " + this + " in "
                            + Utils.getExecClass(proxy));
                } else {
                    mTrace.log(l, (method == null ? "implicitly " : "")
                            + "closed connection\n" + this.dump());
                }
            }

        } finally {
            synchronized (mStatements) {
                itemCount = mStatements.size();
                mStatements.clear();
            }
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
            for (ProxyStatement mStatement : mStatements) {
                Statistics c = (Statistics) mStatement;
                dur += c.getDuration();
            }
        }
        return dur;
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
            for (ProxyStatement mStatement : mStatements) {
                Statistics c = (Statistics) mStatement;
                size += c.getSize();
            }
        }
        return size;
    }

    /**
     * Get the item count.
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return itemCount + mDeletedStmts;
    }

    /**
     * Get the statements.
     *
     * @return the statements.
     */
    @Override
    public List<ProxyStatement> getStatements() {
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
        return uConnection;
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
        if (mDeletedStmts > 0) {
            strb.append("1 .. ").append(mDeletedStmts).append(": ...\n");
        }
        synchronized (mStatements) {
            for (ProxyStatement s : mStatements) {
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
     * Dump the connection.
     *
     * @return String
     */
    @Override
    public String toString() {
        synchronized (mStatements) {
            return "Connection[#stmt=" + getItemCount() + "; duration="
                    + Utils.getTimeString(getDuration())
                    + (getSize() > 0 ? "; size=" + Utils.getSizeString(getSize()) : "") + ", opened in " + getCaller()
                    + "]";
        }
    }
}
