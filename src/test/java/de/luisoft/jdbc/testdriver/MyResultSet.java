package de.luisoft.jdbc.testdriver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class MyResultSet implements ResultSet {

	private int rscnt;
	private int itertime;

	private int count;

	public MyResultSet(int rscnt, int itertime) {
		this.rscnt = rscnt;
		this.itertime = itertime;
	}

	public boolean absolute(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public int findColumn(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Array getArray(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getAsciiStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getBinaryStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getBoolean(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getBoolean(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public double getDouble(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDouble(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return count;
	}

	public int getInt(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return count;
	}

	public long getLong(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return count;
	}

	public long getLong(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return count;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return new MyResultSetMetaData();
	}

	public Object getObject(int arg0, Map arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String arg0, Map arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public InputStream getUnicodeStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getUnicodeStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		if (count == rscnt - 1) {
			try {
				Thread.sleep(itertime);
			} catch (Exception e) {
			}
		}
		if (count >= rscnt) {
			return false;
		} else {
			count++;
			return true;
		}
	}

	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean relative(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateArray(int arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateArray(String arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNull(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNull(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRef(String arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
