package de.luisoft.jdbcspy;

import de.luisoft.jdbc.testdriver.MyConnection;
import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ProxyConnection;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Some simple tests.
 */
public class ProxyPerformanceTest {

    private static final boolean debug = false;
    protected Connection conn;

    @Before
    public void setUp() {
        Connection uconn = new MyConnection(10000, 0, 0);
        ConnectionFactory connFac = new ConnectionFactory();
        conn = connFac.getProxyConnection(uconn);
    }

    @Test
    public void testExecute() throws Exception {

        int cnt = 1;
        long start = System.currentTimeMillis();

        for (int i = 0; i < cnt; i++) {
            Statement s = conn.createStatement();
            boolean b = s.execute("select * from x");
            s.close();

            if ((debug || i < 2 || i > cnt - 3) && conn instanceof ProxyConnection) {
                StatementStatistics st = (StatementStatistics) s;
                System.out.println(i + ":" + s);
                Assert.assertTrue("exec time must be 0 != " + st.getExecutionTime(),
                        Math.abs(st.getExecutionTime()) < 20);
                Assert.assertEquals("#items must be 0!=" + st.getItemCount(), 0, st.getItemCount());
                Assert.assertEquals("duration must be equal execution time " + st.getExecutionTime() + "!=" + st.getDuration(),
                        st.getExecutionTime(), st.getDuration());
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("execute: total time=" + (end - start) + "ms");

        // <= 5ms
        System.out.println("Overhead Exec:" + (end - start) / cnt + "ms/execution");
    }

    @Test
    public void testExecuteQuery() throws Exception {

        int cnt = 1;
        long start = System.currentTimeMillis();

        for (int i = 0; i < cnt; i++) {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from x");
            while (rs.next()) {
                rs.getInt(0);
            }
            rs.close();
            s.close();

            if ((debug || i < 2 || i > cnt - 3) && conn instanceof ProxyConnection) {
                StatementStatistics st = (StatementStatistics) s;
                Assert.assertTrue("exec time must be 1000 !=" + st.getExecutionTime(),
                        Math.abs(st.getExecutionTime()) < 25);
                Assert.assertEquals("#items must be " + 10000 + "!=" + st.getItemCount(), st.getItemCount(), 10000);
                Assert.assertTrue(
                        "duration must be greater execution time " + st.getExecutionTime() + "!=" + st.getDuration(),
                        st.getDuration() - st.getExecutionTime() <= 50);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("executeQuery: total time=" + (end - start) + "ms");

        // <= 5ms
        System.out.println("Overhead rs iteration: " + 1000 * (end - start) / 10000 * cnt + "ms/1000rs");
    }
}
