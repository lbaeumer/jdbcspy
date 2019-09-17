package de.luisoft.jdbcspy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;
import de.luisoft.jdbcspy.ProxyConnection;
import de.luisoft.jdbcspy.proxy.StatementStatistics;

/**
 * Some simple tests.
 *
 */
public abstract class AbstractStatementTest extends TestCase {

    private Connection conn;
    private static final boolean debug = false;
    
    public AbstractStatementTest(String name) {
        super(name);
    }

    protected void setUp(Connection conn) throws Exception {
    	this.conn = conn;
    }

    protected void tearDown(Connection conn) throws Exception {
    	this.conn = conn;
    	conn.close();
    }

	public void testExecute() throws Exception {
		int cnt=10;
    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
        	Statement s = conn.createStatement();         
            boolean b = s.execute("select * from x");
        	s.close();
        	
        	if ((debug || i < 2 || i> cnt-3)
        			&& conn instanceof ProxyConnection) {
        		StatementStatistics st = (StatementStatistics) s;
        		System.out.println(i + ":" + s);
        		assertTrue("exec time must be 1000 != "
        				+ st.getExecutionTime(),
        				Math.abs(st.getExecutionTime() - 1000) < 10);
        		assertTrue("#items must be 0!=" + st.getItemCount(),
        				st.getItemCount() == 0);
        		assertTrue("duration must be equal execution time " 
        				+ st.getExecutionTime() + "!=" + st.getDuration(),
        				st.getExecutionTime() == st.getDuration());
        		assertTrue(b);
        	}
    	}

    	long end = System.currentTimeMillis();
        System.out.println("execute: total time=" + (end-start) + "ms");
       	System.out.println(1000*(end-start-cnt*1000)/cnt + "ms/1000stmts");
    }

	public void testExecuteQuery() throws Exception {
		int cnt=10;
		int sleep = 100;
		int rsCount = 100;
    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
        	Statement s = conn.createStatement();         
            ResultSet rs = s.executeQuery(
            		"select * from x");
            while (rs.next()) {
            	rs.getInt(0);
            }
            rs.close();
        	s.close();
        	
        	if ((debug || i < 2 || i> cnt-3)
        			&& conn instanceof ProxyConnection) {
        		StatementStatistics st = (StatementStatistics) s;
        		System.out.println(i + ":" + s);
        		assertTrue("exec time must be 1000 !="
        				+ st.getExecutionTime(),
        				Math.abs(st.getExecutionTime() - 1000) < 10);
        		assertTrue("#items must be " + rsCount
        				+ "!=" + st.getItemCount(),
        				st.getItemCount() == rsCount);
        		assertTrue("duration must be greater execution time " 
        				+ st.getExecutionTime() + "!=" + st.getDuration(),
        				st.getDuration() - st.getExecutionTime() <= 7);
        	}
    	}

    	long end = System.currentTimeMillis();
        System.out.println("executeQuery: total time=" + (end-start) + "ms");
       	System.out.println(1000*(end-start-cnt*sleep)/cnt + "ms/1000stmts");
    }
	public void testExecuteQueryRSDelay() throws Exception {
		int cnt=10;
		int sleep = 100;
		int rsCount = 100;
		int delay = 5;
    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
        	Statement s = conn.createStatement();         
            ResultSet rs = s.executeQuery(
            		"select * from x where time="
            		+ sleep + " and size="
            		+ rsCount + " and delay=" + delay + " ");
            while (rs.next()) {
            	rs.getInt(0);
            }
            rs.close();
        	s.close();
        	
        	if ((debug || i < 2 || i> cnt-3)
        			&& conn instanceof ProxyConnection) {
        		StatementStatistics st = (StatementStatistics) s;
        		System.out.println(i + ":" + s);
        		assertTrue("exec time must be " + sleep + "!="
        				+ st.getExecutionTime(),
        				Math.abs(st.getExecutionTime() - sleep) < 10);
        		assertTrue("#items must be " + rsCount
        				+ "!=" + st.getItemCount(),
        				st.getItemCount() == rsCount);
        		assertTrue("duration must be greater execution time " 
        				+ st.getExecutionTime() + "!=" + st.getDuration(),
        				st.getDuration() - (st.getExecutionTime()+cnt*delay*rsCount) <= 5);
        	}
    	}

    	long end = System.currentTimeMillis();
        System.out.println("executeQuery: total time=" + (end-start) + "ms");
       	System.out.println(1000*(end-start-cnt*sleep-cnt*rsCount*delay)/cnt + "ms/1000stmts");
    }

	public void testExecuteRsQuery() throws Exception {
		int cnt=1;
		int rsCount = 100000;
    	long start = System.currentTimeMillis();

    	for (int i = 0; i < cnt; i++) {
        	Statement s = conn.createStatement();         
            ResultSet rs = s.executeQuery(
            		"speed * from x where size="
            		+ rsCount + " ");
            while (rs.next()) {
            	rs.getInt(0);
            }
            rs.close();
        	s.close();
        	
        	if ((debug || i < 2 || i> cnt-3)
        			&& conn instanceof ProxyConnection) {
        		StatementStatistics st = (StatementStatistics) s;
        		System.out.println(i + ":" + s);
        		assertTrue("#items must be " + rsCount
        				+ "!=" + st.getItemCount(),
        				st.getItemCount() == rsCount);
        	}
    	}

    	long end = System.currentTimeMillis();
        System.out.println("executeRsQuery: total time=" + (end-start) + "ms");
       	System.out.println(10000*(end-start)/rsCount + "ms/10000rs");
    }
}
