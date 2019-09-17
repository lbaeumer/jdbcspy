package de.luisoft.jdbcspy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import de.luisoft.jdbc.testdriver.MyConnection;
import de.luisoft.jdbcspy.ConnectionFactory;
import de.luisoft.jdbcspy.ProxyConnection;

/**
 * Some simple tests.
 *
 */
public class StatTest extends TestCase {

    Connection conn;
    ProxyConnection proxyConn;
    ConnectionFactory connFac;
    
    public StatTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(StatTest.class);
    }

    protected void setUp() throws Exception {
    	conn = new MyConnection(10000, 1000, 5000);
    	connFac = new ConnectionFactory();
    	proxyConn = (ProxyConnection) connFac.getConnection(conn);
    }

    public void testDump() throws Exception {

    	System.out.println("testDump()");

    	Statement s = proxyConn.createStatement();
    	s.execute("dbproxy dump");
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }

    public void testSetDisable() throws Exception {

    	System.out.println("testSetDisable()");

    	Statement s = proxyConn.createStatement();
    	s.execute("dbproxy disable");
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }

    public void testGetProperty() throws Exception {

    	System.out.println("testGetProperty()");

    	Statement s = proxyConn.createStatement();
    	s.execute("dbproxy get ResultSetNextTimeThreshold");
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }

    public void testSetProperty() throws Exception {

    	System.out.println("testSetProperty()");

    	Statement s = proxyConn.createStatement();
    	s.execute("dbproxy set ResultSetNextTimeThreshold 100");
    	s.execute("dbproxy get ResultSetNextTimeThreshold");
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }

    public void testSetPropertyFailed() throws Exception {

    	System.out.println("testSetPropertyFailed()");

    	Statement s = proxyConn.createStatement();
    	assertTrue("set does not exist", !s.execute("dbproxy set xxxxxxxxx 100"));
    	assertTrue("get does not exist", !s.execute("dbproxy get xxx"));
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }

    public void testDump2() throws Exception {

    	System.out.println("testDump2()");

    	Statement s = proxyConn.createStatement();
    	s.execute("select * from xusers");
    	s.executeQuery("dbproxy dump");
    	s.close();
    	proxyConn.close();
    	System.out.println("\n*****************************************");
    	
    }
}
