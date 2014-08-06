package com.gooddata.interviewtask.httpproxy;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.springframework.test.util.ReflectionTestUtils;

public class BackendsControllerTest {

	public static final String PORT_1 = "8082";
	public static final String PORT_2 = "8083";
	private BackendsController backendsController;
	private BackendsService backendsServiceMock;

	@Before
	public void setUp() throws Exception {
		backendsController = new BackendsController();
		backendsServiceMock = EasyMock.createMock(BackendsService.class);
		ReflectionTestUtils.setField(backendsController, "backendsService", backendsServiceMock);
	}

	@Test
	public void testOneBackend() throws Exception {
		// setup
		List<Backend> listOfOneBackend = Arrays.asList( new Backend(PORT_1) );
		EasyMock.expect(backendsServiceMock.getBackends()).andReturn(listOfOneBackend);
		EasyMock.replay(backendsServiceMock);

		// test
		String backendsJson = backendsController.getBackends();

		String expectedJson = "{  \n" +
				"  \"backends\":[  \n" +
				"    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\""+PORT_1+"\"\n" +
				"      }\n" +
				"    }\n" +
				"  ]\n" +
				"}";
		assertEquals(expectedJson, backendsJson);
	}

	@Test
	public void testTwoBackends() throws Exception {
		// setup
		List<Backend> listOfOneBackend = Arrays.asList( new Backend(PORT_1), new Backend(PORT_2) );
		EasyMock.expect(backendsServiceMock.getBackends()).andReturn(listOfOneBackend);
		EasyMock.replay(backendsServiceMock);

		// test
		String backendsJson = backendsController.getBackends();

		String expectedJson = "{  \n" +
				"  \"backends\":[  \n" +
				"    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\""+PORT_1+"\"\n" +
				"      }\n" +
				"    },\n" +
				"    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\""+PORT_2+"\"\n" +
				"      }\n" +
				"    }\n" +
				"  ]\n" +
				"}";
		assertEquals(expectedJson, backendsJson);
	}
}
