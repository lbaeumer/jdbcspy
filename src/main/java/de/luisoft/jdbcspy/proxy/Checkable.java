package de.luisoft.jdbcspy.proxy;

import de.luisoft.jdbcspy.proxy.exception.ProxyException;

/**
 * Check if the object was correctly closed.
 */
public interface Checkable {

	/**
	 * Check if the object is closed.
	 * 
	 * @throws ProxyException
	 *             if the resource was not closed
	 */
	void checkClosed() throws ProxyException;

	/**
	 * Is the statement closed?
	 * 
	 * @return boolean
	 */
	boolean isClosed();
}
