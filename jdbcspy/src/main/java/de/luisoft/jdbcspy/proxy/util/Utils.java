package de.luisoft.jdbcspy.proxy.util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyConnection;

/**
 * The utils class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: Utils.java 747 2006-06-08 18:28:10Z lui $
 */
public class Utils {

    /** the size formatter */
    public static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("0.0#");

    /** the date formatter */
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    /** the time formatter */
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

    /** the milli time formatter */
    public static final SimpleDateFormat MILLI_TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss.SSS");

    /** the logger object for tracing */
    private static final Log mTrace =
        LogFactory.getLog(Utils.class);

    /**
     * Get the size string.
     * @param size long
     * @return String
     */
    public static String getSizeString(long size) {
        if (size < 1000) {
            return size + "byte";
        } else if (size < 1000000) {
            return NUMBER_FORMATTER.format(size / 1024f) + "kB";
        }

        return NUMBER_FORMATTER.format(size / 1048576f) + "MB";
    }

    /**
     * Get the size string.
     * @param duration the duration
     * @return the time string
     */
    public static String getTimeString(long duration) {
        if (duration < 1000) {
            return duration + "ms";
        } else if (duration < 60000) {
            return NUMBER_FORMATTER.format(duration / 1000f) + "s";
        } else if (duration < 3600000) {
            // less 1h
            int m = (int) (duration / 60000);
            duration -= (60000 * m);
            int s = (int) (duration / 1000);
            duration -= (1000 * s);

            return m + "m " + s + "s " + duration + "ms";
        }

        long d = duration / (3600000 * 24);
        duration -= (3600000 * 24 * d);

        long h = duration / 3600000;
        duration -= (3600000 * h);

        long m = duration / 60000;
        duration -= (60000 * m);

        long s = (duration % 60000) / 1000;

        return (d != 0 ? d + "d " : "")
            + (h != 0 ? h + "h " : "")
            + (m != 0 ? m + "m " : "")
            + (s != 0 ? s + "s" : "");
    }

    /**
     * Get the execution class.
     * @param proxy the proxy object
     * @return String
     */
    public static String getExecClass(Object proxy) {
        Throwable t = new Throwable();
        StackTraceElement[] e = t.getStackTrace();
        StringBuffer execClass = new StringBuffer();

        int j = 1;
        for (int i = 0; i < e.length; i++) {
            StackTraceElement el = e[i];
            if (el.getClassName().startsWith(ProxyConnection.class.getPackage().
                getName())
                || proxy.getClass().getName().equals(el.getClassName())
                || el.getClassName().startsWith("org.jboss.")
                || el.getClassName().startsWith("java.")
                || el.getClassName().startsWith("sun.")) {
                continue;
            }

            if (i > 0
                && e[i - 1].getClassName().startsWith(
                    ProxyConnection.class.getPackage().getName())
                && el.getMethodName().equals("close")) {
                // this is the proxy class
                continue;
            }

            if (j > 1) {
                execClass.append("|");
            }
            execClass.append(getPrintClass(el));
            if (j >= ClientProperties.getInstance().getInt(
                ClientProperties.DB_TRACE_DEPTH)) {
                break;
            }

            j++;
        }

        return execClass.toString();
    }

    /**
     * Get the exec class.
     * @param el StackTraceElement
     * @return String
     */
    private static String getPrintClass(StackTraceElement el) {
        return (el.getClassName().indexOf('.') > 0
                ? el.getClassName().substring(el.getClassName().lastIndexOf(".") + 1)
                : el.getClassName())
            + "." + el.getMethodName() + ":" + el.getLineNumber();
    }

    /**
     * Get the formatted date.
     * @param date date
     * @return String
     */
    public static String getFormattedDate(Date date) {
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        if (cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
            && cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return TIME_FORMATTER.format(date);
        }
        else {
            return DATE_FORMATTER.format(date);
        }
    }

    /**
     * Remove the hints.
     * @param sql sql
     * @return output sql
     */
    public static String removeHints(String sql) {
        return sql.replaceAll("/\\*\\+[^/]*\\*/", "");
    }

