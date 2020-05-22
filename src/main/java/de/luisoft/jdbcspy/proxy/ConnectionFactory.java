package de.luisoft.jdbcspy.proxy;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.handler.ConnectionInvocationHandler;
import de.luisoft.jdbcspy.proxy.handler.XAConnectionInvocationHandler;
import de.luisoft.jdbcspy.proxy.handler.XAResourceInvocationHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Title: ConnectionFactory
 */
public class ConnectionFactory {

    /**
     * A Logger.
     */
    private static final Logger mTrace = Logger.getLogger("jdbcspy.connectionfactory");

    private static Boolean dumpAfterShutdownThread = false;
    private static Boolean dumpIntervalThread = false;
    /**
     * shall the proxy be enabled
     */
    private final boolean mInitiallyEnableProxy;
    /**
     * shall the proxy be enabled
     */
    private boolean mEnableProxy;

    /**
     * Constructor.
     */
    public ConnectionFactory() {

        mInitiallyEnableProxy = ClientProperties.isInitiallyEnabled();

        if (mInitiallyEnableProxy) {
            mEnableProxy = true;
        } else {
            mTrace.info("Disable the ProxyConnectionFactory initially. " + "Using standard connection.");
        }

        if (ClientProperties.getBoolean(ClientProperties.DB_DUMP_AFTER_SHUTDOWN)) {
            synchronized (dumpAfterShutdownThread) {
                if (!dumpAfterShutdownThread) {
                    dumpAfterShutdownThread = true;

                    Thread t = new Thread(() -> System.out.println(dumpStatistics()));
                    t.setDaemon(true);
                    Runtime.getRuntime().addShutdownHook(t);
                }
            }
        }

        if (ClientProperties.getInt(ClientProperties.DB_DUMP_INTERVAL) > 0) {
            synchronized (dumpIntervalThread) {
                if (!dumpIntervalThread) {
                    dumpIntervalThread = true;

                    Thread t = new Thread(() -> {
                        try {
                            while (true) {
                                Thread.sleep(
                                        1000 * ClientProperties.getInt(ClientProperties.DB_DUMP_INTERVAL));
                                System.out.println(dumpStatistics());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    t.setDaemon(true);
                    t.start();
                }
            }
        }
    }

    /**
     * Dump the statistics.
     *
     * @return String
     */
    public static final String dumpStatistics() {
        StringBuilder strb = new StringBuilder();
        for (ExecutionListener obj : ClientProperties.getListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        for (ConnectionListener obj : ClientProperties.getConnectionListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        for (ExecutionFailedListener obj : ClientProperties.getFailedListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        return strb.toString();
    }

    /**
     * Get the connection.
     *
     * @param conn the original connection
     * @return a proxy connection
     */
    public final Connection getProxyConnection(Connection conn) {

        if (!mEnableProxy) {
            // get standard connection
            return conn;
        }

        ConnectionInvocationHandler connHandler =
                new ConnectionInvocationHandler(conn);

        for (ConnectionListener listener : ClientProperties.getConnectionListener()) {
            connHandler.addConnectionListener(listener);
        }

        Connection c = (ProxyConnection) Proxy.newProxyInstance(ProxyConnection.class.getClassLoader(),
                new Class[]{ProxyConnection.class}, connHandler);

        ConnectionEvent event = new ConnectionEvent(connHandler);
        for (ConnectionListener listener : ClientProperties.getConnectionListener()) {
            listener.openConnection(event);
        }

        return c;
    }

    /**
     * Get the connection.
     *
     * @param conn the original connection
     * @return a proxy connection
     */
    public final XAConnection getProxyXAConnection(XAConnection conn) {

        if (!mEnableProxy) {
            // get standard connection
            return conn;
        }

        XAConnectionInvocationHandler connHandler =
                new XAConnectionInvocationHandler(conn, this);

        return (XAConnection) Proxy.newProxyInstance(ProxyConnection.class.getClassLoader(),
                new Class[]{ProxyConnection.class}, connHandler);
    }

    /**
     * Get the connection.
     *
     * @param xaResource the original connection
     * @return a proxy connection
     */
    public final XAResource getProxyXAResource(XAResource xaResource,
                                               XAConnectionInvocationHandler handler) {

        if (!mEnableProxy) {
            // get standard connection
            return xaResource;
        }

        XAResourceInvocationHandler xaHandler =
                new XAResourceInvocationHandler(xaResource, handler);

        return (XAResource) Proxy.newProxyInstance(XAResource.class.getClassLoader(),
                new Class[]{XAResource.class}, xaHandler);
    }
}
