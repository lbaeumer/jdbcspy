package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ExecutionEvent;

/**
 * The Execution Repeat checker.
 */
public class ExecutionRepeatCountListener extends ExecutionAdapter {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ExecutionRepeatCountListener.class);

	/** the time map */
	private Map<String, Integer> mMap;

	/** max print size */
	private static final int MAX_PRINT_SIZE = 10;

	/**
	 * Constructor.
	 */
	public ExecutionRepeatCountListener() {
		mMap = new HashMap<>();
	}

	private int repeatCountStmtSize;
	private int repeatCountThreshold;

	public int getRepeatCountStmtSize() {
		return repeatCountStmtSize;
	}

	public void setRepeatCountStmtSize(final int repeatCountStmtSize) {
		this.repeatCountStmtSize = repeatCountStmtSize;
		mMap = new HashMap<>((int) (1.35 * repeatCountStmtSize));
	}

	public int getRepeatCountThreshold() {
		return repeatCountThreshold;
	}

	public void setRepeatCountThreshold(int repeatCountThreshold) {
		this.repeatCountThreshold = repeatCountThreshold;
	}

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#startExecution
	 */
	@Override
	public void startExecution(ExecutionEvent event) {
		if (repeatCountStmtSize == 0) {
			return;
		}

		String stmt = event.getStatementStatistics().getSQL();

		Integer count;
		int size = 0;
		synchronized (mMap) {
			count = mMap.get(stmt);
			if (count != null) {
				mMap.put(stmt, new Integer(count.intValue() + 1));
				if ((count.intValue() + 1) % repeatCountThreshold == 0) {
					mTrace.warn(
							"The statement " + stmt + " in method " + event.getStatementStatistics().getExecuteCaller()
									+ " has been executed " + (count.intValue() + 1) + " times ");
				}
			} else {
				mMap.put(stmt, new Integer(1));
			}
			size = mMap.size();
		}

		if (size > repeatCountStmtSize) {
			// clear some entries
			int smallest2n = Integer.MAX_VALUE;
			int smallest = Integer.MAX_VALUE;
			int cnt = 0;
			synchronized (mMap) {
				Collection<Integer> c = mMap.values();
				for (Integer i : c) {
					if (i < smallest) {
						smallest = i;
						cnt = 1;
					} else if (i == smallest) {
						cnt++;
					} else if (i < smallest2n) {
						smallest2n = i;
					}
				}

				// delete at least 15% and maximum 50% of all entries
				if (cnt < 0.85f * mMap.size()) {
					smallest = smallest2n;
				}

				int maxDelete = mMap.size() / 2;
				for (Iterator it = mMap.entrySet().iterator(); it.hasNext();) {
					Map.Entry entry = (Map.Entry) it.next();
					cnt = ((Integer) entry.getValue()).intValue();
					if (cnt == smallest) {
						it.remove();
						maxDelete--;
						if (maxDelete == 0) {
							break;
						}
					}
				}
			}
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
	 */
	@Override
	public void clearStatistics() {
		if (repeatCountStmtSize == 0) {
			return;
		}

		synchronized (mMap) {
			mMap.clear();
		}
	}

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		if (repeatCountStmtSize == 0) {
			return null;
		}

		StringBuffer strb = new StringBuffer("[ExecutionRepeatCountListener[\n");
		Set<Map.Entry<String, Integer>> s = new TreeSet<>(new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				int c = e2.getValue() - e1.getValue();
				if (c != 0) {
					return c;
				}
				return e2.getKey().compareTo(e1.getKey());
			}
		});

		synchronized (mMap) {
			s.addAll(mMap.entrySet());
		}

		int count = MAX_PRINT_SIZE;
		for (Map.Entry<String, Integer> e : s) {
			strb.append((MAX_PRINT_SIZE - count + 1) + ": #=" + e.getValue() + ": \"" + e.getKey() + "\"\n");
			count--;
			if (count <= 0) {
				break;
			}
		}
		strb.append("]]\n");
		return strb.toString();
	}
}
