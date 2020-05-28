package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class DerbyProxyDriver extends AbstractProxyDriver {

    static {
        new DerbyProxyDriver();
    }

    public DerbyProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_DERBY_DRIVER_CLASS));
    }
}
