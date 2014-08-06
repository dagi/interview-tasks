package com.gooddata.interviewtask.httpproxy.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SuppressWarnings("unused")
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {ApplicationConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebMvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