    /**
     * Is the statement traceable?
     * @param regExps List of regular expressions
     * @return boolean
     */
    public static String isTraceClass(List regExps) {
        if (!regExps.isEmpty()) {
            Throwable t = new Throwable();
            StackTraceElement[] e = t.getStackTrace();
            for (int i = 0; i < e.length; i++) {
                StackTraceElement el = e[i];
                for (Iterator it = regExps.iterator(); it.hasNext(); ) {
                    String regExp = (String) it.next();
                    if ((el.getClassName() + "."
                         + el.getMethodName()
//                         + ":" + el.getLineNumber()
                        ).matches(regExp)) {
                        return regExp;
                    }
                    else {
                        if (mTrace.isDebugEnabled()) {
                            mTrace.debug(el.getClassName() + "."
                                + el.getMethodName()
//                                + ":" + el.getLineNumber()
                                + " does not match "
                                + regExp);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Is the statement traceable?
     * @param regExps List of regular expressions
     * @param sql the sql command
     * @return the reg exp that matches
     */
    private static String isTraceSql(List regExps, String sql) {
        for (Iterator it = regExps.iterator(); it.hasNext(); ) {
            String regExp = (String) it.next();
            if (sql.replaceAll("[\n\r]", "").matches(regExp)) {
                return regExp;
            }
        }
        return null;
    }

    /**
     * isTrace.
     * @param sql String
     * @return the reg Exp
     */
    public static String isTrace(String sql) {
        ClientProperties prop = ClientProperties.getInstance();
        List debug = prop.getList(ClientProperties.
            DB_STMT_DEBUG_CLASS_EXP);
        String regExp = Utils.isTraceClass(debug);
        if (regExp != null) {
            return regExp;
        }
        else {
            debug = prop.getList(ClientProperties.
                DB_STMT_DEBUG_SQL_EXP);
            return Utils.isTraceSql(debug, sql);
        }
    }

    /**
     * isHistoryTrace.
     * @param regExps List
     * @param sql String
     * @return boolean
     */
    public static String isHistoryTrace(String sql) {
        ClientProperties prop = ClientProperties.getInstance();
        List debug = prop.getList(ClientProperties.
            DB_STMT_HISTORIZE_CLASS_EXP);
        String regExp = Utils.isTraceClass(debug);
        if (regExp != null) {
            return regExp;
        }
        else {
            debug = prop.getList(ClientProperties.
                DB_STMT_HISTORIZE_SQL_EXP);
            return Utils.isTraceSql(debug, sql);
        }
    }

    /**
     * Get the type name by the typeId.
     * @param type the type id
     * @return the type name
     */
    public static String getTypeName(Number type) {
        switch (type.intValue()) {
            case Types.ARRAY:
                return "ARRAY";
            case Types.BIGINT:
                return "BIGINT";
            case Types.BINARY:
                return "BINARY";
            case Types.BIT:
                return "BIT";
            case Types.BLOB:
                return "BLOB";
            case Types.BOOLEAN:
                return "BOOLEAN";
            case Types.CHAR:
                return "CHAR";
            case Types.CLOB:
                return "CLOB";
            case Types.DATALINK:
                return "DATALINK";
            case Types.DATE:
                return "DATE";
            case Types.DECIMAL:
                return "DECIMAL";
            case Types.DISTINCT:
                return "DISTINCT";
            case Types.DOUBLE:
                return "DOUBLE";
            case Types.FLOAT:
                return "FLOAT";
            case Types.INTEGER:
                return "INTEGER";
            case Types.JAVA_OBJECT:
                return "JAVA_OBJECT";
            case Types.LONGVARBINARY:
                return "LONGVARBINARY";
            case Types.LONGVARCHAR:
                return "LONGVARCHAR";
            case Types.NULL:
                return "NULL";
            case Types.NUMERIC:
                return "NUMERIC";
            case Types.OTHER:
                return "OTHER";
            case Types.REAL:
                return "REAL";
            case Types.REF:
                return "REF";
            case Types.SMALLINT:
                return "SMALLINT";
            case Types.STRUCT:
                return "STRUCT";
            case Types.TIME:
                return "TIME";
            case Types.TIMESTAMP:
                return "TIMESTAMP";
            case Types.TINYINT:
                return "TINYINT";
            case Types.VARBINARY:
                return "VARBINARY";
            case Types.VARCHAR:
                return "VARCHAR";
            default:
                return "<unknown>";
        }
    }

    /**
     * Get the isolation level.
     * @param id int
     * @return String
     */
    public static String getIsolationLevel(int id) {
        switch (id) {
            case Connection.TRANSACTION_NONE: return "NONE";
            case Connection.TRANSACTION_READ_COMMITTED: return "readCommitted";
            case Connection.TRANSACTION_READ_UNCOMMITTED: return "readUncommitted";
            case Connection.TRANSACTION_REPEATABLE_READ: return "repeatableRead";
            case Connection.TRANSACTION_SERIALIZABLE: return "serializable";
            default: return "???";
        }
    }

    public static void setProperty(Object obj, String name, String value) {
    	Class c = obj.getClass();
    	if (mTrace.isDebugEnabled()) {
    		mTrace.debug("setProperty(" + obj.getClass()
    				+ ", " + name + ", " + value + ")");
    	}
    	Method m[] = c.getMethods();
    	for (int i = 0; m != null && i < m.length; i++) {
    		if (mTrace.isDebugEnabled()) {
    			mTrace.debug("found method" + m[i]);
    		}
    		if (m[i].getName().equals(
    				"set" + name.substring(0, 1).toUpperCase()
    				+ name.substring(1))) {
    			if (mTrace.isDebugEnabled()) {
    				mTrace.debug("korrekt method" + m[i]);
    			}
    			Class[] parms = m[i].getParameterTypes();
    			if (parms != null && parms.length == 1) {
    				if (mTrace.isDebugEnabled()) {
    					mTrace.debug("korrekt parms" + parms[0]);
    				}
    				if (parms[0].isAssignableFrom(String.class)) {
    					try {
    						m[i].invoke(obj, new Object[] { value });
    						return;
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}

    				if (parms[0].isAssignableFrom(int.class)) {
    					try {
    						m[i].invoke(obj,
    							new Object[] { new Integer(Integer.parseInt(value)) });
    						return;
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}

    				if (parms[0].isAssignableFrom(boolean.class)) {
    					try {
    						m[i].invoke(obj, new Object[] { new Boolean(Boolean.parseBoolean(value)) });
    						return;
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}
    			}
    		}
    	}
    	mTrace.warn("did not find a method " + obj + ", "
    		+ name + ", " + value);
    }
}
