package de.luisoft.jdbcspy.proxy.listener.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.luisoft.jdbcspy.proxy.listener.ExecutionAdapter;
import de.luisoft.jdbcspy.proxy.listener.ResourceEvent;

/**
 * The Execution resource listener.
 */
public class ExecutionResourceListener extends ExecutionAdapter {

	/** the logger object for tracing */
	private static final Log mTrace = LogFactory.getLog(ExecutionResourceListener.class);

	/** the resource map String-&gt; Integer */
	private Map<String, Entry> mResource;

	/**
	 * Constructor.
	 */
	public ExecutionResourceListener() {
		mResource = new LinkedHashMap<>();
	}

	private boolean throwWarnings;

	public boolean isThrowWarnings() {
		return throwWarnings;
	}

	public void setThrowWarnings(boolean throwWarnings) {
		this.throwWarnings = throwWarnings;
	}

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#resourceFailure
	 */
	@Override
	public void resourceFailure(ResourceEvent event) {
		synchronized (mResource) {
			Entry entry = mResource.get(event.getOpenMethod());
			if (entry == null) {
				entry = new Entry();
				entry.count = 1;
				entry.cause = event.getCause();

				mResource.put(event.getOpenMethod(), entry);
				if (!throwWarnings) {
					mTrace.warn("resource failure in " + event.getMethod(), event.getCause());
				}
			} else {
				entry.count++;
				if (entry.count % 10 == 0) {
					mTrace.warn("resource failure in " + event.getMethod() + " occurred " + entry.count + " times");
				}
			}
		}
	};

	/**
	 * @see de.luisoft.jdbcspy.proxy.listener.ExecutionListener#clearStatistics
	 */
	@Override
	public void clearStatistics() {
		synchronized (mResource) {
			mResource.clear();
		}
	}

	/**
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString() {
		if (mResource.isEmpty()) {
			return null;
		}

		StringBuffer strb = new StringBuffer("[ExecutionResourceListener[");
		int i = 0;
		synchronized (mResource) {
			for (Map.Entry<String, Entry> entry : mResource.entrySet()) {
				if (i == 0) {
					strb.append("\n");
				}
				Entry e = entry.getValue();
				strb.append(i + ": " + e.cause.getMessage() + " (#=" + e.count + ")\n");
				i++;
			}

		}
		strb.append("]]\n");
		return strb.toString();
	}

	/**
	 * The entry class
	 */
	private class Entry {
		/** the count */
		private int count;
		/** the cause exception */
		private Exception cause;
	}
}
