package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyDatasource;

public class MssqlProxyDatasource extends ProxyDatasource {

    public MssqlProxyDatasource() {
        super(ClientProperties.DB_MSSQL_DATASOURCE_CLASS);
    }
}
