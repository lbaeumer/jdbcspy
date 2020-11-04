A lightweight monitoring and profiling jdbc driver proxy

The jdbcspy is a lightweight profiling and monitoring proxy for your jdbc connection. It can be configured very easily and will provide information, which statements are executed, what are the statements with the longest duration, additional statistics about your connections, what statements are executed how often, etc. In addition it is possible to extend the proxy by implementing custom listeners that will provide additional information.

# Features

- log the execution and the iteration time of all SQL statements
- identify statements that are executed multiple times
- the stack trace with configurable depth for all listed statements
- reports statistics for all connections, SQL statements, resultsets
- reports the size of the resultset
- provides an API to retrieve all statistical information
- list all statements that are currently being executed
- list all statements that have been executed, but have not been closed
- notifies (e.g. via trace) if a statement's execution time exceeds a configurable threshold
- notifies if you forgot to close a resultset or a statement before the connection is closed
- extendable by custom listeners

# Installation

The installation is quite easy.

1. Copy the file jdbcspy.jar into your classpath
1. (Optional) copy the dbproxy.xml file into your home drive and edit the properties to customize the jdbcspy's behaviour.
1. Choose one of the following options dependent on the way your database is configured:
    1. Driver URL: If you are using a database url, you only have a add the prefix  **proxy**:
       Example: the driver url
       ```
       jdbc:db2://myhost:5021/DATABASE
       ```
       changes to:
       ```
       proxy:jdbc:db2://myhost:5021/DATABASE
       ```
    1.  java.sql.Datasource: Either use one of the predefined vendor specific database drivers of define your own one.
        Some predefined driver classes are:
        ```
        de.luisoft.jdbcspy.vendor.DerbyProxyDatasource
        de.luisoft.jdbcspy.vendor.DB2ProxyDatasource
        de.luisoft.jdbcspy.vendor.MssqlProxyDatasource
        de.luisoft.jdbcspy.vendor.MysqlProxyDatasource
        de.luisoft.jdbcspy.vendor.OracleProxyDatasource
        de.luisoft.jdbcspy.vendor.PostgreSqlProxyDatasource
        ```
    1.  java.sql.XADatasource: Either use one of the predefined vendor specific database drivers of define your own one.
        Some predefined driver classes are:
        ```
        de.luisoft.jdbcspy.vendor.DerbyProxyXADatasource
        de.luisoft.jdbcspy.vendor.DB2ProxyXADatasource
        de.luisoft.jdbcspy.vendor.MssqlProxyXADatasource
        de.luisoft.jdbcspy.vendor.MysqlProxyXADatasource
        de.luisoft.jdbcspy.vendor.OracleProxyXADatasource
        de.luisoft.jdbcspy.vendor.PostgreSqlProxyXADatasource
        ```
        You will find an example for WebSphere Liberty below.
1. Now you can start your application as usual. The proxy will be activated automatically and will trace your jdbc connection.

# User Guide Driver URL (5min)

## Example code

Try the following code (taken from the junit testclass [DriverTest.minimal()](/lbaeumer/jdbcspy/blob/master/src/test/java/de/luisoft/jdbc/test/DriverTest.java) - cmp source code):

```java
    // instanciate the driver
    Class.forName("de.luisoft.jdbcspy.AbstractProxyDriver");

    // add the prefix 'proxy:' to the usual url
    Connection c = DriverManager.getConnection("proxy:mytestdb&rscnt=100&itertime=1000&exectime=500");

    PreparedStatement p = c.prepareStatement("select * from test");
    ResultSet rs = p.executeQuery();

    while (rs.next()) {
        // read result set
    }
    rs.close();
    p.close();
    c.close();

    // dump the statistics gathered by the listeners
    System.out.println("connection dump:\n"
        + ConnectionFactory.dumpStatistics());
```

## Jdbcspy result

