package com.gooddata.interviewtask.httpproxy.http;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gooddata.interviewtask.httpproxy.ping.Ping;
import com.gooddata.interviewtask.httpproxy.ping.PingService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@SuppressWarnings("unused")
@Service
public class PingServiceHttpProxy implements PingService {

	static final Map<String,String> nodeUrls = new HashMap<String, String>(2) {{
																	this.put("8082","http://localhost:8082/ping");
																	this.put("8083","http://localhost:8083/ping");
																}};

	@Override
	public Ping getPing(String preferredBackendId) {
		Collection<String> urls = getUrls(preferredBackendId);
		Client client = Client.create();
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
		if (!preferredBackendId.isEmpty()) {
			if (nodeUrls.containsKey(preferredBackendId)) {
				return Arrays.asList( nodeUrls.get(preferredBackendId) );
			} else {
				return nodeUrls.values();
			}
		} else {
			return nodeUrls.values();
		}
	}

}
