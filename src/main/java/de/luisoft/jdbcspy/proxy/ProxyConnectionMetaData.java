package de.luisoft.jdbcspy.proxy;

import java.util.List;

/**
 * The connection factory produces proxy connections. The returned connection
 * will additionally implement the ProxyConnection interface. The class read the
 * following environment variables: <br>
 * <table border=1 cellspacing=0 cellpadding=0>
 * <tr>
 * <td>Variable</td>
 * <td>Description</td>
 * <td>Type</td>
 * </tr>
 * <tr>
 * <td>EnableProxyInitially</td>
 * <td>enable the proxy functionality</td>
 * <td>boolean</td>
 * </tr>
 * <tr>
 * <td>TraceDepth</td>
 * <td>the trace depth</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>ResultSetNextTimeThreshold</td>
 * <td>display statement if result set next method execution time exeeds
 * threshold (in ms)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>ResultSetTotalTimeThreshold</td>
 * <td>display statement if result set total execution time exeeds threshold (in
 * ms)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>ResultSetTotalSizeThreshold</td>
 * <td>display statement if result set size exeeds threshold (in byte)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>StmtExecuteTimeThreshold</td>
 * <td>display statement if statement execute method execution time exeeds
 * threshold (in ms)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>StmtTotalTimeThreshold</td>
 * <td>display statement if statement total execution time exeeds threshold (in
 * ms)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>StmtTotalSizeThreshold</td>
 * <td>display statement if statement total size exeeds threshold (in byte)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>ConnDumpCloseClassExp</td>
 * <td>dump the connection if closing method matches</td>
 * <td>list</td>
 * </tr>
 * <tr>
 * <td>ConnTotalTimeThreshold</td>
 * <td>display connection if connection total execution time exeeds threshold
 * (in ms)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>ConnTotalSizeThreshold</td>
 * <td>display connection if connection total size exeeds threshold (in
 * byte)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>DisplaySqlStringMaxlen</td>
 * <td>display sql string with maxlen</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>RemoveHints</td>
 * <td>remove the hints from the statement</td>
 * <td>boolean</td>
 * </tr>
 * <tr>
 * <td>IgnoreNotClosedObjects</td>
 * <td>ignore result sets or statements that are not closed</td>
 * <td>boolean</td>
 * </tr>
 * <tr>
 * <td>EnableSizeEvaluation</td>
 * <td>enable the size evaluation</td>
 * <td>boolean</td>
 * </tr>
 * <tr>
 * <td>StmtDebugClassExp</td>
 * <td>display statement if class.method:linenumber matched by any of the
 * regular expressions</td>
 * <td>list of regular expression</td>
 * </tr>
 * <tr>
 * <td>StmtHistorizeClassExp</td>
 * <td>display statement if class.method:linenumber matched by any of the
 * regular expressions</td>
 * <td>list of regular expression</td>
 * </tr>
 * <tr>
 * <td>StmtDebugSQLExp</td>
 * <td>display statement if the SQL statement is matched by any of the regular
 * expressions<br>
 * e.g. .*\sget\sall\smes\s.* will filter all SQL statements that contain the
 * string ' get all mes '.</td>
 * <td>list of regular expression</td>
 * </tr>
 * <tr>
 * <td>StmtHistorizeSQLExp</td>
 * <td>display statement if the SQL statement is matched by any of the regular
 * expressions<br>
 * e.g. .*\sget\sall\smes\s.* will filter all SQL statements that contain the
 * string ' get all mes '.</td>
 * <td>list of regular expression</td>
 * </tr>
 * <tr>
 * <td>LongExecutionThreshold</td>
 * <td>display a warning if an execute exceeds the given time (in seconds)</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>LastStatementExecutionThreshold</td>
 * <td>display a warning if a statement was executed a defined number of times
 * in a row</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>LastStatementMaxHistory</td>
 * <td>the maximum history</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>RepeatCountStmtSize</td>
 * <td>the maximum repeat count size</td>
 * <td>int</td>
 * </tr>
 * <tr>
 * <td>RepeatCountThreshold</td>
 * <td>the repeat count threshold</td>
 * <td>int</td>
 * </tr>
 * </table>
 */
public interface ProxyConnectionMetaData {

    /**
     * Is proxy enabled?
     *
     * @return boolean
     */
    boolean isInitiallyEnabled();

    /**
     * Is proxy enabled?
     *
     * @return boolean
     */
    boolean isEnabled();

    /**
     * Enable the proxy.
     *
     * @param enableProxy boolean
     */
    void enableProxy(boolean enableProxy);

    /**
     * Dump the statistics.
     *
     * @return String
     */
    String dumpStatistics();

    /**
     * Clear the statistics.
     */
    void clearStatistics();

    /**
     * Get the int keys.
     *
     * @return String[]
     */
    List<String> getIntKeys();

    /**
     * Get the boolean keys.
     *
     * @return String[]
     */
    List<String> getBooleanKeys();

    /**
     * Get the list keys.
     *
     * @return String[]
     */
    List<String> getListKeys();

    /**
     * Set an int value.
     *
     * @param property String
     * @param value    int
     */
    void setProperty(String property, Object value);

    /**
     * Get a property.
     *
     * @param property String
     * @return the int value
     */
    Object getProperty(String property);
}
