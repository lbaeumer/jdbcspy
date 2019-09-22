package de.luisoft.jdbc.test;

import org.junit.Test;

import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.impl.ConnectionDumpListener;
import de.luisoft.jdbcspy.proxy.util.Utils;

public class UtilsTest {

	@Test
	public void testStmtWith100000rs() throws Exception {
		ConnectionListener c = new ConnectionDumpListener();
		Utils.setProperty(c, "ConnDumpCloseClassExp", "xxx");
		System.out.println(c.toString());
	}
}
