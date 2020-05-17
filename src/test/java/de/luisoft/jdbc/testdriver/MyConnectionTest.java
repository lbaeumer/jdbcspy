package de.luisoft.jdbc.testdriver;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Some simple tests.
 */
public class MyConnectionTest {

    private static final boolean debug = false;
    protected Connection conn = new MyConnection(
            100, 1000, 3000);

    @Test
    public void testExecute() throws Exception {

        long start = System.currentTimeMillis();
        PreparedStatement s = conn.prepareStatement("select * from x");

        ResultSet rs = s.executeQuery();
        long afterExec = System.currentTimeMillis();
        int i = 0;
        while (rs.next()) {
            i++;
        }
        long afterIter = System.currentTimeMillis();
        rs.close();
        s.close();

        Assert.assertEquals(i, 100);
        Assert.assertTrue("t=" + (afterExec - start), afterExec - start >= 3000 && afterExec - start < 3050);
        Assert.assertTrue("t=" + (afterIter - afterExec), afterIter - afterExec >= 1000 && afterIter - afterExec < 1050);
    }
}
