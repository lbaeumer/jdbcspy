package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class MysqlProxyDriver extends AbstractProxyDriver {

    static {
        new MysqlProxyDriver();
    }

    public MysqlProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_MYSQL_DRIVER_CLASS));
    }
}
