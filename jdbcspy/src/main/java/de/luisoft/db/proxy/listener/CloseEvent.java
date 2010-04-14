package de.luisoft.db.proxy.listener;

import de.luisoft.db.proxy.StatementStatistics;

/**
 * The Close Event class.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Lui Baeumer
 * @version $Id: CloseEvent.java 724 2006-05-19 18:58:13Z lui $
 */
public class CloseEvent {

    /** the event source */
    private StatementStatistics mSource;

    /**
     * Constructor.
     * @param source Object
     */
    public CloseEvent(StatementStatistics source) {
        mSource = source;
    }

    /**
     * Get the source event.
     * @return Object
     */
    public StatementStatistics getStatementStatistics() {
        return mSource;
    }
}
