package com.gooddata.interviewtask.httpproxy.config;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NodeListTest {
	public static final String BASE_URL1 = "http://localhost:8082";
	public static final String BASE_URL2 = "http://localhost:8083";
	private NodeList nodeList;

	@Before
	public void setUp() throws Exception {
	 	nodeList = new NodeList();
	}

	@Test
	public void orderIsPreserved() {
		nodeList.add(8082, BASE_URL1)
				.add(8083, BASE_URL2);
		String[] nodeArray = nodeList.getBaseUrls().toArray(new String[2]);
		assertEquals(BASE_URL1,nodeArray[0]);
		assertEquals(BASE_URL2,nodeArray[1]);
	}
}
