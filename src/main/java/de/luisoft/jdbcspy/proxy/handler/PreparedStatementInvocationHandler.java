package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.proxy.util.Utils;

import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The statement handler.
 */
public class PreparedStatementInvocationHandler extends AbstractStatementInvocationHandler {

    /**
     * the bind variables
     */
    private final Map<Object, Object> mBindVariables = new HashMap<>();

    /**
     * the batch bind variables
     */
    private final Map<Object, Object> mBatchBindVariables = new HashMap<>();

    private final String mSql;
    /**
     * the batched element size
     */
    private int mBatchedSize;

    /**
     * Constructor.
     *
     * @param theStmt the original statement
     * @param theSql  the sql string
     * @param method  the method
     */
    public PreparedStatementInvocationHandler(Statement theStmt,
                                              String theSql, String method) {
        super(theStmt, theSql, method);
        mSql = theSql;
    }

    @Override
    protected void handle(Method method, Object[] args) throws SQLException {
        if (method.getName().startsWith("registerOutParameter") && args.length >= 2) {
            mBindVariables.put(args[0], Utils.getTypeName((Number) args[1]));

        } else if (method.getName().startsWith("set") && args.length >= 2) {
            handleSet(method, args);
        } else if (method.getName().equals("addBatch")) {
            handleAddBatch();
        }
    }

    /**
     * Handle the close method.
     *
     * @param proxy  the proxy
     * @param method the method
     * @param args   the arguments
     * @return the return value
     * @throws Throwable on error
     */
    @Override
    protected Object handleClose(Object proxy, Method method, Object[] args, boolean checkClosed) throws Throwable {

        Object obj = super.handleClose(proxy, method, args, checkClosed);
        mResultSetItemCount += mBatchedSize;
        return obj;
    }

    /**
     * Get the SQL Code.
     *
     * @return the sql code
     */
    @Override
    public String getSQL() {

        String[] s = mSql.split("\\?");
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < s.length; i++) {
            if (i > 0) {
                Object obj = getBindValue(i);
                if (obj != null) {
                    result.append(obj);
                }
            }
            result.append(s[i]);
        }

        Object obj = getBindValue(i);
        if (obj != null) {
            result.append(obj);
        }
        String sql = result.toString();

        int maxLen = ClientProperties.getInt(ClientProperties.DB_DISPLAY_SQL_STRING_MAXLEN);
        if (maxLen > 0 && sql.length() > maxLen) {

            sql = sql.substring(0, maxLen) + "...";
        }
        return sql;
    }

    /**
     * Get the item count.
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        int x = super.getItemCount();
        if (getState() != CLOSED) {
            return x + mBatchedSize;
        }
        return x;
    }

    /**
     * Handle the addBatch method
     */
    protected void handleAddBatch() {
        mBatchedSize++;
        if (mBatchedSize > 100) {
            // heuristic: do not gather more that 100 elements
            return;
        }
        for (Map.Entry entry : mBindVariables.entrySet()) {
            List l = (List) mBatchBindVariables.get(entry.getKey());
            if (l == null) {
                l = new ArrayList();
                mBatchBindVariables.put(entry.getKey(), l);
            }
            l.add(entry.getValue());
        }
    }

    /**
     * Get the bind value.
     *
     * @param i int
     * @return Object
     */
    protected Object getBindValue(int i) {
        if (mBatchedSize == 0) {
            return mBindVariables.get(i);
        } else {
            StringBuilder strb = new StringBuilder("{");
            List l = (List) mBatchBindVariables.get(i);
            if (l == null) {
                return null;
            }

            int c = 0;
            for (Iterator it = l.iterator(); c < 10 && it.hasNext(); c++) {
                if (c > 0) {
                    strb.append(", ");
                }
                strb.append(it.next());
            }

            if (l.size() > 10) {
                strb.append(", ... #=").append(mBatchedSize);
            }
            strb.append("}");
            return strb.toString();
        }
    }

    /**
     * Handle the setXXX method.
     *
     * @param method Method
     * @param args   Object[]
     * @throws SQLException on sql exception
     */
    private void handleSet(Method method, Object[] args) throws SQLException {
        mBindVariables.put(args[0], getArgName(method, args[1]));
    }

    /**
     * Get the argument name.
     *
     * @param method the method
     * @param arg    the argument
     * @return the printable name
     * @throws SQLException on error
     */
    private String getArgName(Method method, Object arg) throws SQLException {

        if ("setNull".equals(method.getName())) {
            return "NULL";
        }
        return getArgName(arg);
    }

    /**
     * Get the argument name.
     *
     * @param arg Object
     * @return String
     * @throws SQLException if the access to the array object fails
     */
    private String getArgName(Object arg) throws SQLException {

        if (arg == null) {
            return "NULL";
        } else if (arg instanceof String) {
            return "'" + arg + "'";
        } else if (arg instanceof byte[]) {
            byte[] b = (byte[]) arg;
            StringBuilder strb = new StringBuilder("'");
            for (int i = 0; i < b.length; i++) {
                strb.append(Integer.toHexString(b[i] < 0 ? b[i] + 256 : b[i]));
                // heuristic to shortcut arrays
                if (i >= 100 && b.length > 120) {
                    strb.append(", ... #=").append(b.length);
                    break;
                }
            }
            strb.append("'");
            return strb.toString();
        } else if (arg instanceof Array) {
            Array a = (Array) arg;
            Object[] o = (Object[]) a.getArray();
            StringBuilder strb = new StringBuilder("[");
            for (int i = 0; i < o.length; i++) {
                if (i > 0) {
                    strb.append(", ");
                }
                strb.append(getArgName(o[i]));
                // heuristic to shortcut arrays
                if (i >= 100 && o.length > 120) {
                    strb.append(", ... #=").append(o.length);
                    break;
                }
            }
            strb.append("]");
            return strb.toString();
        } else {
            return arg.toString();
        }
    }
}
