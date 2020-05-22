package de.luisoft.jdbcspy;

import de.luisoft.jdbc.testdriver.MyConnection;
import org.junit.After;
import org.junit.Before;

/**
 * Some simple tests.
 */
public class StatementTest extends ProxyPerformanceTest {

    @Before
    public void setUp() {
        conn = new MyConnection(10000, 1000, 5000);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
}
