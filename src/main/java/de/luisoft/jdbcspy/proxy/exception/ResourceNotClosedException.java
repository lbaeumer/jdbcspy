package de.luisoft.jdbcspy.proxy.exception;

/**
 * The ResourceNotClosedException class.
 */
public class ResourceNotClosedException extends ProxyException {

    /**
     * the serial version uid
     */
    static final long serialVersionUID = -7961795386461897591L;

    /**
     * the method
     */
    private String method;

    /**
     * Constructor.
     *
     * @param txt the text
     */
    public ResourceNotClosedException(String txt) {
        super(txt);
    }

    /**
     * Get the open method.
     *
     * @return String
     */
    @Override
    public String getOpenMethod() {
        return method;
    }

    /**
     * Set the open method.
     *
     * @param m the open method
     */
    @Override
    public void setOpenMethod(String m) {
        method = m;
    }
}
