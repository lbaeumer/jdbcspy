package de.luisoft.jdbcspy;

import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.handler.XAConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;

import javax.sql.XAConnection;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Title: ConnectionFactory
 */
public class ConnectionFactory implements ProxyConnectionMetaData {

    /**
     * A Logger.
     */
    private static final Logger mTrace = Logger.getLogger(ConnectionFactory.class.getName());

    /**
     * shall the proxy be enabled
     */
    private boolean mInitiallyEnableProxy;
    /**
     * shall the proxy be enabled
     */
    private boolean mEnableProxy;

    /**
     * Constructor.
     */
    ConnectionFactory() {

        ClientProperties props = ClientProperties.getInstance();
        mInitiallyEnableProxy = props.isInitiallyEnabled();

        if (mInitiallyEnableProxy) {
            mEnableProxy = true;
            mTrace.info("settings=" + props);
        } else {
            mTrace.info("Disable the ProxyConnectionFactory initially. " + "Using standard connection.");
        }

        if (ClientProperties.getInstance().getBoolean(ClientProperties.DB_DUMP_AFTER_SHUTDOWN)) {

            Thread t = new Thread(() -> System.out.println(dumpStatistics()));
            t.setDaemon(true);
            Runtime.getRuntime().addShutdownHook(t);
        }

        if (ClientProperties.getInstance().getInt(ClientProperties.DB_DUMP_INTERVAL) > 0) {

            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(
                                1000 * ClientProperties.getInstance().getInt(ClientProperties.DB_DUMP_INTERVAL));
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

    /**
     * Is proxy enabled?
     *
     * @return boolean
     */
    @Override
    public final boolean isInitiallyEnabled() {
        return mInitiallyEnableProxy;
    }

    /**
     * Is proxy enabled?
     *
     * @return boolean
     */
    @Override
    public final boolean isEnabled() {
        return mEnableProxy;
    }

    /**
     * Enable the proxy.
     *
     * @param enableProxy boolean
     */
    @Override
    public final void enableProxy(boolean enableProxy) {
        mTrace.info((enableProxy ? "Enable the Connection proxy." : "Disable the Connection proxy."));
        mEnableProxy = enableProxy;
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

        ConnectionHandler connHandler = new ConnectionHandler(
                ClientProperties.getInstance(),
                conn,
                this);

        for (ConnectionListener listener : ClientProperties.getInstance().getConnectionListener()) {
            connHandler.addConnectionListener(listener);
        }

        Connection c = (ProxyConnection) Proxy.newProxyInstance(ProxyConnection.class.getClassLoader(),
                new Class[]{ProxyConnection.class}, connHandler);

        ConnectionEvent event = new ConnectionEvent(connHandler);
        for (ConnectionListener listener : ClientProperties.getInstance().getConnectionListener()) {
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
    final XAConnection getProxyXAConnection(XAConnection conn) {

        if (!mEnableProxy) {
            // get standard connection
            return conn;
        }

        XAConnectionHandler connHandler = new XAConnectionHandler(
                ClientProperties.getInstance(),
                conn,
                this);

        XAConnection c = (ProxyConnection) Proxy.newProxyInstance(ProxyConnection.class.getClassLoader(),
                new Class[]{ProxyConnection.class}, connHandler);

        return c;
    }

    /**
     * Dump the statistics.
     *
     * @return String
     */
    @Override
    public final String dumpStatistics() {
        StringBuilder strb = new StringBuilder();
        for (ExecutionListener obj : ClientProperties.getInstance().getListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        for (ConnectionListener obj : ClientProperties.getInstance().getConnectionListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        for (ExecutionFailedListener obj : ClientProperties.getInstance().getFailedListener()) {
            if (obj.toString() != null) {
                strb.append(obj.toString());
                strb.append("\n");
            }
        }
        return strb.toString();
    }

    /**
     * Clear the statistics.
     */
    @Override
    public final void clearStatistics() {
        for (ExecutionListener ex : ClientProperties.getInstance().getListener()) {
            ex.clearStatistics();
        }
        for (ConnectionListener cl : ClientProperties.getInstance().getConnectionListener()) {
            cl.clearStatistics();
        }
        for (ExecutionFailedListener ef : ClientProperties.getInstance().getFailedListener()) {
            ef.clearStatistics();
        }
    }

    /**
     * Get the int keys.
     *
     * @return String[]
     */
    @Override
    public final List<String> getIntKeys() {
        return ClientProperties.getInstance().getIntKeys();
    }

    /**
     * Get the boolean keys.
     *
     * @return String[]
     */
    @Override
    public final List<String> getBooleanKeys() {
        return ClientProperties.getInstance().getBooleanKeys();
    }

    /**
     * Get the list keys.
     *
     * @return String[]
     */
    @Override
    public final List<String> getListKeys() {
        return ClientProperties.getInstance().getListKeys();
    }

    /**
     * Set an int value.
     *
     * @param property String
     * @param value    int
     */
    @Override
    public final void setProperty(String property, Object value) {
        ClientProperties.getInstance().setProperty(property, value);
    }

    /**
     * Get a property.
     *
     * @param property String
     * @return the int value
     */
    @Override
    public final Object getProperty(String property) {
        return ClientProperties.getInstance().getProperty(property);
    }
}
