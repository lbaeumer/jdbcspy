package de.luisoft.jdbcspy;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Some simple tests.
 */
public class DerbyTest {

    @Test
    public void testDump2() throws Exception {

        String databaseURL = "jdbc:derby:booksdb;create=true";

        Connection conn = DriverManager.getConnection(databaseURL);
        Statement statement = conn.createStatement();

        String sql = "CREATE TABLE book (book_id int primary key, title varchar(128))";
        statement.execute(sql);


        sql = "INSERT INTO book VALUES (1, 'Effective Java'), (2, 'Core Java')";
        statement.execute(sql);

        ProxyDatasource proxy = new ProxyDatasource();

        proxy.setDatabaseName("booksdb");
        Connection c = proxy.getConnection();
        PreparedStatement s = c.prepareStatement("select * from book");
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
