package de.luisoft.jdbcspy.proxy;

/**
 * Check if the object was correctly closed.
 */
public interface ProxyResultSet {

    /**
     * Check if the object is closed.
     */
    void checkClosed();

    /**
     * Is the statement closed?
     *
     * @return boolean
     */
    boolean isClosed();
}
