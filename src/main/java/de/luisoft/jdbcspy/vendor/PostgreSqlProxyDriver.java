package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class PostgreSqlProxyDriver extends AbstractProxyDriver {

    static {
        new PostgreSqlProxyDriver();
    }

    public PostgreSqlProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_POSTGRESQL_DRIVER_CLASS));
    }
}
