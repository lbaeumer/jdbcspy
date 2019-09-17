package de.luisoft.jdbcspy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class ProxyTest extends TestCase {

    Connection conn;
    ProxyConnection proxyConn;
    ConnectionFactory connFac;
    
    public ProxyTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ProxyTest.class);
    }

    protected void setUp() throws Exception {
    	conn = new MyConnection(10000, 0, 0);
    	connFac = new ConnectionFactory();
    	proxyConn = (ProxyConnection) connFac.getConnection(conn);
    }

    protected void tearDown() throws Exception {
    	System.out.println(connFac.dumpStatistics());
    }

    public void testExecute() throws Exception {
    	int cnt = 10000;
    	System.out.println("execute(" + cnt + ") statements");

    	long l1 = execute(conn, cnt);
    	conn.close();

    	long l2 = execute(proxyConn, cnt);
    	proxyConn.close();

    	System.out.println((1000*(proxyConn.getDuration()-l1)/cnt)+"ms/1000rs dur. overhead");
    	System.out.println(proxyConn.dump());
    	System.out.println("finished execute()");
    	System.out.println("\n*****************************************");
    }

    private long execute(Connection conn, int cnt) throws SQLException {
    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
        	Statement s = conn.createStatement();         
            s.execute("select * from ? ");
        	s.close();
    	}

    	long end = System.currentTimeMillis();
        System.out.println((end-start) + "ms");

        if (conn instanceof ProxyConnection) {
        	System.out.println(1000*(end-start)/cnt + "ms/1000stmts");
        }
        
        return (end-start);
    }
    
    public void testExecutePrep() throws Exception {
    	int cnt = 10000;
    	System.out.println("executePrep(" + cnt + ") statements");

    	long l1 = executePrepQuery(conn, cnt);
    	conn.close();

    	long l2 = executePrepQuery(proxyConn, cnt);
    	proxyConn.close();

    	System.out.println((1000*(proxyConn.getDuration()-l1)/cnt)+"ms/1000rs dur. overhead");
    	System.out.println(proxyConn.dump());
    	System.out.println("finished execute()");
    	System.out.println("\n*****************************************");
    }

    private long executePrepQuery(Connection conn, int cnt) throws SQLException {
    	long start = System.currentTimeMillis();

    	PreparedStatement s = conn.prepareStatement("select * from ? ");         
    	for (int i = 0; i < cnt; i++) {
    		s.setInt(1, i);
            s.execute();
        	s.close();
    	}

    	long end = System.currentTimeMillis();
        System.out.println((end-start) + "ms");

        if (conn instanceof ProxyConnection) {
        	System.out.println(1000*(end-start)/cnt + "ms/1000stmts");
        }
        
        return end-start;
    }
    
    public void testExecuteQuery() throws Exception {

    	int cnt = 100000;
    	System.out.println("executeQuery with (" + cnt + ") resultsets");

    	executeQuery(conn, cnt);
    	conn.close();

        executeQuery(proxyConn, cnt);
    	proxyConn.close();

    	System.out.println(proxyConn.dump());
    	System.out.println("finished executeQuery()");
    	System.out.println("\n*****************************************");
    }
    
    private void executeQuery(Connection conn, int cnt) throws SQLException {

    	Statement s = conn.createStatement();         

    	long start = System.currentTimeMillis();

    	ResultSet r = s.executeQuery(""+cnt);
        int i = 0;
        while (r.next()) {
        	i++;
        	r.getInt(1);
        }
        r.close();
        long end = System.currentTimeMillis();
        System.out.println((end-start) + "ms");
        s.close();

        if (conn instanceof ProxyConnection) {
        	System.out.println(s);
        	System.out.println(1000*(end-start)/cnt + "ms/1000rows");
        }
    }

    public void testExecutePrepQuery() throws Exception {

    	int cnt = 10000;
    	System.out.println("updatePrepQuery(" + cnt + ") * update");

    	updatePrepQuery(conn, cnt);
    	conn.close();

        updatePrepQuery(proxyConn, cnt);
    	proxyConn.close();

    	System.out.println(proxyConn.dump());
    	System.out.println("finished updatePrepQuery()");
    	System.out.println("\n*****************************************");
    	
    }
    
    private void updatePrepQuery(Connection conn, int cnt) throws SQLException {

    	PreparedStatement s = conn.prepareStatement("select ?, ?");         

    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
    		s.setInt(1, i);
    		s.setInt(2, i);
    		s.executeUpdate();
    	}

        long end = System.currentTimeMillis();
        System.out.println((end-start) + "ms");
        s.close();

        if (conn instanceof ProxyConnection) {
        	System.out.println(s);
        	System.out.println(1000*(end-start)/cnt + "ms/1000rows");
        }
    }
}
