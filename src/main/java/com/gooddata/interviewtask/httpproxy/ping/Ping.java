package com.gooddata.interviewtask.httpproxy.ping;

public class Ping {
	private String body;
	private int statusCode;

	public Ping(String body, int statusCode) {
		this.body = body;
		this.statusCode = statusCode;
	}

	public static Ping OK(String body) {
		return new Ping(body,200);
	}

	public String getBody() {
		return body;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
