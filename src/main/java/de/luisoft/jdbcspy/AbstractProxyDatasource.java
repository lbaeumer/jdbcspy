package de.luisoft.jdbcspy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractProxyDatasource {

    protected Object uDatasource;

    public void setDriverType(int type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDriverType", Integer.class);
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
}
