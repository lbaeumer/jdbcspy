package de.luisoft.jdbcspy;

import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.impl.ConnectionDumpListener;
import de.luisoft.jdbcspy.proxy.util.Utils;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void testStmtWith100000rs() {
        ConnectionListener c = new ConnectionDumpListener();
        Utils.setProperty(c, "ConnDumpCloseClassExp", "xxx");
        System.out.println(c.toString());
    }
}
