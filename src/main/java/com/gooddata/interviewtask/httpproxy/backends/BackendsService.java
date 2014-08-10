package com.gooddata.interviewtask.httpproxy.backends;

import java.util.List;

/**
 * Service providing the processing of a <strong>backends</strong> request
 */
public interface BackendsService {
	/**
	 * Process the <strong>backends</strong> request according to concrete implementation
	 * @return list of {@link Backend} structures describing currently alive backend nodes
	 */
	public List<Backend> getBackends();
}
