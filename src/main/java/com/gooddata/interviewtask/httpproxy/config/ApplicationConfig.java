package com.gooddata.interviewtask.httpproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jersey.api.client.Client;

/**
 * Configuration of the service tier
 */
@Configuration
public class ApplicationConfig {

	@Bean
	public Client httpClient() {
		return Client.create();
	}

	@Bean
	public NodeList nodeList() {
		return new NodeList()
				.add(8082,"http://localhost:8082")
				.add(8083,"http://localhost:8083");
	}
}
