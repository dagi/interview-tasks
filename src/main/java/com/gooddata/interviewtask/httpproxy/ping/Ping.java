package com.gooddata.interviewtask.httpproxy.ping;

/**
 * Data structure containing
 * <ul>
 *     <li>body ... the text of the backend's response</li>
 *     <li>statusCode ... HTTP status code of response
 *          <ul>
 *              <li>200 (OK) when one of the backends responds well</li>
 *              <li>503 (Service unavailable) when none of the backends responds OK and in timely fashion</li>
 *          </ul>
 *     </li>
 * </ul>
 */
public class Ping {
	private String body;
	private int statusCode;

	public Ping(String body, int statusCode) {
		this.body = body;
		this.statusCode = statusCode;
	}

	/**
	 * Constructs instance of Ping with status code == 200 (OK)
	 * @param body the body of the response
	 * @return newly created instance of {@link Ping}
	 */
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
