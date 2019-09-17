package de.luisoft.jdbcspy;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.luisoft.jdbc.testdriver.MyConnection;

/**
 * Some simple tests.
 *
 */
public class StatementTest extends AbstractStatementTest {

    Connection conn;
    
    public StatementTest(String name) {
        super(name);
    	conn = new MyConnection(10000, 1000, 5000);
    }

    public static Test suite() {
        return new TestSuite(StatementTest.class);
    }

    protected void setUp() throws Exception {
    	setUp(conn);
    }

    protected void tearDown() throws Exception {
    	tearDown(conn);
    }
}
