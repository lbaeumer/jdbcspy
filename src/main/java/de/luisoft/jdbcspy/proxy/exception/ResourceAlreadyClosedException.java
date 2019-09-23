package de.luisoft.jdbcspy.proxy.exception;

/**
 * The ResourceAlreadyClosedException class.
 */
public class ResourceAlreadyClosedException extends ProxyException {

	/** the serial version uid */
	static final long serialVersionUID = -8576761524925278661L;

	/** the method */
	private String mMethod;

	/**
	 * Constructor.
	 * 
	 * @param txt
	 *            the text
	 */
	public ResourceAlreadyClosedException(String txt) {
		super(txt);
	}

	/**
	 * Set the open method.
	 * 
	 * @param method
	 *            the open method
	 */
	@Override
	public void setOpenMethod(String method) {
		mMethod = method;
	}

	/**
	 * Get the open method.
	 * 
	 * @return String
	 */
	@Override
	public String getOpenMethod() {
		return mMethod;
	}
}
