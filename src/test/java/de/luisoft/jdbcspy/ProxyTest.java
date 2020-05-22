package de.luisoft.jdbcspy;

import de.luisoft.jdbc.testdriver.MyConnection;
import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ProxyConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Some simple tests.
 */
public class ProxyTest {

    Connection conn;
    ProxyConnection proxyConn;
    ConnectionFactory connFac;

    @Before
    public void setUp() {
        conn = new MyConnection(10000, 0, 0);
        connFac = new ConnectionFactory();
        proxyConn = (ProxyConnection) connFac.getProxyConnection(conn);
    }

    @After
    public void tearDown() {
        System.out.println(ConnectionFactory.dumpStatistics());
    }

    @Test
    public void testExecute() throws Exception {
        int cnt = 10000;
        System.out.println("execute(" + cnt + ") statements");

        long l1 = execute(conn, cnt);
        conn.close();

        long l2 = execute(proxyConn, cnt);
        proxyConn.close();

        System.out.println((1000 * (proxyConn.getDuration() - l1) / cnt) + "ms/1000rs dur. overhead");
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
        System.out.println((end - start) + "ms");

        if (conn instanceof ProxyConnection) {
            System.out.println(1000 * (end - start) / cnt + "ms/1000stmts");
        }

        return (end - start);
    }

    @Test
    public void testExecutePrep() throws Exception {
        int cnt = 10000;
        System.out.println("executePrep(" + cnt + ") statements");

        long l1 = executePrepQuery(conn, cnt);
        conn.close();

        long l2 = executePrepQuery(proxyConn, cnt);
        proxyConn.close();

        System.out.println((1000 * (proxyConn.getDuration() - l1) / cnt) + "ms/1000rs dur. overhead");
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
        System.out.println((end - start) + "ms");

        if (conn instanceof ProxyConnection) {
            System.out.println(1000 * (end - start) / cnt + "ms/1000stmts");
        }

        return end - start;
    }

    @Test
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

        ResultSet r = s.executeQuery("" + cnt);
        int i = 0;
        while (r.next()) {
            i++;
            r.getInt(1);
        }
        r.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        s.close();

        if (conn instanceof ProxyConnection) {
            System.out.println(s);
            System.out.println(1000 * (end - start) / cnt + "ms/1000rows");
        }
    }

    @Test
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
        System.out.println((end - start) + "ms");
        s.close();

        if (conn instanceof ProxyConnection) {
            System.out.println(s);
            System.out.println(1000 * (end - start) / cnt + "ms/1000rows");
        }
    }
}
