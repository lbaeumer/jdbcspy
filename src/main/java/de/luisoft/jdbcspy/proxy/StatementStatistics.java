package de.luisoft.jdbcspy.proxy;

/**
 * The StatementStatistics.
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
public interface StatementStatistics extends Statistics {

    /**
     * The caller of the execute method.
     * @return String
     */
    String getExecuteCaller();

    /**
     * The execution time.
     * @return long
     */
    long getExecutionTime();

    /**
     * The point of time when the execution starts.
     * @return long
     */
    long getExecutionStartTime();

    /**
     * Get the SQL code.
     * @return String
     */
    String getSQL();
}
