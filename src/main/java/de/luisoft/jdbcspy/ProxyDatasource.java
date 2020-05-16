package de.luisoft.jdbcspy;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyDatasource extends AbstractProxyDatasource implements DataSource {

    private ConnectionFactory connFac;

    public ProxyDatasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println("jdbcspy: init proxy datasource");
        connFac = new ConnectionFactory();
        String driverClass = (String) connFac.getProperty(ClientProperties.DB_DATASOURCE_CLASS);
        System.out.println("jdbcspy: driver " + driverClass);
        Class c = Class.forName(driverClass);
        System.out.println("jdbcspy: instanciated driver " + c);
        uDatasource = c.getDeclaredConstructor().newInstance();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = ((DataSource) uDatasource).getConnection();
        return connFac.getProxyConnection(c);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection c = ((DataSource) uDatasource).getConnection(username, password);
        return connFac.getProxyConnection(c);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ((DataSource) uDatasource).getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        ((DataSource) uDatasource).setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ((DataSource) uDatasource).getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        ((DataSource) uDatasource).setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ((DataSource) uDatasource).getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ((DataSource) uDatasource).unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ((DataSource) uDatasource).isWrapperFor(iface);
    }
}
