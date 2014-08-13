package com.gooddata.interviewtask.httpproxy.config;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcConfig.class})
public class WebAppConfigurationAware {

	@Inject
	protected org.springframework.web.context.WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void testBackends() throws Exception {
		mockMvc.perform(get("/backends")).andExpect(content().string(containsString("backends")));
	}

}
