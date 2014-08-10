package com.gooddata.interviewtask.httpproxy.backends;

/**
 * Data structure containing
 * <ul>
 *     <li>id ... id of the backend (which happens to be its port number)</li>
 * </ul>
 */
public class Backend {
	private Integer id;

	public Backend(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
