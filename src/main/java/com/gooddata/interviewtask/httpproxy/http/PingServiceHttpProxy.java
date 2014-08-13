package com.gooddata.interviewtask.httpproxy.http;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gooddata.interviewtask.httpproxy.config.NodeList;
import com.gooddata.interviewtask.httpproxy.ping.Ping;
import com.gooddata.interviewtask.httpproxy.ping.PingService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Implemenation of {@link PingService} delegating the requests to HTTP requests to the backends
 * whose URLs are defined in nodeList.
 *
 * @see #getPing(String)
 * @see #nodeList
 */
@SuppressWarnings("unused")
@Service
public class PingServiceHttpProxy implements PingService {

	/**
	 * Map of the node URLs identified by port number of the backend
	 */
	@Inject
	private NodeList nodeList;

	private static final String PINGING_URL_SUFFIX = "/ping";

	private static final Integer TIMEOUT_IN_MILLISECONDS = 5000; // timeout 5 seconds

	@Inject
	private Client httpClient;

	/**
	 * Sends the <strong>ping</strong> requests to the backends in nodeUrls
	 * <ul>
	 *      <li>A backend might be temporary unavailable and immediately respond with HTTP status 503.
	 *          When a backend returns 503 the proxy will dispatch the request to another backend.</li>
	 *      <li>A backend might be slowed down and respond after a while.
	 *          When both backends do not respond the proxy will respond by HTTP status 503.</li>
	 * </ul>
	 * @param preferredBackendId id (port number) of preferred backend node
	 * @return {@link Ping} structure containing response text (from the backend) and status code
	 */
	@Override
	public Ping getPing(String preferredBackendId) {
		Collection<String> urls = getUrls(preferredBackendId);
		Client client = httpClient;
		client.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
		client.setReadTimeout(TIMEOUT_IN_MILLISECONDS);
		String errorMessage = "";
		for (String url : urls) {
			WebResource webResource = client.resource(url);
			try {
				return Ping.OK(webResource.get(String.class));
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		}
		return new Ping(errorMessage,503);
	}

	private Collection<String> getUrls(String preferredBackendId) {
		if (!preferredBackendId.isEmpty() && !"none".equals(preferredBackendId)) {
			try {
				Integer id = Integer.valueOf(preferredBackendId);
				if (nodeList.containsKey(id)) {
					return Arrays.asList( nodeList.get(id).concat(PINGING_URL_SUFFIX) );
				}
			} catch (NumberFormatException ignored) { } // in case of non-numeric preferredBackendId return all node Urls
		}
		return nodeList.getBaseUrlsWithSuffix(PINGING_URL_SUFFIX);
	}

}
