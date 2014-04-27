package com.mtdvd.sockhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testSimpleHttpHelloRequest() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String personName = "John";
        String jsonMessage = new ObjectMapper().writeValueAsString(new HelloMessage(personName));
        mockMvc.perform(post("/hello").content(jsonMessage)
                .accept(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(new Greeting(personName))));
    }

    @Test
    public void testMapWebSocketEndpoints() {
        Map<String, WebSocketMessageHandler> mapOfRequestUrisToHandlers = new WebSocketEndpoint().createMapOfRequestUrisToHandlers(new TestController());
        Assert.assertTrue(mapOfRequestUrisToHandlers.containsKey("/test"));
    }

    //Dummy controller class for tests
    class TestController {
        @RequestMapping("/test")
        public void dummyGreet() {

        }
    }
    
}
