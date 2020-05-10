package de.luisoft.jdbcspy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractProxyDatasource {

    protected Object uDatasource;

    public void setDriverType(int type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            Class<?> c = uDatasource.getClass();
            Method m = c.getMethod("setDriverType", int.class);
            m.invoke(uDatasource, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            Class<?> c = uDatasource.getClass();
            Method m = c.getMethod("setDatabaseName", String.class);
            m.invoke(uDatasource, databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setCurrentSchema(String currentSchema) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentSchema", String.class);
        m.invoke(uDatasource, currentSchema);
    }

    public void setRetrieveMessagesFromServerOnGetMessage(String flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRetrieveMessagesFromServerOnGetMessage", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public void setDeferPrepares(String flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDeferPrepares", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public void setTraceLevel(String flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceLevel", int.class);
        m.invoke(uDatasource, flag);
    }
}
