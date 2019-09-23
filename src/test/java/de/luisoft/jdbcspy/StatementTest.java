package de.luisoft.jdbcspy;

import org.junit.After;
import org.junit.Before;

import de.luisoft.jdbc.testdriver.MyConnection;

/**
 * Some simple tests.
 *
 */
public class StatementTest extends AbstractStatementTest {

	@Before
	public void setUp() throws Exception {
		conn = new MyConnection(10000, 1000, 5000);
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}
}
