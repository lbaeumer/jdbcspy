package de.luisoft.jdbcspy;

import de.luisoft.jdbc.testdriver.MyConnection;
import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ProxyConnection;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Some simple tests.
 */
public class ProxyPerformanceTest {

    private static final boolean debug = false;
    protected Connection conn;

    @BeforeEach
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
                assertTrue(Math.abs(st.getExecutionTime()) < 20,
                        "exec time must be 0 != " + st.getExecutionTime());
                assertEquals(0, st.getItemCount(), "#items must be 0!=" + st.getItemCount());
                assertEquals(st.getExecutionTime(), st.getDuration(),
                        "duration must be equal execution time " + st.getExecutionTime() + "!=" + st.getDuration());
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
                assertTrue(Math.abs(st.getExecutionTime()) < 25,
                        "exec time must be 1000 !=" + st.getExecutionTime());
                assertEquals(st.getItemCount(), 10000, "#items must be " + 10000 + "!=" + st.getItemCount());
                assertTrue(st.getDuration() - st.getExecutionTime() <= 50,
                        "duration must be greater execution time " + st.getExecutionTime() + "!=" + st.getDuration());
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("executeQuery: total time=" + (end - start) + "ms");

        // <= 5ms
        System.out.println("Overhead rs iteration: " + 1000 * (end - start) / 10000 * cnt + "ms/1000rs");
    }
}
