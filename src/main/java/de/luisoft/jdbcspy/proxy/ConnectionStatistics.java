package de.luisoft.jdbcspy.proxy;

import java.util.List;

/**
 * Providing statistics about the caller.
 */
public interface ConnectionStatistics extends Statistics {

    /**
     * Get the caller of the connection.
     *
     * @return String The caller
     */
    String getCaller();

    /**
     * Get the statements.
     *
     * @return The statements.
     */
    List<ProxyStatement> getStatements();
}
