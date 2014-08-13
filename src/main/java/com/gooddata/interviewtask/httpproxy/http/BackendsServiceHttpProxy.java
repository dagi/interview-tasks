package com.gooddata.interviewtask.httpproxy.http;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gooddata.interviewtask.httpproxy.backends.Backend;
import com.gooddata.interviewtask.httpproxy.backends.BackendsService;
import com.gooddata.interviewtask.httpproxy.config.NodeList;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Implemenation of {@link BackendsService} probing nodes in nodeUrls and listing them
 *
 * @see #nodeList
 */
@Service
public class BackendsServiceHttpProxy implements BackendsService {

	/**
	 * Bases of the URLs of the probing service in backend nodes
	 */
	@Inject
	private NodeList nodeList;

	public static final String PROBING_URL_SUFFIX = "/alive";

	@Inject
	private Client httpClient;

	/**
	 * Handles a request by dispatching GET /alive requests to backends defined in {@link #nodeList},
	 * merges their responses and returns a JSON document with the following structure
	 * <pre>
	 *     {
	 *      "backends":[
	 *                  {
	 *                    "backend":{
	 *                                "id":"%ID"
	 *                              }
	 *                  },
	 *                  {
	 *                    "backend":{
	 *                                "id":"%ID"
	 *                              }
	 *                  }
	 *                 ]
	 *    }
	 * </pre>
	 * @return JSON document listing currently active nodes
	 */
	@Override
	public List<Backend> getBackends() {
		List<Backend> result = new ArrayList<>();
		for (String nodeUrl : nodeList.getBaseUrlsWithSuffix(PROBING_URL_SUFFIX)) {
			String nodeStatusJson = getAliveStatus(nodeUrl);
			if (!nodeStatusJson.isEmpty()) {
				Backend backend = buildBackendFromJson(nodeStatusJson);
				result.add(backend);
			}
		}
		return result;

	}

	Backend buildBackendFromJson(String jsonAliveStatus) {
		JSONObject obj = new JSONObject(jsonAliveStatus);
		Integer id = (Integer)obj.getJSONObject("backend").get("id");
		return new Backend(id);
	}

	/**
	 * Probes the backend node. If alive, it should return a JSON document {"backend":{"id": "%ID"}}
	 * @param endpointUrl URL of the probed backend node
	 * @return JSON response of the backend node or empty string in case of an error (e.g. when the node is not available)
	 */
	private String getAliveStatus(String endpointUrl) {
		Client client = httpClient;
		WebResource webResource = client.resource(endpointUrl);
		try {
			return webResource.get(String.class);
		} catch (Exception e) {
			return "";
		}
	}

}
