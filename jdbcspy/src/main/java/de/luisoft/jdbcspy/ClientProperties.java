package de.luisoft.jdbcspy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.luisoft.jdbcspy.proxy.listener.ConnectionListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionFailedListener;
import de.luisoft.jdbcspy.proxy.listener.ExecutionListener;
import de.luisoft.jdbcspy.proxy.util.Utils;

/**
 * The Properties class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ClientProperties.java,v 1.2 2010/03/23 20:01:04 d292734 Exp $
 */
public final class ClientProperties {

    /** A Logger. */
    private static final Log mTrace = LogFactory.getLog(ClientProperties.class);

    /** the db init file */
    private static final String DBINIT_FILE = "/de/luisoft/db/dbinit.xml";

    /** enable the proxy */
    public static final String DB_ENABLE_PROXY_INITIALLY = "EnableProxyInitially";

    /** throw warnings */
    public static final String DB_THROW_WARNINGS = "ThrowWarnings";

    /** driver class */
    public static final String DB_DRIVER_CLASS = "DriverClass";

    /** the threshold for the next method */
    public static final String DB_RESULTSET_NEXT_TIME_THRESHOLD =
        "ResultSetNextTimeThreshold";

    /** the threshold for the resultset iteration */
    public static final String DB_RESULTSET_TOTAL_TIME_THRESHOLD =
        "ResultSetTotalTimeThreshold";

    /** the threshold for the resultset iteration */
    public static final String DB_RESULTSET_TOTAL_SIZE_THRESHOLD =
        "ResultSetTotalSizeThreshold";

    /** the threshold */
    public static final String DB_STMT_EXECUTE_TIME_THRESHOLD =
        "StmtExecuteTimeThreshold";

    /** the threshold */
    public static final String DB_STMT_TOTAL_TIME_THRESHOLD =
        "StmtTotalTimeThreshold";

    /** the threshold */
    public static final String DB_STMT_TOTAL_SIZE_THRESHOLD =
        "StmtTotalSizeThreshold";

    /** the threshold */
    public static final String DB_CONN_TOTAL_TIME_THRESHOLD =
        "ConnTotalTimeThreshold";

    /** the threshold */
    public static final String DB_CONN_TOTAL_SIZE_THRESHOLD =
        "ConnTotalSizeThreshold";

    /** maximum number of characters to be displayed of sql string */
    public static final String DB_DISPLAY_SQL_STRING_MAXLEN =
        "DisplaySqlStringMaxlen";

    /** display entity beans */
    public static final String DB_DISPLAY_ENTITY_BEANS = "DisplayEntityBeans";

    /** remove hints */
    public static final String DB_REMOVE_HINTS = "RemoveHints";

    /** ignore not closed objects */
    public static final String DB_IGNORE_NOT_CLOSED_OBJECTS =
        "IgnoreNotClosedObjects";

    /** ignore double closed objects */
    public static final String DB_IGNORE_DOUBLE_CLOSED_OBJECTS =
        "IgnoreDoubleClosedObjects";

    /** enable size evaluation */
    public static final String DB_ENABLE_SIZE_EVALUATION =
        "EnableSizeEvaluation";

    /** debug beans */
    public static final String DB_STMT_DEBUG_CLASS_EXP = "StmtDebugClassExp";

    /** historize statement */
    public static final String DB_STMT_HISTORIZE_CLASS_EXP =
        "StmtHistorizeClassExp";

    /** debug beans */
    public static final String DB_STMT_DEBUG_SQL_EXP = "StmtDebugSQLExp";

    /** historize beans */
    public static final String DB_STMT_HISTORIZE_SQL_EXP = "StmtHistorizeSQLExp";

    /** the trace depth */
    public static final String DB_TRACE_DEPTH = "TraceDepth";

    /** dump after shutdown */
    public static final String DB_DUMP_AFTER_SHUTDOWN = "DumpAfterShutdown";

    /** dump interval in s */
    public static final String DB_DUMP_INTERVAL = "DumpInterval";

    /** the instance */
    private static ClientProperties instance;

    /** all int values */
    private static List mIntValues = Arrays.asList(new String[] {
        DB_RESULTSET_NEXT_TIME_THRESHOLD,
        DB_RESULTSET_TOTAL_TIME_THRESHOLD,
        DB_RESULTSET_TOTAL_SIZE_THRESHOLD,
        DB_STMT_EXECUTE_TIME_THRESHOLD,
        DB_STMT_TOTAL_TIME_THRESHOLD,
        DB_STMT_TOTAL_SIZE_THRESHOLD,
        DB_CONN_TOTAL_TIME_THRESHOLD,
        DB_CONN_TOTAL_SIZE_THRESHOLD,
        DB_DISPLAY_SQL_STRING_MAXLEN,
        DB_TRACE_DEPTH,
        DB_DUMP_INTERVAL
    });

