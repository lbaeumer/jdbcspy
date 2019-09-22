package de.luisoft.jdbcspy;

import org.junit.After;
import org.junit.Before;

import de.luisoft.jdbc.testdriver.MyConnection;

/**
 * Some simple tests.
 *
 */
public class ProxyStatementTest extends AbstractStatementTest {

    ProxyConnection proxyConn;
    ConnectionFactory connFac;
    
    @Before
    public void setUp() throws Exception {
    	conn = new MyConnection(10000, 1000, 5000);
    	connFac = new ConnectionFactory();
    	proxyConn = (ProxyConnection) connFac.getConnection(conn);
    }

    @After
    public void tearDown() throws Exception {
    	proxyConn.close();
    }
}
