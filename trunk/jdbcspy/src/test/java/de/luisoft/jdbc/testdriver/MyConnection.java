package de.luisoft.jdbc.testdriver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

public class MyConnection implements Connection {

	private int rscnt;
	private int itertime;
	private int exectime;

	public MyConnection(int rscnt, int itertime, int exectime) {
		this.rscnt = rscnt;
		this.itertime = itertime;
		this.exectime = exectime;
	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub
	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub
	}

	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return new MyStatement(rscnt, itertime, exectime);
	}

	public Statement createStatement(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return new MyStatement(rscnt, itertime, exectime);
	}

	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return new MyStatement(rscnt, itertime, exectime);
	}

	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public String nativeSQL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void rollback(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAutoCommit(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCatalog(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setHoldability(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setReadOnly(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTransactionIsolation(int arg0) throws SQLException {
		// TODO Auto-generated method stub
	}

	public void setTypeMap(Map arg0) throws SQLException {
		// TODO Auto-generated method stub
	}
}
