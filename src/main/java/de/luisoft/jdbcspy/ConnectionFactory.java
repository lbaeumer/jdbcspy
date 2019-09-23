package de.luisoft.jdbcspy;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.handler.ConnectionHandler;
import de.luisoft.jdbcspy.proxy.listener.ConnectionEvent;
import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;

/**
 * Title: ConnectionFactory
 */
public class ConnectionFactory implements ProxyConnectionMetaData {

	/** A Logger. */
	private static final Log mTrace = LogFactory.getLog(ConnectionFactory.class);

	/** shall the proxy be enabled */
	private boolean mInitiallyEnableProxy;

	/** shall the proxy be enabled */
	private boolean mEnableProxy;

	/** the version string */
	private static String VERSION;

	/** the build date */
	private static String BUILD_DATE;

	static {
		Properties prop = new Properties();
		try {
			prop.load(ConnectionFactory.class.getResourceAsStream("/de/luisoft/db/build.properties"));
			VERSION = prop.get("build.major") + "." + prop.get("build.release") + "." + prop.get("build.number");
			BUILD_DATE = prop.get("build.date").toString();
		} catch (Exception e) {
			VERSION = "";
			BUILD_DATE = "";
		}
		mTrace.debug("loading properties: " + prop);
	}

	/**
	 * Constructor.
	 */
	ConnectionFactory() {

		ClientProperties props = ClientProperties.getInstance();
		props.init();
		mInitiallyEnableProxy = props.isInitiallyEnabled();

		if (mInitiallyEnableProxy) {
			mEnableProxy = true;
			init(props);
		} else {
			mTrace.info("Disable the ProxyConnectionFactory initially. " + "Using standard connection.");
		}

		if (ClientProperties.getInstance().getBoolean(ClientProperties.DB_DUMP_AFTER_SHUTDOWN)) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(dumpStatistics());
				}
			});
			Runtime.getRuntime().addShutdownHook(t);
		}

		if (ClientProperties.getInstance().getInt(ClientProperties.DB_DUMP_INTERVAL) > 0) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							Thread.sleep(
									1000 * ClientProperties.getInstance().getInt(ClientProperties.DB_DUMP_INTERVAL));
							System.out.println(dumpStatistics());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}
	}

	/**
	 * InitListener.
	 * 
	 * @param props
	 *            the properties
	 */
	private void init(ClientProperties props) {

		mTrace.info("ProxyConnectionFactory (" + VERSION + " " + BUILD_DATE + ") instanciated.");

		if (props == null) {
			props = ClientProperties.getInstance();
			props.init();
		}

		mTrace.info("Setting properties:\n" + props);
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
	 * @param enableProxy
	 *            boolean
	 */
	@Override
	public final void enableProxy(boolean enableProxy) {
		mTrace.info((enableProxy ? "Enable the Connection proxy." : "Disable the Connection proxy."));

		mEnableProxy = enableProxy;
		if (enableProxy) {
			init(null);
		}
	}

	/**
	 * Get the connection.
	 * 
	 * @param conn
	 *            the original connection
	 * @return a proxy connection
	 */
	final Connection getConnection(Connection conn) {

		if (!mEnableProxy) {
			// get standard connection
			return conn;
		}

		ConnectionHandler connHandler = getNewConnectionHandler(conn);
		Connection c = getProxyConnection(connHandler);

		ConnectionEvent event = new ConnectionEvent(connHandler);
		for (ConnectionListener listener : ClientProperties.getInstance().getConnectionListener()) {
			listener.openConnection(event);
		}

		return c;
	}

	/**
	 * Get the proxy connection.
	 * 
	 * @param invocationHandler
	 *            the connection handler
	 * @return Connection
	 */
	protected Connection getProxyConnection(ConnectionHandler invocationHandler) {
		if (mTrace.isDebugEnabled()) {
			mTrace.debug("get proxy connection");
		}

		ProxyConnection proxyConn = (ProxyConnection) Proxy.newProxyInstance(ProxyConnection.class.getClassLoader(),
				new Class[] { ProxyConnection.class }, invocationHandler);

		return proxyConn;
	}

	/**
	 * Get a connection handler
	 * 
	 * @param conn
	 *            Connection
	 * @return InvocationHandler
	 */
	protected final ConnectionHandler getNewConnectionHandler(Connection conn) {
		ClientProperties props = ClientProperties.getInstance();
		ConnectionHandler handler = new ConnectionHandler(props, conn, ClientProperties.getInstance().getListener(),
				ClientProperties.getInstance().getFailedListener(), this);
		for (ConnectionListener listener : ClientProperties.getInstance().getConnectionListener()) {
			handler.addConnectionListener(listener);
		}
		return handler;
	}

	/**
	 * Dump the statistics.
	 * 
	 * @return String
	 */
	@Override
	public final String dumpStatistics() {
		StringBuffer strb = new StringBuffer();
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
	 * @param property
	 *            String
	 * @param value
	 *            int
	 */
	@Override
	public final void setProperty(String property, Object value) {
		ClientProperties.getInstance().setProperty(property, value);
	}

	/**
	 * Get a property.
	 * 
	 * @param property
	 *            String
	 * @return the int value
	 */
	@Override
	public final Object getProperty(String property) {
		return ClientProperties.getInstance().getProperty(property);
	}
}
