package de.luisoft.jdbcspy.proxy.listener;

/**
 * The ResourceEvent class.
 */
public class ResourceEvent {

	/** the event source */
	private Exception mCause;

	/** the resource */
	private String mOpenMethod;

	/** the proxy object */
	private String mMethod;

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            the cause
	 * @param openMethod
	 *            the open method
	 * @param method
	 *            the method
	 */
	public ResourceEvent(Exception cause, String openMethod, String method) {
		mCause = cause;
		mOpenMethod = openMethod;
		mMethod = method;
	}

	/**
	 * Get the source event.
	 * 
	 * @return Object
	 */
	public Exception getCause() {
		return mCause;
	}

	/**
	 * Get the open method.
	 * 
	 * @return String
	 */
	public String getOpenMethod() {
		return mOpenMethod;
	}

	/**
	 * Get the method.
	 * 
	 * @return methodObject
	 */
	public String getMethod() {
		return mMethod;
	}
}
