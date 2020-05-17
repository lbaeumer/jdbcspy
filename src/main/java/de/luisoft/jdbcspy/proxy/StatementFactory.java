package de.luisoft.jdbcspy.proxy;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.handler.PreparedStatementInvocationHandler;
import de.luisoft.jdbcspy.proxy.handler.StatementInvocationHandler;

import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * The statement factory.
 */
public class StatementFactory {

    /**
     * the instance
     */
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
     * @param props the client properties
     * @param ps    the original statement
     * @param sql   the sql code
     * @return Statement the proxy statement
     */
    public Statement getStatement(ClientProperties props, Statement ps, ProxyConnectionMetaData metaData, String sql,
                                  String method) {
        StatementFactory factory = getInstance();
        if (ps instanceof CallableStatement) {
            return factory.getCallableStatementProxy(props, (CallableStatement) ps, metaData, sql,
                    method);
        } else if (ps instanceof PreparedStatement) {
            return factory.getPreparedStatementProxy(props, (PreparedStatement) ps, metaData, sql, method);
        } else {
            return factory.getStatementProxy(props, ps, metaData, sql, method);
        }
    }

    /**
     * Get a statement proxy.
     *
     * @param props the client properties
     * @param ps    the original statement
     * @param sql   the sql code
     * @return the proxy statement
     */
    private Statement getStatementProxy(ClientProperties props, Statement ps, ProxyConnectionMetaData metaData,
                                        String sql, String method) {

        StatementInvocationHandler handler = new StatementInvocationHandler(props, ps, metaData, sql, method);

        handler.setExecutionFailedListener(ClientProperties.getInstance().getFailedListener());
        handler.setExecutionListener(ClientProperties.getInstance().getListener());

        return (Statement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
                new Class[]{Statement.class, Checkable.class, StatementStatistics.class}, handler);
    }

    /**
     * Get a statement proxy.
     *
     * @param props the client properties
     * @param ps    the original statement
     * @param sql   the sql code
     * @return the proxy statement
     */
    private PreparedStatement getPreparedStatementProxy(ClientProperties props, PreparedStatement ps,
                                                        ProxyConnectionMetaData metaData, String sql, String method) {

        PreparedStatementInvocationHandler handler = new PreparedStatementInvocationHandler(props, ps, metaData, sql, method);

        handler.setExecutionFailedListener(ClientProperties.getInstance().getFailedListener());
        handler.setExecutionListener(ClientProperties.getInstance().getListener());

        return (PreparedStatement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
                new Class[]{PreparedStatement.class, Checkable.class, StatementStatistics.class}, handler);
    }

    /**
     * Get a statement proxy.
     *
     * @param props the client properties
     * @param ps    the original statement
     * @param sql   the sql code
     * @return the proxy statement
     */
    private CallableStatement getCallableStatementProxy(ClientProperties props, CallableStatement ps,
                                                        ProxyConnectionMetaData metaData, String sql, String method) {
        PreparedStatementInvocationHandler handler = new PreparedStatementInvocationHandler(props, ps, metaData, sql, method);

        handler.setExecutionFailedListener(ClientProperties.getInstance().getFailedListener());
        handler.setExecutionListener(ClientProperties.getInstance().getListener());

        return (CallableStatement) Proxy.newProxyInstance(Checkable.class.getClassLoader(),
                new Class[]{CallableStatement.class, Checkable.class, StatementStatistics.class}, handler);
    }
}
