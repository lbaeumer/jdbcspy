package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.proxy.Checkable;
import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.ProxyConnection;
import de.luisoft.jdbcspy.proxy.ProxyConnectionMetaData;

import javax.sql.XAConnection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The connection handler.
 */
public class XAConnectionInvocationHandler implements InvocationHandler, ConnectionStatistics {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(XAConnectionInvocationHandler.class.getName());

    /**
     * the underlying connection
     */
    private final XAConnection mConn;

    private List<ProxyConnection> mConnections = new ArrayList<>();

    private ConnectionFactory connFac;

    /**
     * The Constructor.
     *
     * @param theConn the original connection
     */
    public XAConnectionInvocationHandler(XAConnection theConn,
                                         ConnectionFactory connFac) {
        mConn = theConn;
        this.connFac = connFac;
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
            } else if ("getConnection".equals(method.getName())) {
                Connection c = (Connection) method.invoke(mConn, args);
                ProxyConnection pc = (ProxyConnection) connFac.getProxyConnection(c);

                mConnections.add(pc);

                return pc;
            } else if ("dump".equals(method.getName())) {
                return dump();
            }

            return method.invoke(mConn, args);
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
     * Handle the close method.
     *
     * @param proxy  the proxy Object
     * @param method the method
     * @param args   the arguments
     * @return the return object
     * @throws Throwable on error
     */
    private Object handleClose(Object proxy, Method method, Object[] args) throws Throwable {
        mConn.close();
        return null;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public List<Checkable> getStatements() {
        return null;
    }

    @Override
    public String getCaller() {
        return null;
    }

    @Override
    public ProxyConnectionMetaData getProxyConnectionMetaData() {
        return null;
    }

    public String dump() {
        return toString();
    }

    /**
     * Dump the connection.
     *
     * @return String
     */
    @Override
    public String toString() {
        synchronized (mConnections) {
            StringBuilder strb = new StringBuilder();
            strb.append("XAConnection[\n");
            int i = 0;
            for (ProxyConnection c : mConnections) {
                if (i++ > 0) {
                    strb.append(",\n");
                }
                strb.append(c.toString());
            }
            strb.append("\n]");

            return strb.toString();
        }
    }
}
