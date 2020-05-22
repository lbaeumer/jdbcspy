A lightweight monitoring and profiling jdbc driver proxy

The jdbcspy is a lightweight profiling and monitoring proxy for your jdbc connection. It can be configured very easily and will provide information, which statements are executed, what are the statements with the longest duration, additional statistics about your connections, what statements are executed how often, etc. In addition it is possible to extend the proxy by implementing custom listeners that will provide additional information.

# Features

- log the execution and the iteration time of all SQL statements
- identify statements that are executed multiple times
- the stack trace with configurable depth for all listed statements
- provides statistics for all connections, SQL statements, resultsets
- provides the size of the resultset
- provides an API to retrieve all statistical information
- list all statements that are currently being executed
- list all statements that have been executed, but have not been closed
- notifies (e.g. via trace) if a statement's execution time exceeds a configurable threshold
- notifies if you forgot to close a resultset, or a statement before the connection is closed
- supports different loggers (log4j, java logging, slf, ...)
- extendable by custom listeners

# Installation

The installation is quite easy. 

1. Copy the file jdbcspy.jar into your classpath 
1. Optional: copy the dbproxy.xml file into your home drive and edit the properties to customize the jdbcspy's behaviour.
1. Choose one of the following options dependent on the way your database is configured:
    1. Driver URL: If you are using a database url, you only have a add the prefix  **proxy**:
    
       Example:
       diver url: 
       
       jdbc:db2://myhost:5021/DATABASE

       then just change the url to: 
       
       proxy:jdbc:db2://myhost:5021/DATABASE
    1.  java.sql.Datasource: Either use one of the predefined vendor specific database drivers of define your one one.

        Some predefined driver classes are:
        
        de.luisoft.jdbcspy.vendor.DB2ProxyDatasource
        de.luisoft.jdbcspy.vendor.MssqlProxyDatasource
        de.luisoft.jdbcspy.vendor.MysqlProxyDatasource
        de.luisoft.jdbcspy.vendor.OracleProxyDatasource

    1.  java.sql.XADatasource: Either use one of the predefined vendor specific database drivers of define your one one.
    
        Some predefined driver classes are:
        
        de.luisoft.jdbcspy.vendor.DB2ProxyXADatasource
        de.luisoft.jdbcspy.vendor.MssqlProxyXADatasource
        de.luisoft.jdbcspy.vendor.MysqlProxyXADatasource
        de.luisoft.jdbcspy.vendor.OracleProxyXADatasource
    
1. Now you can start your application as usual. The proxy will be activated and gather information about your jdbc connection.

# User Guide (5min)

Try the following code (taken from the junit testclass MyTest.minimal() - cmp source code):

	// register the proxy driver Class.forName("de.luisoft.jdbcspy.DBProxyDriver");

	// get a connection in the usual manner. Please add the prefix
	// 'proxy:' to the driver url
	// The mytestdb driver is a mock database. The statement below
	// will return 100 result sets and takes 1000ms(500ms)
	// to iterate(execute) the result set
	Connection c = DriverManager.getConnection(
    	"proxy:mytestdb&rscnt=100&itertime=1000&exectime=500");

	PreparedStatement p = c.prepareStatement("select * from test");
	ResultSet rs = p.executeQuery();

	while (rs.next()) {
    	// read result set
	}
	rs.close();
	p.close();
	c.close();

The following information is determined by the jdbcspy:

	[ExecutionTimeListener[ currently executing:

	executed but waiting to be closed:

	long running history (execTime + iterTime): 1: "select * from test" (501ms + 1.0s; #=100) executed since 21:54:58.894 in MyTest.minimal:95|TestCase.runTest:166|TestCase.runBare:140 ]]

	[ExecutionLastStatementListener[ 0: "select * from test" (#=1, MyTest.minimal:95|TestCase.runTest:166|TestCase.runBare:140) ]]

	[ExecutionRepeatCountListener[ 1: #=1: "select * from test" ]]

	[ExecutionStatisticListener[online since 21:54:58 (1.65s)


## How do I have to interpret the data?

### ExecutionTimeListener

The ExecutionTimeListener provides information about the statement execution times and maintains a statistic about the long running statements.

First of all it informs you that neither a statement is currently being executed nor a statement that has been executed is waiting to be closed. The 'long running history' lists all statements sorted by the execution time. 
For each statement the following information is provided: 

* the execution time (501ms - 1ms overhead), which is the time that the database needs to return the first result set. 
* the iteration time (1s), which is the time the database needs to return the resultsets 2 to n 
* and the size of the resultset (100) * the time when the execution/query has been started 
* the (simplified) stacktrace (including class, method and line number) to track the statements initiator

	1: "select * from test" (501ms + 1.0s; #=100) executed since 21:54:58.894 in MyTest.minimal:95|TestCase.runTest:166|TestCase.runBare:140

### ExecutionLastStatementListener

List the last statements that have been executed. In our example there is only one statement. The #1 means, that this statement has been executed only once.

	0: "select * from test" (#=1, MyTest.minimal:95|TestCase.runTest:166|TestCase.runBare:140)

### ExecutionRepeatCountListener

List the statements that have been executed multiple times ordered by their occurrence.

	1: #=1: "select * from test"

### ExecutionStatisticListener

The ExecutionStatisticListener provides statistical information about the SQL statements. 
You get the following information:

* 1 statement has been executed 
* the sum of all resultset sizes for all statements is 100 
* the maximum and average duration (sum of execution and iteration times for all statements) is 1.50s 
* the maximum and the average length (serialized all resultset attributes) of the resultset is 18byte 
* there is one statement in the execution time range from 1 to 3 seconds

	stmt=1; #rs=100; duration=1.50s; avgDuration=1.50s; maxLength=18; avgLength=18;
	time={1-3s}=1; length={<=20}=1

### ConnectionStatisticListener

The ConnectionStatisticListener provides statistical information about the connections.
You get the following information: 

* 1 connection has been opened 
* maximum 1 connection has been open concurrently 
* maximum 1 statement has been opened per connection

	conn=1; #max open conns=1; #max stmts/conn=1

For more complex examples you may have a look at the junit tests that are provided with the distribution or checkout the project.
