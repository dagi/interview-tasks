package com.gooddata.interviewtask.httpproxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.gooddata.interviewtask.httpproxy.Application;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class ApplicationConfig {
}
