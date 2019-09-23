package de.luisoft.jdbcspy.proxy;

/**
 * The Statistics.
 */
public interface Statistics {

	/**
	 * The size in byte that was read.
	 * 
	 * @return long
	 */
	long getSize();

	/**
	 * The item count. This may be the number of result sets, the number of
	 * attached rows or the number of batch operations.
	 * 
	 * @return int
	 */
	int getItemCount();

	/**
	 * The duration of the statement (execute + iteration time)
	 * 
	 * @return long
	 */
	long getDuration();
}
