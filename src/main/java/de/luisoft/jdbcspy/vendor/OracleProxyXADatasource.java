package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyXADatasource;

public class OracleProxyXADatasource extends ProxyXADatasource {

    public OracleProxyXADatasource() {
        super(ClientProperties.DB_ORACLE_XA_DATASOURCE_CLASS);
    }
}
