package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyXADatasource;

public class PostgreSqlProxyXADatasource extends ProxyXADatasource {

    public PostgreSqlProxyXADatasource() {
        super(ClientProperties.DB_POSTGRESQL_XA_DATASOURCE_CLASS);
    }
}
