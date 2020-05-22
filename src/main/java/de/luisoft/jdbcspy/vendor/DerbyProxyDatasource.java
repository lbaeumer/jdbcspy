package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyDatasource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DerbyProxyDatasource extends ProxyDatasource {

    public DerbyProxyDatasource() {
        super(ClientProperties.DB_DERBY_DATASOURCE_CLASS);
    }

    public void setRetrieveMessagesFromServerOnGetMessage(boolean flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRetrieveMessagesFromServerOnGetMessage", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public void setDeferPrepares(boolean flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDeferPrepares", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public void setProgressiveStreaming(int traceLevel) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setProgressiveStreaming", int.class);
        m.invoke(uDatasource, traceLevel);
    }

    public void setFullyMaterializeLobData(boolean fullyMaterializeLobData) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFullyMaterializeLobData", boolean.class);
        m.invoke(uDatasource, fullyMaterializeLobData);
    }

    public void setCurrentSchema(String currentSchema) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentSchema", String.class);
        m.invoke(uDatasource, currentSchema);
    }
}
