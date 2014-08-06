package com.gooddata.interviewtask.httpproxy.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gooddata.interviewtask.httpproxy.Backend;
import com.gooddata.interviewtask.httpproxy.BackendsService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Service
public class BackendsServiceHttpProxy implements BackendsService {

	static final String[] nodeUrls = {"http://localhost:8082/alive", "http://localhost:8083/alive"};

	@Override
	public List<Backend> getBackends() {
		List<Backend> result = new ArrayList<Backend>();
		for (String nodeUrl : nodeUrls) {
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
		return new Backend(id.toString());
	}

	private String getAliveStatus(String endpointUrl) {
		Client client = Client.create();
		WebResource webResource = client.resource(endpointUrl);
		try {
			return webResource.get(String.class);
		} catch (Exception e) {
			return "";
		}
	}
}
