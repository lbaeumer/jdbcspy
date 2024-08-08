package de.luisoft.jdbc.testdriver;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals(i, 100);
        assertTrue(afterExec - start >= 3000 && afterExec - start < 3050, "t=" + (afterExec - start));
        assertTrue(afterIter - afterExec >= 1000 && afterIter - afterExec < 1050, "t=" + (afterIter - afterExec));
    }
}
