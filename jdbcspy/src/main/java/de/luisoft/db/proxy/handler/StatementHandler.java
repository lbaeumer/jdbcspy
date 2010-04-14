package de.luisoft.db.proxy.handler;

import java.sql.Statement;

import de.luisoft.db.ClientProperties;
import de.luisoft.db.ProxyConnectionMetaData;

/**
 * The statement handler.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: StatementHandler.java 885 2007-03-18 20:46:41Z lui $
 */
public class StatementHandler extends AbstractStatementHandler {

    /**
     * Constructor.
     * @param props the client properties
     * @param theStmt the original statement
     * @param theSql the sql string
     * @param listener the execution listener
     * @param failedListener the failed listener
     * @param method the method
     */
    public StatementHandler(ClientProperties props, Statement theStmt,
                            ProxyConnectionMetaData metaData,
                            String theSql, String method) {
    	super(props, theStmt, metaData, theSql, method);
    }
}