    /** all boolean values */
    private static List mBoolValues = Arrays.asList(new String[] {
        DB_ENABLE_PROXY_INITIALLY,
        DB_THROW_WARNINGS,
        DB_DISPLAY_ENTITY_BEANS,
        DB_REMOVE_HINTS,
        DB_IGNORE_NOT_CLOSED_OBJECTS,
        DB_IGNORE_DOUBLE_CLOSED_OBJECTS,
        DB_ENABLE_SIZE_EVALUATION,
        DB_DUMP_AFTER_SHUTDOWN
    });

    /** all string values */
    private static List mStringValues = Arrays.asList(new String[] {
        DB_DRIVER_CLASS
    });

    /** all list values */
    private static List mListValues = Arrays.asList(new String[] {
        DB_STMT_DEBUG_CLASS_EXP,
        DB_STMT_HISTORIZE_CLASS_EXP,
        DB_STMT_DEBUG_SQL_EXP,
        DB_STMT_HISTORIZE_SQL_EXP
    });

    /** the values */
    private Map values;

    /**
     * Constructor.
     */
    private ClientProperties() {

        values = new LinkedHashMap();

        InputStream input =
        	ClientProperties.class.getResourceAsStream(DBINIT_FILE);

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(input, mHandler);
            input.close();
        }
        catch (Exception ex) {
            mTrace.info("Parsing the dbinit file " + DBINIT_FILE + " failed.",
                        ex);
            ex.printStackTrace();
        }

