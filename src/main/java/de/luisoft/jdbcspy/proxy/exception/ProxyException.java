package de.luisoft.jdbcspy.proxy.exception;

import java.sql.SQLException;

/**
 * The Proxy Exception class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ProxyException.java 885 2007-03-18 20:46:41Z lui $
 */
public class ProxyException extends SQLException {

    /** the serial version uid */
    static final long serialVersionUID = -3690118765196760445L;

    /** the method */
    private String method;

    /**
     * Constructor.
     * @param txt the text
     */
    public ProxyException(String txt) {
        super(txt);
    }

    /**
     * Set the open method.
     * @param m the open method
     */
    public void setOpenMethod(String m) {
        method = m;
    }

    /**
     * Get the open method.
     * @return String
     */
    public String getOpenMethod() {
        return method;
    }
}
