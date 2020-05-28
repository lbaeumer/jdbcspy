package de.luisoft.jdbc.test;

import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.ResultSetStatistics;
import de.luisoft.jdbcspy.proxy.StatementStatistics;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DriverTest {

    @Test
    public void testStmtWith100000rsa() throws Exception {
        int cnt = 100000;
        Class.forName("de.luisoft.jdbcspy.ProxyDriver");
        System.out.println("starting ...");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
        long start = System.currentTimeMillis();
        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();
        int x = 1;
        while (rs.next()) {
            int nb = rs.getInt(1);
            Assert.assertEquals(nb, x++);
        }
        ResultSetStatistics rstat = (ResultSetStatistics) rs;
        Assert.assertEquals(cnt, rstat.getItemCount());
        Assert.assertTrue("rs=" + rstat.getDuration(), rstat.getDuration() > 1000 && rstat.getDuration() < 1200);

        rs.close();
        rstat = (ResultSetStatistics) rs;
        Assert.assertEquals(cnt, rstat.getItemCount());
        Assert.assertTrue("rs=" + rstat.getDuration(), rstat.getDuration() > 1000 && rstat.getDuration() < 1200);

        StatementStatistics sstat = (StatementStatistics) p;
        Assert.assertEquals(cnt, sstat.getItemCount());
        Assert.assertEquals("select * from test", sstat.getSQL());
        Assert.assertTrue(sstat.getExecuteCaller().contains("DriverTest.testStmtWith100000rs"));
        Assert.assertTrue(sstat.getDuration() > 1500 && sstat.getDuration() < 1700);
        Assert.assertTrue("exec time=" + sstat.getExecutionTime(),
                sstat.getExecutionTime() >= 500 && sstat.getExecutionTime() < 520);

        p.close();

        sstat = (StatementStatistics) p;
        Assert.assertEquals(cnt, sstat.getItemCount());
        Assert.assertEquals("select * from test", sstat.getSQL());
        Assert.assertTrue(sstat.getExecuteCaller().contains("DriverTest.testStmtWith100000rs"));
        Assert.assertTrue(sstat.getDuration() > 1500 && sstat.getDuration() < 1700);
        Assert.assertTrue("exec time=" + sstat.getExecutionTime(),
                sstat.getExecutionTime() >= 500 && sstat.getExecutionTime() < 520);

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertTrue("s=" + stat.getStatements().get(0),
                stat.getStatements().get(0).toString().startsWith("\"select * from test\""));
        Assert.assertTrue(stat.getCaller().contains("DriverTest.testStmtWith100000rs"));
        Assert.assertTrue(stat.getDuration() > 1500 && stat.getDuration() < 1700);

        c.close();
        long end = (System.currentTimeMillis() - start);
        System.out.println(cnt / (end - 1500) + "rs/ms; finished in " + end + "ms");
        // assumption: execute min 90rs/ms but max. 150rs/ms, so overhead is
        // 100000/100

        stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.testStmtWith100000rs"));

    }

    @Test
    public void minimal() throws Exception {
        Class.forName("de.luisoft.jdbcspy.ProxyDriver");
        Connection c = DriverManager.getConnection("proxy:mytestdb&rscnt=100&itertime=1000&exectime=500");

        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();

        while (rs.next()) {
            // read result set
        }
        rs.close();
        p.close();
        c.close();

        Thread.sleep(1000);
        System.out.println("connection dump:\n"
                + ConnectionFactory.dumpStatistics());
    }

    @Test
    public void testStmtWith1000000rs() throws Exception {
        int cnt = 1000000;
        Class.forName("de.luisoft.jdbcspy.ProxyDriver");
        System.out.println("starting " + cnt + " rs...");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
        long start = System.currentTimeMillis();
        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();
        int x = 1;
        while (rs.next()) {
            int nb = rs.getInt(1);
            Assert.assertEquals(nb, x++);
        }
        rs.close();
        p.close();
        c.close();

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller(), stat.getCaller().contains("DriverTest.testStmtWith1000000rs"));

        long end = (System.currentTimeMillis() - start);
        System.out.println("reading " + cnt + " rs; " + cnt / (end - 1500) + "rs/ms; finished in " + end + "ms");
    }

    @Test
    public void testStmtWith100000rs() throws Exception {
        int cnt = 100000;
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        System.out.println("starting " + cnt + " rs...");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
        long start = System.currentTimeMillis();
        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();
        int x = 1;
        while (rs.next()) {
            int nb = rs.getInt(1);
            Assert.assertEquals(nb, x++);
        }
        rs.close();
        p.close();
        c.close();

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.testStmtWith100000rs"));

        long end = (System.currentTimeMillis() - start);
        System.out.println("reading " + cnt + " rs; " + cnt / (end - 1500) + "rs/ms; finished in " + end + "ms");
    }

    @Test
    public void testStmtWith10000rs() throws Exception {
        int cnt = 10000;
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        System.out.println("starting " + cnt + " rs...");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
        long start = System.currentTimeMillis();
        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();
        int x = 1;
        while (rs.next()) {
            int nb = rs.getInt(1);
            Assert.assertEquals(nb, x++);
        }
        rs.close();
        p.close();
        c.close();

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());

        long end = (System.currentTimeMillis() - start);
        System.out.println("reading " + cnt + " rs; " + cnt / (end - 1500) + "rs/ms; finished in " + end + "ms");
    }

    @Test
    public void testStmtWith100rs() throws Exception {
        int cnt = 100;
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        System.out.println("starting " + cnt + " rs...");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
        long start = System.currentTimeMillis();
        PreparedStatement p = c.prepareStatement("select * from test");
        ResultSet rs = p.executeQuery();
        int x = 1;
        while (rs.next()) {
            int nb = rs.getInt(1);
            Assert.assertEquals(nb, x++);
        }
        rs.close();
        p.close();
        c.close();

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(1, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());

        long end = (System.currentTimeMillis() - start);
        System.out.println("reading " + cnt + " rs; " + cnt / (end - 1500) + "rs/ms; finished in " + end + "ms");
    }

    @Test
    public void test100StmtWith1rs() throws Exception {
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
        long start = System.currentTimeMillis();
        int cnt = 10000;
        PreparedStatement p;
        for (int i = 0; i < cnt; i++) {
            p = c.prepareStatement("select * from test");
            ResultSet rs = p.executeQuery();
            int x = 1;
            while (rs.next()) {
                int nb = rs.getInt(1);
                Assert.assertEquals(nb, x++);
            }
            rs.close();
            p.close();

        }
        c.close();

        long end = (System.currentTimeMillis() - start);
        System.out
                .println("reading " + cnt + " pstmts; " + 1000 * cnt / (end) + "pstmts/s; finished in " + end + "ms");

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(cnt, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.test100StmtWith1rs"));
    }

    @Ignore
    @Test
    public void test10000StmtWith1rs() throws Exception {
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
        long start = System.currentTimeMillis();
        int cnt = 100000;
        PreparedStatement p;
        for (int i = 0; i < cnt; i++) {
            p = c.prepareStatement("select * from test");
            ResultSet rs = p.executeQuery();
            int x = 1;
            while (rs.next()) {
                int nb = rs.getInt(1);
                Assert.assertEquals(nb, x++);
            }
            rs.close();
            p.close();

        }
        c.close();

        long end = (System.currentTimeMillis() - start);
        System.out
                .println("reading " + cnt + " pstmts; " + 1000 * cnt / (end) + "pstmts/s; finished in " + end + "ms");

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(cnt, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.test10000StmtWith1rs"));
    }

    @Ignore
    @Test
    public void test10000StmtWith1rs2() throws Exception {
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
        long start = System.currentTimeMillis();
        int cnt = 100000;
        PreparedStatement p;
        for (int i = 0; i < cnt; i++) {
            p = c.prepareStatement("select * from test where x = ?");
            p.setInt(1, i);
            ResultSet rs = p.executeQuery();
            int x = 1;
            while (rs.next()) {
                int nb = rs.getInt(1);
                Assert.assertEquals(nb, x++);
            }
            rs.close();
            p.close();

        }
        c.close();

        long end = (System.currentTimeMillis() - start);
        System.out
                .println("reading " + cnt + " pstmts; " + 1000 * cnt / (end) + "pstmts/s; finished in " + end + "ms");

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(cnt, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.test10000StmtWith1rs2"));
    }

    @Test
    public void test10000StmtWith1rs3() throws Exception {
        Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");
        Connection c = DriverManager.getConnection("proxy:db:xxy&rscnt=5&itertime=0&exectime=0");
        long start = System.currentTimeMillis();
        int cnt = 1000;
        PreparedStatement p;
        for (int i = 0; i < cnt; i++) {
            p = c.prepareStatement("select * from test where x = ?");
            p.setInt(1, i);
            ResultSet rs = p.executeQuery();
            int x = 1;
            while (rs.next()) {
                int nb = rs.getInt(1);
                Assert.assertEquals(nb, x++);
            }
            rs.close();

            StatementStatistics sstat = (StatementStatistics) p;
            Assert.assertEquals(5, sstat.getItemCount());
            Assert.assertEquals("select * from test where x = " + i, sstat.getSQL());

            p.close();

        }
        c.close();

        long end = (System.currentTimeMillis() - start);
        System.out
                .println("reading " + cnt + " pstmts; " + 1000 * cnt / (end) + "pstmts/s; finished in " + end + "ms");

        ConnectionStatistics stat = (ConnectionStatistics) c;
        Assert.assertEquals(cnt, stat.getItemCount());
        Assert.assertEquals(0, stat.getStatements().size());
        Assert.assertTrue(stat.getCaller().contains("DriverTest.test10000StmtWith1rs3"));
    }
}