All closed statements and connections will be printed to java.util.logging.Logger during the execution. In the above code example Jdbcspy detects one statement and one connection. The statement's resultset has 100 datasets. The initial execution took 503ms and iterating all 100 datasets took additionally 1s.

    May 24, 2020 4:40:59 PM de.luisoft.jdbcspy.proxy.handler.AbstractStatementInvocationHandler handleClose
    INFO: closed statement "select * from test" (503ms + 1,0s; #=100) executed since 16:40:57.671 in DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33 in DriverTest.minimal:91|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33
    May 24, 2020 4:40:59 PM de.luisoft.jdbcspy.proxy.handler.ConnectionInvocationHandler handleClose

    INFO: closed connection
    Connection[#stmt=1; duration=1,51s; isolation=NONE, opened in DBProxyDriver.connect:156|DriverTest.minimal:82|JUnit4IdeaTestRunner.startRunnerWithArgs:68] {
        1: "select * from test" (503ms + 1,0s; #=100) executed since 16:40:57.671 in DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33
    }

The final dumpStatistics method will report information gathered by the ExecutionListeners.

    connection dump:
    [ExecutionTimeListener[

      long running history (execTime + iterTime):
        1: "select * from test" (503ms + 1,0s; #=100) executed since 16:40:57.671 in DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33
    ]]

    [ExecutionLastStatementListener[
      0: "select * from test" (#=1, DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33)
    ]]

    [ExecutionStatisticListener[online since 16:40:57 (2,89s)
      #stmt=1; #rs=100; duration=1,51s; avgDuration=1,51s; maxLength=18; avgLength=18;
      time={1-3s}=1;
      length={<=20}=1
    ]]

    [ConnectionStatisticListener[
      #conn=1; #max open conns=1; #max stmts/conn=1
    ]]


## How do I have to interpret the data?

### ExecutionTimeListener

The ExecutionTimeListener provides information about the statement execution times and maintains a statistic about the long running statements.

First of all it informs you that neither a statement is currently being executed nor a statement that has been executed is waiting to be closed. The 'long running history' lists all statements sorted by the execution time.
For each statement the following information is provided:

* the execution time (503ms), which is the time that the database needs to return the first result set.
* the iteration time (1s), which is the time the database needs to return the resultsets 2 to n
* and the size of the resultset (100) * the time when the execution/query has been started
* the (simplified) stacktrace (including class, method and line number) to track the statements initiator


    [ExecutionTimeListener[
      long running history (execTime + iterTime):
        1: "select * from test" (503ms + 1,0s; #=100) executed since 16:40:57.671 in DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33
    ]]

### ExecutionLastStatementListener

List the last statements that have been executed. In our example there is only one statement. The #1 means, that this statement has been executed only once.

    [ExecutionLastStatementListener[
      0: "select * from test" (#=1, DriverTest.minimal:85|JUnit4IdeaTestRunner.startRunnerWithArgs:68|IdeaTestRunner$Repeater.startRunnerWithArgs:33)
    ]]

### ExecutionStatisticListener

The ExecutionStatisticListener provides statistical information about the SQL statements.
You get the following information:

* 1 statement has been executed
* the sum of all resultset sizes for all statements is 100
* the maximum and average duration (sum of execution and iteration times for all statements) is 1.51s
* the maximum and the average length (serialized all resultset attributes) of the resultset is 18byte
* there is one statement in the execution time range from 1 to 3 seconds


    [ExecutionStatisticListener[online since 16:40:57 (2,89s)
      #stmt=1; #rs=100; duration=1,51s; avgDuration=1,51s; maxLength=18; avgLength=18;
      time={1-3s}=1;
      length={<=20}=1
    ]]

### ConnectionStatisticListener

The ConnectionStatisticListener provides statistical information about the connections.
You get the following information:

* 1 connection has been opened
* maximum 1 connection has been open concurrently
* maximum 1 statement has been opened per connection


    [ConnectionStatisticListener[
      #conn=1; #max open conns=1; #max stmts/conn=1
    ]]

For more complex examples you may have a look at the junit tests that are provided with the distribution or checkout the project.

# User Guide Datasource (5min)

## Configuration Example WebSphere Liberty

The WebSphere Liberty configuration will probably look like the following code snippet.

```xml
    <library id="DB2JCCLib">
        <fileset dir="/mypath/driver" includes="db2jcc.jar db2jcc_license_cisuz.jar jdbcspy.jar"/>
    </library>

    <dataSource id="db2xa" jndiName="jdbc/db2xa" type="javax.sql.XADataSource">
        <jdbcDriver libraryRef="DB2JCCLib"
            javax.sql.XADataSource="de.luisoft.jdbcspy.vendor.DB2ProxyXADatasource"
        />
        <properties.db2.jcc databaseName="SAMPLEDB" serverName="localhost" portNumber="50000"/>
    </dataSource>
```

There are only two entries you have to add:
1. you have to add the jdbcspy.jar to the classpath and
1. you have to provide the javax.sql.XADataSource class name.


