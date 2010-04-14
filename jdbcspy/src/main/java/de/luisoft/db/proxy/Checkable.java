package de.luisoft.db.proxy;

import de.luisoft.db.proxy.exception.ProxyException;

/**
 * Check the consistence of the object.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: Checkable.java 747 2006-06-08 18:28:10Z lui $
 */
public interface Checkable {

    /**
     * Check if the object is closed.
     * @throws ProxyException if the resource was not closed
     */
    void checkClosed() throws ProxyException;

    /**
     * Is the statement closed?
     * @return boolean
     */
    boolean isClosed();
}
