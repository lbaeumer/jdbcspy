package de.luisoft.jdbcspy.proxy.exception;


/**
 * The ResourceNotClosedException class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ResourceNotClosedException.java 766 2006-06-19 18:41:37Z lui $
 */
public class ResourceNotClosedException extends ProxyException {

    /** the serial version uid */
    static final long serialVersionUID = -7961795386461897591L;

    /** the method */
    private String method;

    /**
     * Constructor.
     * @param txt the text
     */
    public ResourceNotClosedException(String txt) {
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
