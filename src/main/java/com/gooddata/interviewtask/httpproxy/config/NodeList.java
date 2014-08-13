package com.gooddata.interviewtask.httpproxy.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public class NodeList {
	private Map<Integer,String> nodes;

	public NodeList() {
		this.nodes = new LinkedHashMap<>();
	}

	public NodeList add(Integer id, String baseUrl) {
		this.nodes.put(id,baseUrl);
		return this;
	}

	@SuppressWarnings("unused")
	public Map<Integer,String> getNodes() {
		return nodes;
	}

	public Collection<String> getBaseUrls() {
		return nodes.values();
	}

	public boolean containsKey(Integer id) {
		return nodes.containsKey(id);
	}

	public String get(Integer id) {
		return nodes.get(id);
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getBaseUrlsWithSuffix(final String suffix) {
		return CollectionUtils.collect(
				getBaseUrls(),
				new Transformer() {
					@Override
					public Object transform(Object input) {
						return ((String) input).concat(suffix);
					}
				}
		);
	}
}
