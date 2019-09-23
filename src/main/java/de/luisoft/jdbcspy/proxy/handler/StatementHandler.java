package de.luisoft.jdbcspy.proxy.handler;

import java.sql.Statement;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyConnectionMetaData;

/**
 * The statement handler.
 */
public class StatementHandler extends AbstractStatementHandler {

	/**
	 * Constructor.
	 * 
	 * @param props
	 *            the client properties
	 * @param theStmt
	 *            the original statement
	 * @param theSql
	 *            the sql string
	 * @param listener
	 *            the execution listener
	 * @param failedListener
	 *            the failed listener
	 * @param method
	 *            the method
	 */
	public StatementHandler(ClientProperties props, Statement theStmt, ProxyConnectionMetaData metaData, String theSql,
			String method) {
		super(props, theStmt, metaData, theSql, method);
	}
}
