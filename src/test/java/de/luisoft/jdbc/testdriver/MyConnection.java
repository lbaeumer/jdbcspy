package de.luisoft.jdbc.testdriver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MyConnection implements Connection {

	private int rscnt;
	private int itertime;
	private int exectime;

	public MyConnection(int rscnt, int itertime, int exectime) {
		this.rscnt = rscnt;
		this.itertime = itertime;
		this.exectime = exectime;
	}

	@Override
	public void clearWarnings() throws SQLException {
	}

	@Override
	public void close() throws SQLException {
	}

	@Override
	public void commit() throws SQLException {
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new MyStatement(rscnt, itertime, exectime);
	}

	@Override
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return new MyStatement(rscnt, itertime, exectime);
	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
		return new MyStatement(rscnt, itertime, exectime);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	@Override
	public String getCatalog() throws SQLException {
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		return 0;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return 0;
	}

	@Override
	public Map getTypeMap() throws SQLException {
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	@Override
	public String nativeSQL(String arg0) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return new MyPreparedStatement(rscnt, itertime, exectime);
	}

	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
	}

	@Override
	public void rollback() throws SQLException {
	}

	@Override
	public void rollback(Savepoint arg0) throws SQLException {
	}

	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {
	}

	@Override
	public void setCatalog(String arg0) throws SQLException {
	}

	@Override
	public void setHoldability(int arg0) throws SQLException {
	}

	@Override
	public void setReadOnly(boolean arg0) throws SQLException {
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return null;
	}

	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		return null;
	}

	@Override
	public void setTransactionIsolation(int arg0) throws SQLException {
	}

	@Override
	public void setTypeMap(Map arg0) throws SQLException {
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
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return null;
	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return null;
	}

	@Override
	public String getClientInfo(String arg0) throws SQLException {
		return null;
	}

	@Override
	public boolean isValid(int arg0) throws SQLException {
		return false;
	}

	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
	}

	@Override
	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
	}

	@Override
	public void setSchema(String schema) throws SQLException {
	}

	@Override
	public String getSchema() throws SQLException {
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return 0;
	}
}
