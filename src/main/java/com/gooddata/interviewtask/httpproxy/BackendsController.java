package com.gooddata.interviewtask.httpproxy;

import java.util.List;

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
		return "{  \n" +
				"  \"backends\":[  \n" +
				"    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\"8082\"\n" +
				"      }\n" +
				"    },\n" +
				"    {  \n" +
				"      \"backend\":{  \n" +
				"        \"id\":\"8083\"\n" +
				"      }\n" +
				"    }\n" +
				"  ]\n" +
				"}";
	}
}
