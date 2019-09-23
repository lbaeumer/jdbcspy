package de.luisoft.jdbcspy;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.luisoft.jdbc.testdriver.MyConnection;

/**
 * Some simple tests.
 *
 */
public class StatTest {

	Connection conn;
	ProxyConnection proxyConn;
	ConnectionFactory connFac;

	@Before
	public void setUp() throws Exception {
		conn = new MyConnection(10000, 1000, 5000);
		connFac = new ConnectionFactory();
		proxyConn = (ProxyConnection) connFac.getConnection(conn);
	}

	@Test
	public void testDump() throws Exception {

		System.out.println("testDump()");

		Statement s = proxyConn.createStatement();
		s.execute("dbproxy dump");
		s.close();
		proxyConn.close();
		System.out.println("\n*****************************************");

	}

	@Test
	public void testSetDisable() throws Exception {

		System.out.println("testSetDisable()");

		Statement s = proxyConn.createStatement();
		s.execute("dbproxy disable");
		s.close();
		proxyConn.close();
		System.out.println("\n*****************************************");

	}

	@Test
	public void testGetProperty() throws Exception {

		System.out.println("testGetProperty()");

		Statement s = proxyConn.createStatement();
		s.execute("dbproxy get ResultSetNextTimeThreshold");
		s.close();
		proxyConn.close();
		System.out.println("\n*****************************************");

	}

	@Test
	public void testSetProperty() throws Exception {

		System.out.println("testSetProperty()");

		Statement s = proxyConn.createStatement();
		s.execute("dbproxy set ResultSetNextTimeThreshold 100");
		s.execute("dbproxy get ResultSetNextTimeThreshold");
		s.close();
		proxyConn.close();
		System.out.println("\n*****************************************");

	}

	@Test
	public void testSetPropertyFailed() throws Exception {

		System.out.println("testSetPropertyFailed()");

		Statement s = proxyConn.createStatement();
		Assert.assertTrue("set does not exist", !s.execute("dbproxy set xxxxxxxxx 100"));
		Assert.assertTrue("get does not exist", !s.execute("dbproxy get xxx"));
		s.close();
		proxyConn.close();
		System.out.println("\n*****************************************");

	}

	@Test
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
