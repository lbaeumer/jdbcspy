package de.luisoft.jdbc.testdriver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MyConnection implements Connection {

    private final int rscnt;
    private final int itertime;
    private final int exectime;

    public MyConnection(int rscnt, int itertime, int exectime) {
        this.rscnt = rscnt;
        this.itertime = itertime;
        this.exectime = exectime;
    }

    @Override
    public void clearWarnings() {
    }

    @Override
    public void close() {
    }

    @Override
    public void commit() {
    }

    @Override
    public Statement createStatement() {
        return new MyStatement(rscnt, itertime, exectime);
    }

    @Override
    public Statement createStatement(int arg0, int arg1) {
        return new MyStatement(rscnt, itertime, exectime);
    }

    @Override
    public Statement createStatement(int arg0, int arg1, int arg2) {
        return new MyStatement(rscnt, itertime, exectime);
    }

    @Override
    public boolean getAutoCommit() {
        return false;
    }

    @Override
    public void setAutoCommit(boolean arg0) {
    }

    @Override
    public String getCatalog() {
        return null;
    }

    @Override
    public void setCatalog(String arg0) {
    }

    @Override
    public int getHoldability() {
        return 0;
    }

    @Override
    public void setHoldability(int arg0) {
    }

    @Override
    public DatabaseMetaData getMetaData() {
        return null;
    }

    @Override
    public int getTransactionIsolation() {
        return TRANSACTION_NONE;
    }

    @Override
    public void setTransactionIsolation(int arg0) {
    }

    @Override
    public Map<String, Class<?>> getTypeMap() {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> arg0) {
    }

    @Override
    public SQLWarning getWarnings() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setReadOnly(boolean arg0) {
    }

    @Override
    public String nativeSQL(String arg0) {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String arg0) {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2) {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String arg0) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int[] arg1) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, String[] arg1) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) {
        return new MyPreparedStatement(rscnt, itertime, exectime);
    }

    @Override
    public void releaseSavepoint(Savepoint arg0) {
    }

    @Override
    public void rollback() {
    }

    @Override
    public void rollback(Savepoint arg0) {
    }

    @Override
    public Savepoint setSavepoint() {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String arg0) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class arg0) {
        return false;
    }

    @Override
    public Object unwrap(Class arg0) {
        return null;
    }

    @Override
    public Array createArrayOf(String arg0, Object[] arg1) {
        return null;
    }

    @Override
    public Blob createBlob() {
        return null;
    }

    @Override
    public Clob createClob() {
        return null;
    }

    @Override
    public NClob createNClob() {
        return null;
    }

    @Override
    public SQLXML createSQLXML() {
        return null;
    }

    @Override
    public Struct createStruct(String arg0, Object[] arg1) {
        return null;
    }

    @Override
    public Properties getClientInfo() {
        return null;
    }

    @Override
    public void setClientInfo(Properties arg0) {
    }

    @Override
    public String getClientInfo(String arg0) {
        return null;
    }

    @Override
    public boolean isValid(int arg0) {
        return false;
    }

    @Override
    public void setClientInfo(String arg0, String arg1) {
    }

    @Override
    public String getSchema() {
        return null;
    }

    @Override
    public void setSchema(String schema) {
    }

    @Override
    public void abort(Executor executor) {
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) {
    }

    @Override
    public int getNetworkTimeout() {
        return 0;
    }
}
