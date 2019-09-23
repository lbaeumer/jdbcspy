package de.luisoft.jdbc.testdriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class MyStatement implements Statement {

	private int rscnt;
	private int itertime;
	private int exectime;

	public MyStatement(int rscnt, int itertime, int exectime) {
		this.rscnt = rscnt;
		this.itertime = itertime;
		this.exectime = exectime;
	}

	@Override
	public void addBatch(String sql) throws SQLException {

	}

	@Override
	public void cancel() throws SQLException {

	}

	@Override
	public void clearBatch() throws SQLException {

	}

	@Override
	public void clearWarnings() throws SQLException {

	}

	@Override
	public void close() throws SQLException {

	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean execute(String sql) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return new MyResultSet(rscnt, itertime);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {

		try {
			Thread.sleep(exectime);
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {

		return null;
	}

	@Override
	public int getFetchDirection() throws SQLException {

		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {

		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {

		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {

		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {

		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {

		return false;
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {

		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {

		return 0;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {

		return new MyResultSet(rscnt, itertime);
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {

		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {

		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {

		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {

		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {

		return null;
	}

	@Override
	public void setCursorName(String name) throws SQLException {

	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {

	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {

	}

	@Override
	public void setMaxRows(int max) throws SQLException {

	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {

	}

	@Override
	public boolean isWrapperFor(Class arg0) throws SQLException {

		return false;
	}

	@Override
	public Object unwrap(Class arg0) throws SQLException {

		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {

		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {

		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {

	}

	@Override
	public void closeOnCompletion() throws SQLException {

	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {

		return false;
	}
}
