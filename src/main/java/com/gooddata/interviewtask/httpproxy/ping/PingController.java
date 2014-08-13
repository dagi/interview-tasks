package com.gooddata.interviewtask.httpproxy.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ping")
public class PingController {

	@Autowired
	private PingService pingService;

	@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getPing(@RequestHeader(value="X-Backend-id", defaultValue="none") String preferredBackendId) {
		final Ping ping = pingService.getPing(preferredBackendId);
		HttpStatus status = HttpStatus.valueOf(ping.getStatusCode());
		return new ResponseEntity<String>(ping.getBody(),status);
	}

}
