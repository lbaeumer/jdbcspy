package de.luisoft.jdbcspy;

import de.luisoft.jdbcspy.proxy.ConnectionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractProxyDatasource {

    protected static ConnectionFactory connFac;
    protected Object uDatasource;

    public AbstractProxyDatasource(String driver) {
        Class c = null;
        Exception e = null;
        try {
            connFac = new ConnectionFactory();
            String driverClass = (String) ClientProperties.getProperty(driver);
            c = Class.forName(driverClass);
            uDatasource = c.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            e = ex;
        } finally {
            if (c != null) {
                System.out.println("jdbcspy: instanciated driver " + c);
            } else {
                System.out.println("jdbcspy: init failed.");
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDriverType(int type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDriverType", int.class);
        m.invoke(uDatasource, type);
    }

    public void setUser(String user) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUser", String.class);
        m.invoke(uDatasource, user);
    }

    public void setPassword(String password) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPassword", String.class);
        m.invoke(uDatasource, password);
    }

    public void setDatabaseName(String databaseName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDatabaseName", String.class);
        m.invoke(uDatasource, databaseName);
    }

    public void setServerName(String serverName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setServerName", String.class);
        m.invoke(uDatasource, serverName);
    }

    public void setPortNumber(int portNumber) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPortNumber", int.class);
        m.invoke(uDatasource, portNumber);
    }

    public void setTraceLevel(int traceLevel) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceLevel", int.class);
        m.invoke(uDatasource, traceLevel);
    }

    @Override
    public String toString() {
        return ConnectionFactory.dumpStatistics();
    }
}
