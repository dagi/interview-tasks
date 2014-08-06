package com.gooddata.interviewtask.httpproxy;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backends")
public class BackendsController {

	@Autowired
	private BackendsService backendsService;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public String getBackends() {
		List<Backend> backends = backendsService.getBackends();
		return buildJsonStringForBackends(backends);
	}

	private String buildJsonStringForBackends(List<Backend> backends) {
		return "{  \n" +
				"  \"backends\":[  \n" +
				StringUtils.join(
						CollectionUtils.collect(backends, new Transformer() {
							@Override
							public Object transform(Object input) {
								return buildJsonForSingleBackend(((Backend)input).getId());
							}
						})
						,",\n"
				)
				 +
				"\n" +
				"  ]\n" +
				"}";
	}

	private String buildJsonForSingleBackend(final String id) {
		return "    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\"" + id + "\"\n" +
				"      }\n" +
				"    }";
	}
}
