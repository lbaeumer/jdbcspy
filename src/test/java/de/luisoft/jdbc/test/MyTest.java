package de.luisoft.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Test;

import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.proxy.ResultSetStatistics;
import de.luisoft.jdbcspy.proxy.StatementStatistics;

public class MyTest {

	private static final int MAX_OVERHEAD = 80;  // resultsets/ms
	private static final int MIN_OVERHEAD = 140;  // resultsets/ms

	private static final int MAX_PST_OVERHEAD = 800;  // prepared stmt/s
	private static final int MIN_PST_OVERHEAD = 1200;  // prepared stmt/s

	@Test
	public void testStmtWith100000rsa() throws Exception {
		int cnt = 100000;
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		System.out.println("starting ...");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
		long start = System.currentTimeMillis();
		PreparedStatement p = c.prepareStatement("select * from test");
		ResultSet rs = p.executeQuery();
		int x = 1;
		while (rs.next()) {
			int nb = rs.getInt(1);
			Assert.assertTrue(nb == x++);
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
		Assert.assertTrue(sstat.getExecuteCaller().indexOf("MyTest.testStmtWith100000rs") >= 0);
		Assert.assertTrue(sstat.getDuration() > 1500 && sstat.getDuration() < 1700);
		Assert.assertTrue("exec time=" + sstat.getExecutionTime(),
				sstat.getExecutionTime() >= 500 && sstat.getExecutionTime() < 520);
		
		p.close();

		sstat = (StatementStatistics) p;
		Assert.assertEquals(cnt, sstat.getItemCount());
		Assert.assertEquals("select * from test", sstat.getSQL());
		Assert.assertTrue(sstat.getExecuteCaller().indexOf("MyTest.testStmtWith100000rs") >= 0);
		Assert.assertTrue(sstat.getDuration() > 1500 && sstat.getDuration() < 1700);
		Assert.assertTrue("exec time=" + sstat.getExecutionTime(),
				sstat.getExecutionTime() >= 500 && sstat.getExecutionTime() < 520);
		

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertTrue(stat.getStatements().get(0).toString(),
				stat.getStatements().get(0).toString().startsWith("\"select * from test\""));
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.testStmtWith100000rs") >= 0);
		Assert.assertTrue(stat.getDuration() > 1500 && stat.getDuration() < 1700);
		
		c.close();
		long end = (System.currentTimeMillis() - start);
		System.out.println(cnt/(end-1500) + "rs/ms; finished in " + end + "ms");
		// assumption: execute min 90rs/ms but max. 150rs/ms, so overhead is 100000/100 
		
		stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.testStmtWith100000rs") >= 0);
		Assert.assertTrue(stat.getDuration() > 1500 && stat.getDuration() < 1700);

	}

	@Test
	public void minimal() throws Exception {
		 Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
	        Connection c = DriverManager.getConnection(
	                "proxy:mytestdb&rscnt=100&itertime=1000&exectime=500");

	        PreparedStatement p = c.prepareStatement("select * from test");
	        ResultSet rs = p.executeQuery();

	        while (rs.next()) {
	                // read result set
	        }
	        rs.close();
	        p.close();
	        c.close();
	}

	@Test
	public void testStmtWith1000000rs() throws Exception {
		int cnt = 1000000;
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		System.out.println("starting " + cnt + " rs...");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
		long start = System.currentTimeMillis();
		PreparedStatement p = c.prepareStatement("select * from test");
		ResultSet rs = p.executeQuery();
		int x = 1;
		while (rs.next()) {
			int nb = rs.getInt(1);
			Assert.assertTrue(nb == x++);
		}
		rs.close();
		p.close();
		c.close();

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.testStmtWith1000000rs") >= 0);

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " rs; " + cnt/(end-1500) + "rs/ms; finished in " + end + "ms");

		Assert.assertTrue("duration="
				+ cnt/MIN_OVERHEAD
				+ "<" + end + " - 1500 <"
				+ cnt/MAX_OVERHEAD
				+ "; set overhead to " + cnt/(end-1500) + "rs/ms",
				end - 1500 > cnt/MIN_OVERHEAD
			 && end - 1500 < cnt/MAX_OVERHEAD);

		// assumption: execute min 90rs/ms but max. 150rs/ms, so overhead is 100000/100 
		Assert.assertTrue("time is " + end, Math.abs(end-1500) <= cnt/90); // 2167 - 2500
		Assert.assertTrue("time is " + end, Math.abs(end-1500) >= cnt/150);
	}

