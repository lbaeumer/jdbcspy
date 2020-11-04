package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyDatasource;

public class PostgreSqlProxyDatasource extends ProxyDatasource {

    public PostgreSqlProxyDatasource() {
        super(ClientProperties.DB_POSTGRESQL_DATASOURCE_CLASS);
    }
}
