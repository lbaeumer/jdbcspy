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
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class MyResultSet implements ResultSet {

    private final int rscnt;
    private final int itertime;

    private int count;

    public MyResultSet(int rscnt, int itertime) {
        this.rscnt = rscnt;
        this.itertime = itertime;
    }

    @Override
    public boolean absolute(int arg0) {

        return false;
    }

    @Override
    public void afterLast() {

    }

    @Override
    public void beforeFirst() {

    }

    @Override
    public void cancelRowUpdates() {

    }

    @Override
    public void clearWarnings() {

    }

    @Override
    public void close() {

    }

    @Override
    public void deleteRow() {

    }

    @Override
    public int findColumn(String arg0) {

        return 0;
    }

    @Override
    public boolean first() {

        return false;
    }

    @Override
    public Array getArray(int arg0) {

        return null;
    }

    @Override
    public Array getArray(String arg0) {

        return null;
    }

    @Override
    public InputStream getAsciiStream(int arg0) {

        return null;
    }

    @Override
    public InputStream getAsciiStream(String arg0) {

        return null;
    }

    @Override
    public BigDecimal getBigDecimal(int arg0, int arg1) {

        return null;
    }

    @Override
    public BigDecimal getBigDecimal(int arg0) {

        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String arg0, int arg1) {

        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String arg0) {

        return null;
    }

    @Override
    public InputStream getBinaryStream(int arg0) {

        return null;
    }

    @Override
    public InputStream getBinaryStream(String arg0) {

        return null;
    }

    @Override
    public Blob getBlob(int arg0) {

        return null;
    }

    @Override
    public Blob getBlob(String arg0) {

        return null;
    }

    @Override
    public boolean getBoolean(int arg0) {

        return false;
    }

    @Override
    public boolean getBoolean(String arg0) {

        return false;
    }

    @Override
    public byte getByte(int arg0) {

        return 0;
    }

    @Override
    public byte getByte(String arg0) {

        return 0;
    }

    @Override
    public byte[] getBytes(int arg0) {

        return null;
    }

    @Override
    public byte[] getBytes(String arg0) {

        return null;
    }

    @Override
    public Reader getCharacterStream(int arg0) {

        return null;
    }

    @Override
    public Reader getCharacterStream(String arg0) {

        return null;
    }

    @Override
    public Clob getClob(int arg0) {

        return null;
    }

    @Override
    public Clob getClob(String arg0) {

        return null;
    }

    @Override
    public int getConcurrency() {

        return 0;
    }

    @Override
    public String getCursorName() {

        return null;
    }

    @Override
    public Date getDate(int arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Date getDate(int arg0) {

        return null;
    }

    @Override
    public Date getDate(String arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Date getDate(String arg0) {

        return null;
    }

    @Override
    public double getDouble(int arg0) {

        return 0;
    }

    @Override
    public double getDouble(String arg0) {

        return 0;
    }

    @Override
    public int getFetchDirection() {

        return 0;
    }

    @Override
    public void setFetchDirection(int arg0) {

    }

    @Override
    public int getFetchSize() {

        return 0;
    }

    @Override
    public void setFetchSize(int arg0) {

    }

    @Override
    public float getFloat(int arg0) {

        return 0;
    }

    @Override
    public float getFloat(String arg0) {

        return 0;
    }

    @Override
    public int getInt(int arg0) {

        return count;
    }

    @Override
    public int getInt(String arg0) {

        return count;
    }

    @Override
    public long getLong(int arg0) {

        return count;
    }

    @Override
    public long getLong(String arg0) {

        return count;
    }

    @Override
    public ResultSetMetaData getMetaData() {

        return new MyResultSetMetaData();
    }

    @Override
    public Object getObject(int arg0, Map arg1) {

        return null;
    }

    @Override
    public Object getObject(int arg0) {

        return null;
    }

    @Override
    public Object getObject(String arg0, Map arg1) {

        return null;
    }

    @Override
    public Object getObject(String arg0) {

        return null;
    }

    @Override
    public Ref getRef(int arg0) {

        return null;
    }

    @Override
    public Ref getRef(String arg0) {

        return null;
    }

    @Override
    public int getRow() {

        return 0;
    }

    @Override
    public short getShort(int arg0) {

        return 0;
    }

    @Override
    public short getShort(String arg0) {

        return 0;
    }

    @Override
    public Statement getStatement() {

        return null;
    }

    @Override
    public String getString(int arg0) {

        return null;
    }

    @Override
    public String getString(String arg0) {

        return null;
    }

    @Override
    public Time getTime(int arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Time getTime(int arg0) {

        return null;
    }

    @Override
    public Time getTime(String arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Time getTime(String arg0) {

        return null;
    }

    @Override
    public Timestamp getTimestamp(int arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Timestamp getTimestamp(int arg0) {

        return null;
    }

    @Override
    public Timestamp getTimestamp(String arg0, Calendar arg1) {

        return null;
    }

    @Override
    public Timestamp getTimestamp(String arg0) {

        return null;
    }

    @Override
    public int getType() {

        return 0;
    }

    @Override
    public InputStream getUnicodeStream(int arg0) {

        return null;
    }

    @Override
    public InputStream getUnicodeStream(String arg0) {

        return null;
    }

    @Override
    public URL getURL(int arg0) {

        return null;
    }

    @Override
    public URL getURL(String arg0) {

        return null;
    }

    @Override
    public SQLWarning getWarnings() {

        return null;
    }

    @Override
    public void insertRow() {

    }

    @Override
    public boolean isAfterLast() {

        return false;
    }

    @Override
    public boolean isBeforeFirst() {

        return false;
    }

    @Override
    public boolean isFirst() {

        return false;
    }

    @Override
    public boolean isLast() {

        return false;
    }

    @Override
    public boolean last() {

        return false;
    }

    @Override
    public void moveToCurrentRow() {

    }

    @Override
    public void moveToInsertRow() {

    }

    @Override
    public boolean next() {

        if (count == rscnt - 1) {
            try {
                Thread.sleep(itertime);
            } catch (Exception ignored) {
            }
        }
        if (count >= rscnt) {
            return false;
        } else {
            count++;
            return true;
        }
    }

    @Override
    public boolean previous() {

        return false;
    }

    @Override
    public void refreshRow() {

    }

    @Override
    public boolean relative(int arg0) {

        return false;
    }

    @Override
    public boolean rowDeleted() {

        return false;
    }

    @Override
    public boolean rowInserted() {

        return false;
    }

    @Override
    public boolean rowUpdated() {

        return false;
    }

    @Override
    public void updateArray(int arg0, Array arg1) {

    }

    @Override
    public void updateArray(String arg0, Array arg1) {

    }

    @Override
    public void updateAsciiStream(int arg0, InputStream arg1, int arg2) {

    }

    @Override
    public void updateAsciiStream(String arg0, InputStream arg1, int arg2) {

    }

    @Override
    public void updateBigDecimal(int arg0, BigDecimal arg1) {

    }

    @Override
    public void updateBigDecimal(String arg0, BigDecimal arg1) {

    }

    @Override
    public void updateBinaryStream(int arg0, InputStream arg1, int arg2) {

    }

    @Override
    public void updateBinaryStream(String arg0, InputStream arg1, int arg2) {

    }

    @Override
    public void updateBlob(int arg0, Blob arg1) {

    }

    @Override
    public void updateBlob(String arg0, Blob arg1) {

    }

    @Override
    public void updateBoolean(int arg0, boolean arg1) {

    }

    @Override
    public void updateBoolean(String arg0, boolean arg1) {

    }

    @Override
    public void updateByte(int arg0, byte arg1) {

    }

    @Override
    public void updateByte(String arg0, byte arg1) {

    }

    @Override
    public void updateBytes(int arg0, byte[] arg1) {

    }

    @Override
    public void updateBytes(String arg0, byte[] arg1) {

    }

    @Override
    public void updateCharacterStream(int arg0, Reader arg1, int arg2) {

    }

    @Override
    public void updateCharacterStream(String arg0, Reader arg1, int arg2) {

    }

    @Override
    public void updateClob(int arg0, Clob arg1) {

    }

    @Override
    public void updateClob(String arg0, Clob arg1) {

    }

    @Override
    public void updateDate(int arg0, Date arg1) {

    }

    @Override
    public void updateDate(String arg0, Date arg1) {

    }

    @Override
    public void updateDouble(int arg0, double arg1) {

    }

    @Override
    public void updateDouble(String arg0, double arg1) {

    }

    @Override
    public void updateFloat(int arg0, float arg1) {

    }

    @Override
    public void updateFloat(String arg0, float arg1) {

    }

    @Override
    public void updateInt(int arg0, int arg1) {

    }

    @Override
    public void updateInt(String arg0, int arg1) {

    }

    @Override
    public void updateLong(int arg0, long arg1) {

    }

    @Override
    public void updateLong(String arg0, long arg1) {

    }

    @Override
    public void updateNull(int arg0) {

    }

    @Override
    public void updateNull(String arg0) {

    }

    @Override
    public void updateObject(int arg0, Object arg1, int arg2) {

    }

    @Override
    public void updateObject(int arg0, Object arg1) {

    }

    @Override
    public void updateObject(String arg0, Object arg1, int arg2) {

    }

    @Override
    public void updateObject(String arg0, Object arg1) {

    }

    @Override
    public void updateRef(int arg0, Ref arg1) {

    }

    @Override
    public void updateRef(String arg0, Ref arg1) {

    }

    @Override
    public void updateRow() {

    }

    @Override
    public void updateShort(int arg0, short arg1) {

    }

    @Override
    public void updateShort(String arg0, short arg1) {

    }

    @Override
    public void updateString(int arg0, String arg1) {

    }

    @Override
    public void updateString(String arg0, String arg1) {

    }

    @Override
    public void updateTime(int arg0, Time arg1) {

    }

    @Override
    public void updateTime(String arg0, Time arg1) {

    }

    @Override
    public void updateTimestamp(int arg0, Timestamp arg1) {

    }

    @Override
    public void updateTimestamp(String arg0, Timestamp arg1) {

    }

    @Override
    public boolean wasNull() {

        return false;
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
    public int getHoldability() {

        return 0;
    }

    @Override
    public Reader getNCharacterStream(int arg0) {

        return null;
    }

    @Override
    public Reader getNCharacterStream(String arg0) {

        return null;
    }

    @Override
    public NClob getNClob(int arg0) {

        return null;
    }

    @Override
    public NClob getNClob(String arg0) {

        return null;
    }

    @Override
    public String getNString(int arg0) {

        return null;
    }

    @Override
    public String getNString(String arg0) {

        return null;
    }

    @Override
    public RowId getRowId(int arg0) {

        return null;
    }

    @Override
    public RowId getRowId(String arg0) {

        return null;
    }

    @Override
    public SQLXML getSQLXML(int arg0) {

        return null;
    }

    @Override
    public SQLXML getSQLXML(String arg0) {

        return null;
    }

    @Override
    public boolean isClosed() {

        return false;
    }

    @Override
    public void updateAsciiStream(int arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateAsciiStream(int arg0, InputStream arg1) {

    }

    @Override
    public void updateAsciiStream(String arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateAsciiStream(String arg0, InputStream arg1) {

    }

    @Override
    public void updateBinaryStream(int arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateBinaryStream(int arg0, InputStream arg1) {

    }

    @Override
    public void updateBinaryStream(String arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateBinaryStream(String arg0, InputStream arg1) {

    }

    @Override
    public void updateBlob(int arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateBlob(int arg0, InputStream arg1) {

    }

    @Override
    public void updateBlob(String arg0, InputStream arg1, long arg2) {

    }

    @Override
    public void updateBlob(String arg0, InputStream arg1) {

    }

    @Override
    public void updateCharacterStream(int arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateCharacterStream(int arg0, Reader arg1) {

    }

    @Override
    public void updateCharacterStream(String arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateCharacterStream(String arg0, Reader arg1) {

    }

    @Override
    public void updateClob(int arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateClob(int arg0, Reader arg1) {

    }

    @Override
    public void updateClob(String arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateClob(String arg0, Reader arg1) {

    }

    @Override
    public void updateNCharacterStream(int arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateNCharacterStream(int arg0, Reader arg1) {

    }

    @Override
    public void updateNCharacterStream(String arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateNCharacterStream(String arg0, Reader arg1) {

    }

    @Override
    public void updateNClob(int arg0, NClob arg1) {

    }

    @Override
    public void updateNClob(int arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateNClob(int arg0, Reader arg1) {

    }

    @Override
    public void updateNClob(String arg0, NClob arg1) {

    }

    @Override
    public void updateNClob(String arg0, Reader arg1, long arg2) {

    }

    @Override
    public void updateNClob(String arg0, Reader arg1) {

    }

    @Override
    public void updateNString(int arg0, String arg1) {

    }

    @Override
    public void updateNString(String arg0, String arg1) {

    }

    @Override
    public void updateRowId(int arg0, RowId arg1) {

    }

    @Override
    public void updateRowId(String arg0, RowId arg1) {

    }

    @Override
    public void updateSQLXML(int arg0, SQLXML arg1) {

    }

    @Override
    public void updateSQLXML(String arg0, SQLXML arg1) {

    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) {

        return null;
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) {

        return null;
    }
}
