package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class MssqlProxyDriver extends AbstractProxyDriver {

    static {
        new MssqlProxyDriver();
    }

    public MssqlProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_MSSQL_DRIVER_CLASS));
    }
}
