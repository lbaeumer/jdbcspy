package de.luisoft.jdbcspy;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.luisoft.jdbc.testdriver.MyConnection;
import de.luisoft.jdbcspy.ConnectionFactory;
import de.luisoft.jdbcspy.ProxyConnection;

/**
 * Some simple tests.
 *
 */
public class ProxyStatementTest extends AbstractStatementTest {

    Connection conn;
    ProxyConnection proxyConn;
    ConnectionFactory connFac;
    
    public ProxyStatementTest(String name) {
        super(name);
    	conn = new MyConnection(10000, 1000, 5000);
    	connFac = new ConnectionFactory();
    	proxyConn = (ProxyConnection) connFac.getConnection(conn);
    }

    public static Test suite() {
        return new TestSuite(ProxyStatementTest.class);
    }

    protected void setUp() throws Exception {
    	setUp(proxyConn);
    }

    protected void tearDown() throws Exception {
    	tearDown(proxyConn);
    }
}
