package de.luisoft.jdbcspy.proxy;

/**
 * The StatementStatistics.
 */
public interface StatementStatistics extends Statistics {

    /**
     * The caller of the execute method.
     *
     * @return String
     */
    String getExecuteCaller();

    /**
     * The execution time.
     *
     * @return long
     */
    long getExecutionTime();

    /**
     * The point of time when the execution starts.
     *
     * @return long
     */
    long getExecutionStartTime();

    /**
     * Get the SQL code.
     *
     * @return String
     */
    String getSQL();
}
