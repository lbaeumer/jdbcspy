package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class DB2ProxyDriver extends AbstractProxyDriver {

    static {
        new DB2ProxyDriver();
    }

    public DB2ProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_DB2_DRIVER_CLASS));
    }
}
