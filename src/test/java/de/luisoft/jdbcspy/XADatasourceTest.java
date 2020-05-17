package de.luisoft.jdbcspy;

import de.luisoft.jdbcspy.proxy.ProxyConnection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.XAConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Some simple tests.
 */
public class XADatasourceTest {

    @BeforeClass
    public static void setUp() throws SQLException {
        String databaseURL = "jdbc:derby:booksdb;create=true";

        Connection conn = DriverManager.getConnection(databaseURL);
        Statement statement = conn.createStatement();

        String sql = "CREATE TABLE book (book_id int primary key, title varchar(128))";
        try {
            statement.execute(sql);


            sql = "INSERT INTO book VALUES (1, 'Effective Java'), (2, 'Core Java')";
            statement.execute(sql);
        } catch (SQLException e) {
            Assert.assertEquals(e.getSQLState(), e.getSQLState(), "X0Y32");
        }
    }

    @Test
    public void testDumpXA() throws Exception {

        ProxyXADatasource proxy = new ProxyXADatasource();
        //EmbeddedXADataSource proxy = new EmbeddedXADataSource();
        //org.apache.derby.jdbc.EmbedXAConnection c;


        //org.apache.derby.jdbc.EmbedPooledConnection c;
        javax.sql.PooledConnection p;
        org.apache.derby.iapi.jdbc.BrokeredConnection42 f;
        proxy.setDatabaseName("booksdb");

        ProxyConnection c = (ProxyConnection) proxy.getXAConnection();
        Connection con = c.getConnection();
        System.out.println("con=" + con);
        con.isReadOnly();
        PreparedStatement s = con.prepareStatement("select * from book");
        ResultSet rs = s.executeQuery();

        int i = 0;
        while (rs.next()) {
            i++;
        }
        Assert.assertEquals(i, 2);
        rs.close();
        s.close();
        con.close();
        System.out.println("c=" + c);

        // again
        con = c.getConnection();
        System.out.println("con=" + con);
        con.isReadOnly();
        i=0;
         s = con.prepareStatement("select * from book");
        rs = s.executeQuery();

        while (rs.next()) {
            i++;
        }
        Assert.assertEquals(i, 2);

        Thread.sleep(40000);
        rs.close();
        s.close();
        con.close();
        System.out.println("c=" + c);

        c.close();


        System.out.println("dump" + c.dump());
    }

}
