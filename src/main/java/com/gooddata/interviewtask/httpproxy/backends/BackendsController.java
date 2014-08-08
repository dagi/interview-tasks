package com.gooddata.interviewtask.httpproxy.backends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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

	private String buildJsonStringForBackends(final List<Backend> backends) {
		Map<String,List> backendsMap = new HashMap<String, List>() {{
			this.put("backends",
					new ArrayList<Map>() {{
						for (final Backend backend : backends) {
							this.add(
									new HashMap<String,Map>() {{
										this.put("backend",
												new HashMap<String,Integer>() {{
													this.put("id", Integer.valueOf(backend.getId()));
												}}
										);
									}}
							);
						}
					}}
			);
		}};
		return new JSONObject(backendsMap).toString();
	}

}
