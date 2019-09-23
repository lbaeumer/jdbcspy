package de.luisoft.jdbc.testdriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDriver implements Driver {

	static {
		try {
			System.out.println("register MyDriver");
			DriverManager.registerDriver(new MyDriver());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean acceptsURL(String arg0) throws SQLException {
		return true;
	}

	@Override
	public Connection connect(String url, Properties arg1) throws SQLException {
		int rscnt = 100;
		int itertime = 1000;
		int exectime = 1000;
		if (url.indexOf("itertime=") > 0) {
			String itertimestr = url.substring(url.indexOf("itertime=") + 9);
			if (itertimestr.indexOf("&") > 0) {
				itertimestr = itertimestr.substring(0, itertimestr.indexOf("&"));
			}
			itertime = Integer.parseInt(itertimestr);
		}

		if (url.indexOf("exectime=") > 0) {
			String exectimestr = url.substring(url.indexOf("exectime=") + 9);
			if (exectimestr.indexOf("&") > 0) {
				exectimestr = exectimestr.substring(0, exectimestr.indexOf("&"));
			}
			exectime = Integer.parseInt(exectimestr);
		}

		if (url.indexOf("rscnt=") > 0) {
			String rsstr = url.substring(url.indexOf("rscnt=") + 6);
			if (rsstr.indexOf("&") > 0) {
				rsstr = rsstr.substring(0, rsstr.indexOf("&"));
			}
			rscnt = Integer.parseInt(rsstr);
		}

		Connection c = new MyConnection(rscnt, itertime, exectime);
		return c;
	}

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		return true;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
