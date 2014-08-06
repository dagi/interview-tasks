package com.gooddata.interviewtask.httpproxy;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Service
public class BackendsServiceHttpProxy implements BackendsService {
	@Override
	public List<Backend> getBackends() {
		String statusNode1 = getAliveStatus("http://localhost:8082/alive");
		String statusNode2 = getAliveStatus("http://localhost:8083/alive");
		return Arrays.asList( new Backend("8082"), new Backend("8083") );

	}

	private String getAliveStatus(String endpointUrl) {
		Client client = Client.create();
		WebResource webResource = client.resource(endpointUrl);
		return webResource.get(String.class);
	}
}
