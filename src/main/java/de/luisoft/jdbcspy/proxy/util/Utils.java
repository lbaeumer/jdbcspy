package de.luisoft.jdbcspy.proxy.util;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.ProxyConnection;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The utils class.
 */
public class Utils {

    /**
     * the size formatter
     */
    public static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("0.0#");
    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger(Utils.class.getName());
    /**
     * the date formatter
     */
    public final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    /**
     * the time formatter
     */
    public final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");
    /**
     * the milli time formatter
     */
    public final SimpleDateFormat MILLI_TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * Get the execution class.
     *
     * @param proxy the proxy object
     * @return String
     */
    public static String getExecClass(Object proxy) {
        Throwable t = new Throwable();
        StackTraceElement[] e = t.getStackTrace();
        StringBuilder execClass = new StringBuilder();

        String ignorePath = (String) ClientProperties.getProperty(ClientProperties.DB_TRACE_CLASS_IGNORE_REGEXP);
        int j = 1;
        for (int i = 0; i < e.length; i++) {
            StackTraceElement el = e[i];
            if (el.getClassName().startsWith("de.luisoft.jdbcspy.proxy")
                    || proxy.getClass().getName().equals(el.getClassName())
                    || el.getClassName().matches(ignorePath)) {
                continue;
            }

            if (i > 0 && e[i - 1].getClassName().startsWith(ProxyConnection.class.getPackage().getName())
                    && el.getMethodName().equals("close")) {
                // this is the proxy class
                continue;
            }

            if (j > 1) {
                execClass.append("|");
            }
            execClass.append(getPrintClass(el));
            if (j >= ClientProperties.getInt(ClientProperties.DB_TRACE_DEPTH)) {
                break;
            }

            j++;
        }

        return execClass.toString();
    }

    /**
     * Get the exec class.
     *
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
     * Remove the hints.
     *
     * @param sql sql
     * @return output sql
     */
    public static String removeHints(String sql) {
        return sql.replaceAll("/\\*\\+[^/]*\\*/", "");
    }

    /**
     * Is the statement traceable?
     *
     * @param regExps List of regular expressions
     * @return boolean
     */
    public static String isTraceClass(List<String> regExps) {
        if (!regExps.isEmpty()) {
            Throwable t = new Throwable();
            StackTraceElement[] e = t.getStackTrace();
            for (StackTraceElement el : e) {
                for (Object exp : regExps) {
                    String regExp = (String) exp;
                    if ((el.getClassName() + "." + el.getMethodName()
                            // + ":" + el.getLineNumber()
                    ).matches(regExp)) {
                        return regExp;
                    } else {
                        if (mTrace.isLoggable(Level.FINE)) {
                            mTrace.fine(el.getClassName() + "." + el.getMethodName()
                                    // + ":" + el.getLineNumber()
                                    + " does not match " + regExp);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Is the statement traceable?
     *
     * @param regExps List of regular expressions
     * @param sql     the sql command
     * @return the reg exp that matches
     */
    private static String isTraceSql(List<String> regExps, String sql) {
        for (Object exp : regExps) {
            String regExp = (String) exp;
            if (sql.replaceAll("[\n\r]", "").matches(regExp)) {
                return regExp;
            }
        }
        return null;
    }

    /**
     * isTrace.
     *
     * @param sql String
     * @return the reg Exp
     */
    public static String isTrace(String sql) {
        List<String> debug = ClientProperties.getList(ClientProperties.DB_STMT_DEBUG_CLASS_EXP);
        String regExp = Utils.isTraceClass(debug);
        if (regExp != null) {
            return regExp;
        } else {
            debug = ClientProperties.getList(ClientProperties.DB_STMT_DEBUG_SQL_EXP);
            return Utils.isTraceSql(debug, sql);
        }
    }

    /**
     * isHistoryTrace.
     *
     * @param sql String
     * @return boolean
     */
    public static String isHistoryTrace(String sql) {
        List<String> debug = ClientProperties.getList(ClientProperties.DB_STMT_HISTORIZE_CLASS_EXP);
        String regExp = Utils.isTraceClass(debug);
        if (regExp != null) {
            return regExp;
        } else {
            debug = ClientProperties.getList(ClientProperties.DB_STMT_HISTORIZE_SQL_EXP);
            return Utils.isTraceSql(debug, sql);
        }
    }

    /**
     * Get the type name by the typeId.
     *
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
     *
     * @param id int
     * @return String
     */
    public static String getIsolationLevel(int id) {
        switch (id) {
            case Connection.TRANSACTION_NONE:
                return "NONE";
            case Connection.TRANSACTION_READ_COMMITTED:
                return "readCommitted";
            case Connection.TRANSACTION_READ_UNCOMMITTED:
                return "readUncommitted";
            case Connection.TRANSACTION_REPEATABLE_READ:
                return "repeatableRead";
            case Connection.TRANSACTION_SERIALIZABLE:
                return "serializable";
            default:
                return "???";
        }
    }

    public static void setProperty(final Object obj, final String name, final String value) {
        Class c = obj.getClass();
        final String setter = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Method[] m = c.getMethods();
        for (Method method : m) {
            if (method.getName().equals(setter)) {
                Class[] parms = method.getParameterTypes();
                if (parms.length == 1) {
                    if (parms[0].isAssignableFrom(String.class)) {
                        try {
                            method.invoke(obj, value);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (parms[0].isAssignableFrom(int.class)) {
                        try {
                            method.invoke(obj, Integer.parseInt(value));
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (parms[0].isAssignableFrom(boolean.class)) {
                        try {
                            method.invoke(obj, Boolean.parseBoolean(value));
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        mTrace.warning("did not find a setter " + setter + " in class " + obj.getClass());
    }

    /**
     * Get the size string.
     *
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
     *
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

        return (d != 0 ? d + "d " : "") + (h != 0 ? h + "h " : "") + (m != 0 ? m + "m " : "") + (s != 0 ? s + "s" : "");
    }

    /**
     * Get the method signature.
     *
     * @param method Method
     * @param args   Object[]
     * @return String
     */
    public static String getMethodSignature(Method method, Object[] args) {
        StringBuilder strb = new StringBuilder(
                (method != null ? method.getName() : "")
                        + "(");
        for (int i = 0; args != null && i < args.length; i++) {
            if (i != 0) {
                strb.append(", ");
            }
            strb.append(args[i]);
        }
        strb.append(")");
        return strb.toString();
    }

    /**
     * Get the formatted date.
     *
     * @param date date
     * @return String
     */
    public String getFormattedDate(Date date) {
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        if (cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
                && cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return TIME_FORMATTER.format(date);
        } else {
            return DATE_FORMATTER.format(date);
        }
    }
}
