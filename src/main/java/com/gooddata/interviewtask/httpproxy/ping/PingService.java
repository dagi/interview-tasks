package com.gooddata.interviewtask.httpproxy.ping;

/**
 * Service providing the processing of a ping request
 */
public interface PingService {
	/**
	 * Process the get request according to concrete implementation
	 * @param preferredBackendId Id of the backend node preferred by the client
	 *                              (any value other than "8082" or "8083" serves as an indication of no preferrence)
	 * @return {@link Ping} structure containing response text (from the backend) and status code
	 */
	Ping getPing(String preferredBackendId);
}
