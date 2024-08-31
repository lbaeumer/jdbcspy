package de.luisoft.jdbc.test;

import de.luisoft.jdbcspy.proxy.ConnectionFactory;
import de.luisoft.jdbcspy.proxy.ConnectionStatistics;
import de.luisoft.jdbcspy.vendor.DerbyProxyXADatasource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Some simple tests.
 */
public class XADatasourceTest {

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
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

    @AfterEach
    public void tearDown() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String databaseURL = "jdbc:derby:booksdb";

        Connection conn = DriverManager.getConnection(databaseURL);
        Statement statement = conn.createStatement();

        String sql = "DROP TABLE book";
        statement.execute(sql);
    }

    @Test
    public void minimalXA() throws Exception {

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        XADataSource datasource = new DerbyProxyXADatasource();
        ((DerbyProxyXADatasource) datasource).setDatabaseName("booksdb");

        XAConnection xaConnection = datasource.getXAConnection();
        Connection con = xaConnection.getConnection();
        PreparedStatement s = con.prepareStatement("select * from book");
        ResultSet rs = s.executeQuery();

        int i = 0;
        while (rs.next()) {
            i++;
        }

        assertEquals(i, 2);
        rs.close();
        s.close();
        con.close();

        // again
        con = xaConnection.getConnection();
        i = 0;
        s = con.prepareStatement("select book_id, title from book");
        rs = s.executeQuery();

        while (rs.next()) {
            i++;
        }
        assertEquals(i, 2);

        rs.close();
        s.close();

        ConnectionStatistics stats = (ConnectionStatistics) con;
        assertEquals(stats.getStatements().size(), 1);
        assertEquals(stats.getItemCount(), 1);
        assertTrue(stats.getCaller().contains("getConnection:-1|XADatasourceTest.minimalXA:"),
                stats.getCaller());

        con.close();

        stats = (ConnectionStatistics) con;
        assertEquals(stats.getStatements().size(), 0);
        assertEquals(stats.getItemCount(), 1);
        assertTrue(stats.getCaller().contains("getConnection:-1|XADatasourceTest.minimalXA:"),
                stats.getCaller());

        System.out.println("connection dump:\n"
                + ConnectionFactory.dumpStatistics());
    }

    @Test
    public void minimalDriverURL() throws Exception {

        Class.forName("de.luisoft.jdbcspy.vendor.DerbyProxyDriver");

        System.out.println("starting ...");
        Connection c = DriverManager.getConnection("proxy:jdbc:derby:booksdb");
        PreparedStatement p = c.prepareStatement("select * from book");
        ResultSet rs = p.executeQuery();
        int x = 0;
        while (rs.next()) {
            int nb = rs.getInt(1);
            System.out.println(nb + "/" + rs.getString(2));
            assertEquals(nb, ++x);
        }
        rs.close();

        assertEquals(x, 2);
    }
}
