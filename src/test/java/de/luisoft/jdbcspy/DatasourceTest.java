package de.luisoft.jdbcspy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Some simple tests.
 */
public class DatasourceTest {

    @BeforeEach
    public void setUp() throws SQLException {
        String databaseURL = "jdbc:derby:booksdb;create=true";

        Connection conn = DriverManager.getConnection(databaseURL);
        Statement statement = conn.createStatement();

        String sql = "CREATE TABLE book (book_id int primary key, title varchar(128))";
        try {
            statement.execute(sql);


            sql = "INSERT INTO book VALUES (1, 'Effective Java'), (2, 'Core Java')";
            statement.execute(sql);
        } catch (SQLException e) {
            assertEquals(e.getSQLState(), e.getSQLState(), "X0Y32");
        }
    }

    @Test
    public void testDump() throws Exception {

        ProxyDatasource proxy = new ProxyDatasource();
        proxy.setDatabaseName("booksdb");

        Connection c = proxy.getConnection();
        PreparedStatement s = c.prepareStatement("select * from book");
        ResultSet rs = s.executeQuery();

        int i = 0;
        while (rs.next()) {
            i++;
        }
        assertEquals(i, 2);
        rs.close();
        s.close();
        c.close();
        System.out.println("c=" + c);
    }
}
