package de.luisoft.jdbcspy;

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

        proxy.setDatabaseName("booksdb");

        XAConnection c = proxy.getXAConnection();
        PreparedStatement s = c.getConnection().prepareStatement("select * from book");
        ResultSet rs = s.executeQuery();

        int i = 0;
        while (rs.next()) {
            i++;
        }
        Assert.assertEquals(i, 2);
        rs.close();
        s.close();
        c.close();
        System.out.println("c=" + c);
    }

}
