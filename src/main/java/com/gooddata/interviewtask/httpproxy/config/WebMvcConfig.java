package com.gooddata.interviewtask.httpproxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.gooddata.interviewtask.httpproxy"})
public class WebMvcConfig extends WebMvcConfigurationSupport {
}
