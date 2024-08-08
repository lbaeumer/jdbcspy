package de.luisoft.jdbcspy;

import de.luisoft.jdbc.testdriver.MyConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Some simple tests.
 */
public class StatementTest extends ProxyPerformanceTest {

    @BeforeEach
    public void setUp() {
        conn = new MyConnection(10000, 1000, 5000);
    }

    @AfterEach
    public void tearDown() throws Exception {
        conn.close();
    }
}
