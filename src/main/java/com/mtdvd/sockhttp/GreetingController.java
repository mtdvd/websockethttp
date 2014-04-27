package com.mtdvd.sockhttp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController extends WebSocketEndpoint {

	@RequestMapping("/hello")
	public @ResponseBody Greeting welcome(@RequestBody HelloMessage helloMessage) {
		return new Greeting(helloMessage.getName());
	}
}