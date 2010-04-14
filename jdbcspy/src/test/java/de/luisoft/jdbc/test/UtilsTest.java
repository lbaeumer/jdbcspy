package de.luisoft.jdbc.test;

import junit.framework.TestCase;
import de.luisoft.db.proxy.listener.ConnectionListener;
import de.luisoft.db.proxy.listener.impl.ConnectionDumpListener;
import de.luisoft.db.proxy.util.Utils;

public class UtilsTest extends TestCase {

	public UtilsTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void testStmtWith100000rs() throws Exception {
		ConnectionListener c = new ConnectionDumpListener();
		Utils.setProperty(c, "ConnDumpCloseClassExp", "xxx");
		System.out.println(c.toString());
	}
}
