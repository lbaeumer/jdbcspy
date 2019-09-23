A lightweight monitoring and profiling jdbc driver proxy

The jdbcspy is a lightweight profiling and monitoring proxy for your jdbc connection. It can be configured very easily and will provide information, which statements are executed, what are the statements with the longest duration, additional statistics about your connections, what statements are executed how often, etc. In addition it is possible to extend the proxy by implementing custom listeners that will provide additional information.

Features

log the execution and the iteration time of all SQL statements
identify statements that are executed multiple times
the stack trace with configurable depth for all listed statements
provides statistics for all connections, SQL statements, resultsets
provides the size of the resultset
provides an API to retrieve all statistical information
list all statements that are currently being executed
list all statements that have been executed, but have not been closed
notifies (e.g. via trace) if a statement's execution time exceeds a configurable threshold
notifies if you forgot to close a resultset, or a statement before the connection is closed
supports different loggers (log4j, java logging, slf, ...)
extendable by custom listeners

Installation

The installation is quite easy. 1. copy the file jdbcspy.jar into your classpath 1. copy the dbproxy.xml file into your home drive and edit the driver class name of the underlying jdbc driver. In addition you may want to configure the driver. Detailed information is here. 1. To the normal database url, you have to add the prefix proxy: 1. Now you can start your application as usual. The proxy will be activated and gather information about your jdbc connection.

User Guide (5min)
Try the following code (taken from the junit testclass MyTest.minimal() - cmp source code):

``` // register the proxy driver Class.forName("de.luisoft.jdbcspy.DBProxyDriver");

