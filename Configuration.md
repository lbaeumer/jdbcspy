# Introduction #

There are several properties to control the execution and to manage the data that should be collected. If you want to customize the jdbcspy please copy the dbproxy.xml into your home drive and modify the properties which are described below.

# Details #

|Variable|Description|Type|
|:-------|:----------|:---|
|EnableProxyInitially|enable the proxy functionality|boolean|
|TraceDepth|the trace depth|int|
|ResultSetNextTimeThreshold|display statement if result set next method execution time exeeds threshold (in ms)|int|
|ResultSetTotalTimeThreshold|display statement if result set total execution time exeeds threshold (in ms)|int|
|ResultSetTotalSizeThreshold|display statement if result set size exeeds threshold (in byte)|int|
|StmtExecuteTimeThreshold|display statement if statement execute method execution time exeeds threshold (in ms)|int|
|StmtTotalTimeThreshold|display statement if statement total execution time exeeds threshold (in ms)|int|
|StmtTotalSizeThreshold|display statement if statement total size exeeds threshold (in byte)|int|
|ConnDumpCloseClassExp|dump the connection if closing method matches|list|
|ConnTotalTimeThreshold|display connection if connection total execution time exeeds threshold (in ms)|int|
|ConnTotalSizeThreshold|display connection if connection total size exeeds threshold (in byte)|int|
|DisplaySqlStringMaxlen|display sql string with maxlen|int|
|DisplayEntityBeans|display entity bean info|boolean|
|RemoveHints|remove the hints from the statement|boolean|
|IgnoreNotClosedObjects|ignore result sets or statements that  are not closed|boolean|
|IgnoreDoubleClosedObjects|ignore result sets or statements that are not closed more than one time|boolean|
|EnableSizeEvaluation|enable the size evaluation|boolean|
|StmtDebugClassExp|display statement if class.method:linenumber matched by any of the regular expressions|list of regular expression|
|StmtHistorizeClassExp|display statement if class.method:linenumber matched by any of the regular expressions|list of regular expression|
|StmtDebugSQLExp|display statement if the SQL statement is matched by any of the regular expressions<br>e.g. .<b>\sget\sall\smes\s.</b> will filter all SQL statements that contain the string ' get all mes '.<table><thead><th>list of regular expression</th></thead><tbody>
<tr><td>StmtHistorizeSQLExp</td><td>display statement if the SQL statement is matched by any of the regular expressions<br>e.g. .<b>\sget\sall\smes\s.</b> will filter all SQL statements that contain the string ' get all mes '.</td><td>list of regular expression</td></tr>
<tr><td>LongExecutionThreshold</td><td>display a warning if an execute exceeds the given time (in seconds)</td><td>int</td></tr>
<tr><td>LastStatementExecutionThreshold</td><td>display a warning if a statement was executed a defined number of times in a row</td><td>int</td></tr>
<tr><td>LastStatementMaxHistory</td><td>the maximum history</td><td>int</td></tr>
<tr><td>RepeatCountStmtSize</td><td>the maximum repeat count size</td><td>int</td></tr>
<tr><td>RepeatCountThreshold</td><td>the repeat count threshold</td><td>int</td></tr>