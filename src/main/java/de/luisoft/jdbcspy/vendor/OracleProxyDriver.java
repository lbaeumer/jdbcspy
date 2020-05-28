package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.AbstractProxyDriver;
import de.luisoft.jdbcspy.ClientProperties;

public class OracleProxyDriver extends AbstractProxyDriver {

    static {
        new OracleProxyDriver();
    }

    public OracleProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_ORACLE_DRIVER_CLASS));
    }
}
