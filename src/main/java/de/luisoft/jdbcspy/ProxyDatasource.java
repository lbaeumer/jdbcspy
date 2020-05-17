package de.luisoft.jdbcspy;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyDatasource extends AbstractProxyDatasource implements DataSource {

    public ProxyDatasource() {
        super(ClientProperties.DB_DATASOURCE_CLASS);
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
