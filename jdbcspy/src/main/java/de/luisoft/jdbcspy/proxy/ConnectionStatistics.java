package de.luisoft.jdbcspy.proxy;

import java.util.List;

import de.luisoft.jdbcspy.ProxyConnectionMetaData;

/**
 * The ConnectionStatistics.
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: PSI</p>
 *
 * @author Lui
 * @version $Id: $
 */
public interface ConnectionStatistics extends Statistics {

    /**
    * Get the caller of the connection.
    * @return String The caller
    */
   String getCaller();

   /**
     * Get the statements.
     * @return The statements.
     */
    List getStatements();

    /**
      * Get the ConnectionFactory.
      */
    ProxyConnectionMetaData getProxyConnectionMetaData();
}
