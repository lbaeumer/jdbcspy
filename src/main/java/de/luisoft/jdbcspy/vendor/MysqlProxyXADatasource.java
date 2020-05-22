package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyXADatasource;

public class MysqlProxyXADatasource extends ProxyXADatasource {

    public MysqlProxyXADatasource() {
        super(ClientProperties.DB_MYSQL_XA_DATASOURCE_CLASS);
    }
}