        try {
            input =	ClientProperties.class.getResourceAsStream("/dbproxy.xml");
            if (input != null) {
            	System.out.println("Reading dbproxy.xml configuration from classpath.");
	            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
	            parser.parse(input, mHandler);
	            input.close();
            }
        }
        catch (Exception ex) {
            mTrace.info("Parsing the dbinit file dbproxy.xml failed.",
                        ex);
            ex.printStackTrace();
        }
    }

    /**
     * Get the instance.
     * @return instance
     */
    public static ClientProperties getInstance() {
        if (instance == null) {
            instance = new ClientProperties();
        }
        return instance;
    }

    /**
     * Init the client properties.
     */
    public void init() {

        File f = new File(System.getProperty("user.home") + "/dbproxy.xml");
        mTrace.debug("Reading properties from " + f);

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(f, mHandler);
        }
        catch (FileNotFoundException ex) {
            mTrace.info("The dbproxy configuration file " + f
                        + " does not exist.");
        }
        catch (Exception ex) {
            mTrace.error("parsing the file " + f + " failed", ex);
        }

        mTrace.debug("Reading system properties");
        readSystemProperties();
    }

    /**
     * Get the int value.
     * @param flag the flag
     * @return the value
     */
    public int getInt(String flag) {
        return ((Integer) values.get(flag)).intValue();
    }

    /**
     * Get the boolean value.
     * @param flag the flag
     * @return the value
     */
    public boolean getBoolean(String flag) {
        return ((Boolean) values.get(flag)).booleanValue();
    }

    /**
     * Get the list value.
     * @param flag the flag
     * @return the value
     */
    public List getList(String flag) {
        return (List) values.get(flag);
    }

    /**
     * Get the property.
     * @param flag the flag
     * @return the value
     */
    public Object getProperty(String flag) {
        return values.get(flag);
    }

    /**
     * Get the int keys.
     * @return String[]
     */
    public List getIntKeys() {
        return mIntValues;
    }

    /**
     * Get the boolean keys.
     * @return String[]
     */
    public List getBooleanKeys() {
        return mBoolValues;
    }

    /**
     * Get the string keys.
     * @return String[]
     */
    public List getStringKeys() {
        return mStringValues;
    }

    /**
     * Get the list keys.
     * @return String[]
     */
    public List getListKeys() {
        return mListValues;
    }

    /**
     * @see java.lang.Object
     */
    public String toString() {
        return values.toString();
    }

    /**
     * Is the proxy enabled?
     * @return boolean
     */
    public boolean isInitiallyEnabled() {
        return ((Boolean) values.get(DB_ENABLE_PROXY_INITIALLY)).booleanValue();
    }

    /**
     * Get the driver class
     * @return the driver class
     */
    public String getDriverClass() {
        return (String) values.get(DB_DRIVER_CLASS);
    }

    /**
     * Set am integer property.
     * @param property String
     * @param value the value
     */
    public void setProperty(String property, Object value) {
        if (value instanceof Boolean) {
            if (!mBoolValues.contains(property)) {
                throw new IllegalArgumentException("the boolean property "
                    + property
                    + " does not exist.");
            }
            mTrace.debug("set " + property + " to " + value);
            values.put(property, value);
            return;
        }
        if (value instanceof Integer) {
            if (!mIntValues.contains(property)) {
                throw new IllegalArgumentException("the int property "
                    + property
                    + " does not exist.");
            }
            mTrace.debug("set " + property + " to " + value);
            values.put(property, value);
            return;
        }
        if (value instanceof String) {
            if (!mStringValues.contains(property)) {
                throw new IllegalArgumentException("the string property "
                    + property
                    + " does not exist.");
            }
            mTrace.debug("set " + property + " to " + value);
            values.put(property, value);
            return;
        }
        if (value instanceof List) {
            if (!mListValues.contains(property)) {
                throw new IllegalArgumentException("the list property "
                    + property
                    + " does not exist.");
            }
            mTrace.debug("set " + property + " to " + value);
            values.put(property, value);
            return;
        }

        throw new IllegalArgumentException("the argument " + value + " is illegal");
    }

    private void readSystemProperties() {
        Properties p = System.getProperties();
        for (Iterator it = mIntValues.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object obj = p.get(key);
            if (obj != null) {
                try {
                    mTrace.debug("set " + key + " to " + obj);
                    values.put(key, Integer.valueOf((String) obj));
                } catch (Exception e) {
                    System.err.println("property for " + key + "="
                        + obj + " is not convertable to type int");
                }
            }
        }
        for (Iterator it = mBoolValues.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object obj = p.get(key);
            if (obj != null) {
                try {
                    mTrace.debug("set " + key + " to " + obj);
                    values.put(key, Boolean.valueOf((String) obj));
                } catch (Exception e) {
                    System.err.println("property for " + key + "="
                        + obj + " is not convertable to type boolean");
                }
            }
        }
        for (Iterator it = mStringValues.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            Object obj = p.get(key);
            if (obj != null) {
                mTrace.debug("set " + key + " to " + obj);
                values.put(key, obj);
            }
        }
    }

    /** the listener list */
    private List mListener = new ArrayList();

    /** the listener list */
    private List mFailedListener = new ArrayList();

    /** the connection listener list */
    private List mConnectionListener = new ArrayList();

    public List getListener() {
    	return mListener;
    }
    public List getFailedListener() {
    	return mFailedListener;
    }
    public List getConnectionListener() {
    	return mConnectionListener;
    }
    /**
     * The XML handler.
     */
    private DefaultHandler mHandler = new DefaultHandler() {
    	private ConnectionListener connectionListener;
    	private ExecutionListener executionListener;
    	private ExecutionFailedListener executionFailedListener;

        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes) {
            if ("property".equals(qName)) {
                String name = attributes.getValue("name");
                String value = attributes.getValue("value");

                if (connectionListener == null
                		&& executionFailedListener == null
                		&& executionListener == null) {
	                boolean found = false;
	                if (mBoolValues.contains(name)) {
	                    setProperty(name, Boolean.valueOf(value));
	                    found = true;
	                } else if (mIntValues.contains(name)) {
	                    setProperty(name, Integer.valueOf(value));
	                    found = true;
	                } else if (mStringValues.contains(name)) {
	                    setProperty(name, value);
	                    found = true;
	                } else if (mListValues.contains(name)) {
	                    List l = new ArrayList();
	                    String[] s = value.split(",");
	                    for (int j = 0; j < s.length; j++) {
	                        l.add(s[j]);
	                    }
	                    setProperty(name, l);
	                    found = true;
	                }
	
	                if (!found) {
	                    mTrace.warn("Could not find the property " + name + ".");
	                }
                } else {
                	// this must be a value assigned by a listener
                	if (connectionListener != null) {
                		Utils.setProperty(connectionListener, name, value);
                	} else if (executionFailedListener != null) {
                		Utils.setProperty(executionFailedListener, name, value);
                    } else if (executionListener != null) {
                    	Utils.setProperty(executionListener, name, value);
                    } else {
                    	mTrace.error("illegal state");
                    }
                }
            } else if (qName.endsWith("listener")) {

                String classname = attributes.getValue("class");
                try {
                	Class c = Class.forName(classname);
                	Object cl = c.newInstance();
                	
                	if (qName.equals("connectionlistener")) {
                		mConnectionListener.add(cl);
                		connectionListener = (ConnectionListener) cl;
                	} else if (qName.equals("executionfailedlistener")) {
                		mFailedListener.add(cl);
                		executionFailedListener = (ExecutionFailedListener) cl;
                	} else if (qName.equals("executionlistener")) {
                		mListener.add(cl);
                		executionListener = (ExecutionListener) cl;
                	} else { 
                		throw new IllegalArgumentException("The listener " + qName + " does not exist.");
                	} 
 
                } catch (Exception e) {
                	e.printStackTrace();
                }
                
            }
        }

        public void endElement(String uri, String localName, String qName)
				throws SAXException {

        	if (qName.endsWith("listener")) {

            	connectionListener = null;
            	executionFailedListener = null;
            	executionListener = null;
        	}
		}
    };
}
