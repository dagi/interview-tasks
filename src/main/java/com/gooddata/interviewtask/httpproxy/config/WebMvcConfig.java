package com.gooddata.interviewtask.httpproxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Configuration of Web MVC
 * Setup of packages to be scanned for injection of autowired dependencies
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.gooddata.interviewtask.httpproxy"})
public class WebMvcConfig extends WebMvcConfigurationSupport {
}
