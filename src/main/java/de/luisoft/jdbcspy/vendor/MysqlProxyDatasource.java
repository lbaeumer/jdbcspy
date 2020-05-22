package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyDatasource;

public class MysqlProxyDatasource extends ProxyDatasource {

    public MysqlProxyDatasource() {
        super(ClientProperties.DB_MYSQL_DATASOURCE_CLASS);
    }
}
