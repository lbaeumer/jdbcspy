package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyDatasource;

public class OracleProxyDatasource extends ProxyDatasource {

    public OracleProxyDatasource() {
        super(ClientProperties.DB_ORACLE_DATASOURCE_CLASS);
    }
}
