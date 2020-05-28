package de.luisoft.jdbcspy;

public class ProxyDriver extends AbstractProxyDriver {

    static {
        new ProxyDriver();
    }

    public ProxyDriver() {
        super((String) ClientProperties.getProperty(ClientProperties.DB_DRIVER_CLASS));
    }
}
