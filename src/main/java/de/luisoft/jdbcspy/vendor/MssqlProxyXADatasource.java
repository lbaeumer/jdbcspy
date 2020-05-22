package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyXADatasource;

public class MssqlProxyXADatasource extends ProxyXADatasource {

    public MssqlProxyXADatasource() {
        super(ClientProperties.DB_MSSQL_XA_DATASOURCE_CLASS);
    }
}
