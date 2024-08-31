package de.luisoft.jdbcspy.proxy.handler;

import de.luisoft.jdbcspy.proxy.util.Utils;

import javax.transaction.xa.XAResource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The connection handler.
 */
public class XAResourceInvocationHandler implements InvocationHandler {

    /**
     * the logger object for tracing
     */
    private static final Logger mTrace = Logger.getLogger("jdbcspy.xaresource");

    /**
     * the underlying connection
     */
    private final XAResource uXa;
    private final XAConnectionInvocationHandler xaConnectionInvocationHandler;

    /**
     * The Constructor.
     *
     * @param xa the xa resource
     */
    public XAResourceInvocationHandler(XAResource xa, XAConnectionInvocationHandler xaConnectionInvocationHandler) {
        uXa = xa;
        this.xaConnectionInvocationHandler = xaConnectionInvocationHandler;
    }

    /**
     * @see InvocationHandler
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        try {
            if (mTrace.isLoggable(Level.FINE)) {
                mTrace.fine("call " + uXa.getClass() + "." + Utils.getMethodSignature(method, args));
            }

            if ("end".equals(method.getName())) {
                xaConnectionInvocationHandler.endTx();
            }

            return method.invoke(uXa, args);
        } catch (Exception e) {
            mTrace.log(Level.SEVERE, "unknown error in " + uXa.getClass()
                    + "." + method.getName() + " failed for " + this, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
