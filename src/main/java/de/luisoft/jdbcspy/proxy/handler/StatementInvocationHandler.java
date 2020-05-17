package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.ProxyConnectionMetaData;

import java.sql.Statement;

/**
 * The statement handler.
 */
public class StatementInvocationHandler extends AbstractStatementInvocationHandler {

    /**
     * Constructor.
     *
     * @param props   the client properties
     * @param theStmt the original statement
     * @param theSql  the sql string
     * @param method  the method
     */
    public StatementInvocationHandler(ClientProperties props, Statement theStmt, ProxyConnectionMetaData metaData, String theSql,
                                      String method) {
        super(props, theStmt, metaData, theSql, method);
    }
}
