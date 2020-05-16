package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ConnectionFactory;
import de.luisoft.jdbcspy.ProxyConnection;
import de.luisoft.jdbcspy.proxy.util.Utils;

import javax.sql.XAConnection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The connection handler.
 */
public class XAConnectionHandler implements InvocationHandler {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(XAConnectionHandler.class.getName());

    /**
     * the prepared statement
     */
    private final XAConnection mConn;

    private ClientProperties mProps;
    private String mCaller;
    private ConnectionFactory connFac;

    /**
     * The Constructor.
     *
     * @param props   the client properties
     * @param theConn the original connection
     */
    public XAConnectionHandler(ClientProperties props,
                               XAConnection theConn,
                               ConnectionFactory connFac) {
        mConn = theConn;
        mProps = props;
        this.connFac = connFac;
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

            if ("getConnection".equals(method.getName())) {
                Connection c = (Connection) method.invoke(mConn, args);
                ProxyConnection pc = (ProxyConnection) connFac.getProxyConnection(c);
                return pc;
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
}
