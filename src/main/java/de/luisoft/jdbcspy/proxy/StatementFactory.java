package de.luisoft.jdbcspy.proxy;

import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyConnectionMetaData;
import de.luisoft.jdbcspy.proxy.handler.PreparedStatementHandler;
import de.luisoft.jdbcspy.proxy.handler.StatementHandler;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;

/**
 * The statement factory.
 */
public class StatementFactory {

	/** the instance */
	private static StatementFactory instance;

	/**
	 * Constructor.
	 */
	private StatementFactory() {
	}

	/**
	 * Get an instance.
	 * 
	 * @return an instance
	 */
	public static StatementFactory getInstance() {
		if (instance == null) {
			instance = new StatementFactory();
		}
		return instance;
	}

	/**
	 * Get a statement.
	 * 
	 * @param props
	 *            the client properties
	 * @param ps
	 *            the original statement
	 * @param sql
	 *            the sql code
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @return Statement the proxy statement
	 */
	public Statement getStatement(ClientProperties props, Statement ps, ProxyConnectionMetaData metaData, String sql,
			List<ExecutionListener> listener, List<ExecutionFailedListener> failedListener, String method) {
		StatementFactory factory = getInstance();
		if (ps instanceof CallableStatement) {
			return factory.getCallableStatementProxy(props, (CallableStatement) ps, metaData, sql, listener,
					failedListener, method);
		} else if (ps instanceof PreparedStatement) {
			return factory.getPreparedStatementProxy(props, (PreparedStatement) ps, metaData, sql, listener,
					failedListener, method);
		} else {
			return factory.getStatementProxy(props, ps, metaData, sql, listener, failedListener, method);
		}
	}

	/**
	 * Get a statement proxy.
	 * 
	 * @param props
	 *            the client properties
	 * @param ps
	 *            the original statement
	 * @param sql
	 *            the sql code
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @return the proxy statement
	 */
	private Statement getStatementProxy(ClientProperties props, Statement ps, ProxyConnectionMetaData metaData,
			String sql, List<ExecutionListener> listener, List<ExecutionFailedListener> failedListener, String method) {

		StatementHandler handler = new StatementHandler(props, ps, metaData, sql, method);

		handler.setExecutionFailedListener(failedListener);
		handler.setExecutionListener(listener);

		Statement proxyPrep = (Statement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
				new Class[] { Statement.class, Checkable.class, StatementStatistics.class }, handler);

		return proxyPrep;
	}

	/**
	 * Get a statement proxy.
	 * 
	 * @param props
	 *            the client properties
	 * @param ps
	 *            the original statement
	 * @param sql
	 *            the sql code
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @return the proxy statement
	 */
	private PreparedStatement getPreparedStatementProxy(ClientProperties props, PreparedStatement ps,
			ProxyConnectionMetaData metaData, String sql, List<ExecutionListener> listener,
			List<ExecutionFailedListener> failedListener, String method) {

		PreparedStatementHandler handler = new PreparedStatementHandler(props, ps, metaData, sql, method);

		handler.setExecutionFailedListener(failedListener);
		handler.setExecutionListener(listener);

		PreparedStatement proxyPrep = (PreparedStatement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
				new Class[] { PreparedStatement.class, Checkable.class, StatementStatistics.class }, handler);

		return proxyPrep;
	}

	/**
	 * Get a statement proxy.
	 * 
	 * @param props
	 *            the client properties
	 * @param ps
	 *            the original statement
	 * @param sql
	 *            the sql code
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @return the proxy statement
	 */
	private CallableStatement getCallableStatementProxy(ClientProperties props, CallableStatement ps,
			ProxyConnectionMetaData metaData, String sql, List<ExecutionListener> listener,
			List<ExecutionFailedListener> failedListener, String method) {
		PreparedStatementHandler handler = new PreparedStatementHandler(props, ps, metaData, sql, method);

		handler.setExecutionFailedListener(failedListener);
		handler.setExecutionListener(listener);

		CallableStatement proxyPrep = (CallableStatement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
				new Class[] { CallableStatement.class, Checkable.class, StatementStatistics.class }, handler);

		return proxyPrep;
	}
}
