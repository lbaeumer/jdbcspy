package de.luisoft.jdbcspy;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyXADatasource extends AbstractProxyDatasource implements XADataSource {

    private ConnectionFactory connFac;

    public ProxyXADatasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println("jdbcspy: init proxy xa datasource");
        connFac = new ConnectionFactory();
        String driverClass = (String) connFac.getProperty(ClientProperties.DB_XA_DATASOURCE_CLASS);
        System.out.println("jdbcspy: found xa driver " + driverClass);
        Class c = Class.forName(driverClass);
        System.out.println("jdbcspy: instanciated xa driver " + c);
        uDatasource = c.getDeclaredConstructor().newInstance();
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        XAConnection c = ((XADataSource) uDatasource).getXAConnection();
        return connFac.getProxyXAConnection(c);
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        XAConnection c = ((XADataSource) uDatasource).getXAConnection(user, password);
        return connFac.getProxyXAConnection(c);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ((XADataSource) uDatasource).getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        ((XADataSource) uDatasource).setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ((XADataSource) uDatasource).getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        ((XADataSource) uDatasource).setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ((XADataSource) uDatasource).getParentLogger();
    }

}