	@Test
	public void testStmtWith100000rs() throws Exception {
		int cnt = 100000;
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		System.out.println("starting " + cnt + " rs...");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
		long start = System.currentTimeMillis();
		PreparedStatement p = c.prepareStatement("select * from test");
		ResultSet rs = p.executeQuery();
		int x = 1;
		while (rs.next()) {
			int nb = rs.getInt(1);
			Assert.assertTrue(nb == x++);
		}
		rs.close();
		p.close();
		c.close();

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.testStmtWith100000rs") >= 0);

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " rs; " + cnt/(end-1500) + "rs/ms; finished in " + end + "ms");

		Assert.assertTrue("duration="
				+ cnt/MIN_OVERHEAD
				+ "<" + end + " - 1500 <"
				+ cnt/MAX_OVERHEAD
				+ "; set overhead to " + cnt/(end-1500) + "rs/ms",
				end - 1500 > cnt/MIN_OVERHEAD
			 && end - 1500 < cnt/MAX_OVERHEAD);

		// assumption: execute min 90rs/ms but max. 150rs/ms, so overhead is 100000/100 
		Assert.assertTrue("time is " + end, Math.abs(end-1500) <= cnt/90); // 2167 - 2500
		Assert.assertTrue("time is " + end, Math.abs(end-1500) >= cnt/150);
	}

	@Test
	public void testStmtWith10000rs() throws Exception {
		int cnt = 10000;
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		System.out.println("starting " + cnt + " rs...");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
		long start = System.currentTimeMillis();
		PreparedStatement p = c.prepareStatement("select * from test");
		ResultSet rs = p.executeQuery();
		int x = 1;
		while (rs.next()) {
			int nb = rs.getInt(1);
			Assert.assertTrue(nb == x++);
		}
		rs.close();
		p.close();
		c.close();

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " rs; " + cnt/(end-1500) + "rs/ms; finished in " + end + "ms");

		Assert.assertTrue("duration="
				+ cnt/MIN_OVERHEAD
				+ "<" + end + " - 1500 <"
				+ cnt/MAX_OVERHEAD
				+ "; set overhead to " + cnt/(end-1500) + "rs/ms",
				end - 1500 > cnt/MIN_OVERHEAD
			 && end - 1500 < cnt/MAX_OVERHEAD);

		// assumption: execute min 90rs/ms but max. 150rs/ms, so overhead is 100000/100 
		Assert.assertTrue("time is " + end, Math.abs(end-1500) <= cnt/90); // 2167 - 2500
		Assert.assertTrue("time is " + end, Math.abs(end-1500) >= cnt/150);
	}

	@Test
	public void testStmtWith100rs() throws Exception {
		int cnt = 100;
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		System.out.println("starting " + cnt + " rs...");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=" + cnt + "&itertime=1000&exectime=500");
		long start = System.currentTimeMillis();
		PreparedStatement p = c.prepareStatement("select * from test");
		ResultSet rs = p.executeQuery();
		int x = 1;
		while (rs.next()) {
			int nb = rs.getInt(1);
			Assert.assertTrue(nb == x++);
		}
		rs.close();
		p.close();
		c.close();

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(1, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " rs; " + cnt/(end-1500) + "rs/ms; finished in " + end + "ms");

		Assert.assertTrue("duration="
				+ cnt/MIN_OVERHEAD
				+ "<" + end + " - 1500 <"
				+ cnt/10
				+ "; set overhead to " + cnt/(end-1500) + "rs/ms",
				end - 1500 > cnt/MIN_OVERHEAD
			 && end - 1500 < cnt/10);
	}

	@Test
	public void test100StmtWith1rs() throws Exception {
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
		long start = System.currentTimeMillis();
		int cnt = 10000;
		PreparedStatement p;
		for (int i = 0; i < cnt; i++) {
			p = c.prepareStatement("select * from test");
			ResultSet rs = p.executeQuery();
			int x = 1;
			while (rs.next()) {
				int nb = rs.getInt(1);
				Assert.assertTrue(nb == x++);
			}
			rs.close();
			p.close();
	
		}
		c.close();

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " pstmts; " + 1000 * cnt/(end) + "pstmts/ms; finished in " + end + "ms");

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(cnt, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.test100StmtWith1rs") >= 0);


		Assert.assertTrue("duration="
				+ 1000 * cnt/MIN_PST_OVERHEAD
				+ "<" + end + " <"
				+ 1000 * cnt/MAX_PST_OVERHEAD
				+ "; set overhead to " + cnt/(end) + "rs/ms",
				end > 1000 * cnt/MIN_PST_OVERHEAD
			 && end < 1000 * cnt/MAX_PST_OVERHEAD);

	}

	@Test
	public void test10000StmtWith1rs() throws Exception {
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
		long start = System.currentTimeMillis();
		int cnt = 100000;
		PreparedStatement p;
		for (int i = 0; i < cnt; i++) {
			p = c.prepareStatement("select * from test");
			ResultSet rs = p.executeQuery();
			int x = 1;
			while (rs.next()) {
				int nb = rs.getInt(1);
				Assert.assertTrue(nb == x++);
			}
			rs.close();
			p.close();
	
		}
		c.close();

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " pstmts; " + 1000 * cnt/(end) + "pstmts/ms; finished in " + end + "ms");

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(cnt, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.test10000StmtWith1rs") >= 0);


		Assert.assertTrue("duration="
				+ 1000 * cnt/MIN_PST_OVERHEAD
				+ "<" + end + " <"
				+ 1000 * cnt/MAX_PST_OVERHEAD
				+ "; set overhead to " + cnt/(end) + "rs/ms",
				end > 1000 * cnt/MIN_PST_OVERHEAD
			 && end < 1000 * cnt/MAX_PST_OVERHEAD);

	}

	@Test
	public void test10000StmtWith1rs2() throws Exception {
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=1&itertime=0&exectime=0");
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
				Assert.assertTrue(nb == x++);
			}
			rs.close();
			p.close();
	
		}
		c.close();

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " pstmts; " + 1000 * cnt/(end) + "pstmts/ms; finished in " + end + "ms");

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(cnt, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.test10000StmtWith1rs2") >= 0);


		Assert.assertTrue("duration="
				+ 1000 * cnt/MIN_PST_OVERHEAD
				+ "<" + end + " <"
				+ 1000 * cnt/MAX_PST_OVERHEAD
				+ "; set overhead to " + cnt/(end) + "rs/ms",
				end > 1000 * cnt/MIN_PST_OVERHEAD
			 && end < 1000 * cnt/MAX_PST_OVERHEAD);

	}

	@Test
	public void test10000StmtWith1rs3() throws Exception {
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=5&itertime=0&exectime=0");
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
				Assert.assertTrue(nb == x++);
			}
			rs.close();

			StatementStatistics sstat = (StatementStatistics) p;
			Assert.assertEquals(5, sstat.getItemCount());
			Assert.assertEquals("select * from test where x = " + i, sstat.getSQL());

			p.close();
	
		}
		c.close();

		long end = (System.currentTimeMillis() - start);
		System.out.println("reading " + cnt + " pstmts; " + 1000 * cnt/(end) + "pstmts/ms; finished in " + end + "ms");

		ConnectionStatistics stat = (ConnectionStatistics) c;
		Assert.assertEquals(cnt, stat.getItemCount());
		Assert.assertEquals(0, stat.getStatements().size());
		Assert.assertTrue(stat.getCaller().indexOf("MyTest.test10000StmtWith1rs3") >= 0);


		Assert.assertTrue("duration="
				+ 1000 * cnt/MIN_PST_OVERHEAD
				+ "<" + end + " <"
				+ 1000 * cnt/MAX_PST_OVERHEAD
				+ "; set overhead to " + cnt/(end) + "rs/ms",
				end > 1000 * cnt/MIN_PST_OVERHEAD
			 && end < 1000 * cnt/MAX_PST_OVERHEAD);

	}

	@Test
	public void testDump() throws Exception {
		Class.forName("de.luisoft.jdbcspy.DBProxyDriver");
		Connection c = DriverManager.getConnection(
			"proxy:db:xxy&rscnt=1&itertime=0&exectime=0");

		ConnectionStatistics stat = (ConnectionStatistics) c;
		String dump = stat.getProxyConnectionMetaData().dumpStatistics().replaceAll("\r", "+").replaceAll("\n", "+");
		Assert.assertTrue(dump.matches(".*1\\:\\ \\\"select\\ \\*\\ from\\ test\\\"\\ \\(5[012][0-9]ms.*"));
		Assert.assertTrue(dump.matches(".*#stmt=211005;\\ #rs=1425100.*"));
		Assert.assertTrue(dump.matches(".*1\\:\\ #=110005:\\ \"select\\ \\*\\ from\\ test\".*"));
	}
}
