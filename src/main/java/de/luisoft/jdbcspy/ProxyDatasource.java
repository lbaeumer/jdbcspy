package de.luisoft.jdbcspy;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyDatasource implements DataSource {

    private ConnectionFactory connFac;
    private DataSource uDatasource;

    public ProxyDatasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        connFac = new ConnectionFactory();
        String driverClass = (String) connFac.getProperty(ClientProperties.DB_DATASOURCE_CLASS);
        System.out.println("inst " + driverClass);
        Class c = Class.forName(driverClass);
        uDatasource = (DataSource) c.getDeclaredConstructor().newInstance();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = uDatasource.getConnection();
        return connFac.getConnection(c);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection c = uDatasource.getConnection(username, password);
        return connFac.getConnection(c);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return uDatasource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        uDatasource.setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return uDatasource.getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        uDatasource.setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return uDatasource.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return uDatasource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return uDatasource.isWrapperFor(iface);
    }
}
