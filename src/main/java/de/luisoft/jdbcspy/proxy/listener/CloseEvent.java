package de.luisoft.jdbcspy.proxy.listener;

import de.luisoft.jdbcspy.proxy.StatementStatistics;

/**
 * The Close Event class.
 */
public class CloseEvent {

    /**
     * the event source
     */
    private final StatementStatistics mSource;

    /**
     * Constructor.
     *
     * @param source Object
     */
    public CloseEvent(StatementStatistics source) {
        mSource = source;
    }

    /**
     * Get the source event.
     *
     * @return Object
     */
    public StatementStatistics getStatementStatistics() {
        return mSource;
    }
}
