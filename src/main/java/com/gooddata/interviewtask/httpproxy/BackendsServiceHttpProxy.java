package com.gooddata.interviewtask.httpproxy;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BackendsServiceHttpProxy implements BackendsService {
	@Override
	public List<Backend> getBackends() {
		return Arrays.asList( new Backend("8082"), new Backend("8083") );
	}
}
