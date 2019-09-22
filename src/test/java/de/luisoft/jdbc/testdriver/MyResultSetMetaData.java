package de.luisoft.jdbc.testdriver;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MyResultSetMetaData implements ResultSetMetaData {

	public String getCatalogName(int arg0) throws SQLException {
		return null;
	}

	public String getColumnClassName(int arg0) throws SQLException {
		return null;
	}

	public int getColumnCount() throws SQLException {
		return 1;
	}

	public int getColumnDisplaySize(int arg0) throws SQLException {
		return 0;
	}

	public String getColumnLabel(int arg0) throws SQLException {
		return null;
	}

	public String getColumnName(int arg0) throws SQLException {
		return "mycol";
	}

	public int getColumnType(int arg0) throws SQLException {
		return 0;
	}

	public String getColumnTypeName(int arg0) throws SQLException {
		return null;
	}

	public int getPrecision(int arg0) throws SQLException {
		return 0;
	}

	public int getScale(int arg0) throws SQLException {
		return 0;
	}

	public String getSchemaName(int arg0) throws SQLException {
		return null;
	}

	public String getTableName(int arg0) throws SQLException {
		return null;
	}

	public boolean isAutoIncrement(int arg0) throws SQLException {
		return false;
	}

	public boolean isCaseSensitive(int arg0) throws SQLException {
		return false;
	}

	public boolean isCurrency(int arg0) throws SQLException {
		return false;
	}

	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		return false;
	}

	public int isNullable(int arg0) throws SQLException {
		return 0;
	}

	public boolean isReadOnly(int arg0) throws SQLException {
		return false;
	}

	public boolean isSearchable(int arg0) throws SQLException {
		return false;
	}

	public boolean isSigned(int arg0) throws SQLException {
		return false;
	}

	public boolean isWritable(int arg0) throws SQLException {
		return false;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		return null;
	}
}
