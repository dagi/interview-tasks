package com.gooddata.interviewtask.httpproxy.ping;

public interface PingService {
	Ping getPing(String preferredBackendId);
}
