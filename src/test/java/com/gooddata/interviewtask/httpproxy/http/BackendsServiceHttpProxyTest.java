package com.gooddata.interviewtask.httpproxy.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gooddata.interviewtask.httpproxy.backends.Backend;

public class BackendsServiceHttpProxyTest {

	@Test
	public void testBuildBackendFromJson() throws Exception {
	   	BackendsServiceHttpProxy service = new BackendsServiceHttpProxy();
		Backend backend = service.buildBackendFromJson("{\"backend\":{\"id\": \"8082\"}}");
		assertEquals("8082",backend.getId());
	}
}
