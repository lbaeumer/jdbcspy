package de.luisoft.jdbcspy.proxy.exception;


/**
 * The ResourceAlreadyClosedException class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: ResourceAlreadyClosedException.java 766 2006-06-19 18:41:37Z lui $
 */
public class ResourceAlreadyClosedException extends ProxyException {

    /** the serial version uid */
    static final long serialVersionUID = -8576761524925278661L;

    /** the method */
    private String mMethod;

    /**
     * Constructor.
     * @param txt the text
     */
    public ResourceAlreadyClosedException(String txt) {
        super(txt);
    }

    /**
     * Set the open method.
     * @param method the open method
     */
    public void setOpenMethod(String method) {
        mMethod = method;
    }

    /**
     * Get the open method.
     * @return String
     */
    public String getOpenMethod() {
        return mMethod;
    }
}
