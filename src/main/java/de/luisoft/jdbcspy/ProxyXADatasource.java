package de.luisoft.jdbcspy;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ProxyXADatasource implements XADataSource {

    private ConnectionFactory connFac;
    private XADataSource uDatasource;

    public ProxyXADatasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        connFac = new ConnectionFactory();
        String driverClass = (String) connFac.getProperty(ClientProperties.DB_XA_DATASOURCE_CLASS);
        System.out.println("inst " + driverClass);
        Class c = Class.forName(driverClass);
        uDatasource = (XADataSource) c.getDeclaredConstructor().newInstance();
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        XAConnection c = uDatasource.getXAConnection();
        return connFac.getConnection(c);
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        XAConnection c = uDatasource.getXAConnection(user, password);
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

}
