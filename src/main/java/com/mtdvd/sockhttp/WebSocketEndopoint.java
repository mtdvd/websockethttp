package com.mtdvd.sockhttp;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maty.david
 * @since 4/19/2014
 */
@ServerEndpoint("/ws/{requestUri}")
public class WebSocketEndopoint {

    private static HashMap<String, WebSocketMessageHandler> requestUrisToHandlersMap = new HashMap<>();
    private        ObjectMapper                             jsonObjectMapper         = new ObjectMapper();

    public WebSocketEndopoint() {
        requestUrisToHandlersMap.putAll(createMapOfRequestUrisToHandlers(this));
    }

    Map<String, WebSocketMessageHandler> createMapOfRequestUrisToHandlers(final Object instance) {
        final HashMap<String, WebSocketMessageHandler> webSocketMethodsHandler = new HashMap<>();
        ReflectionUtils.doWithMethods(instance.getClass(), new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                RequestMapping requestMappingAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                if (requestMappingAnnotation != null) {
                    String[] requestMappingValues = requestMappingAnnotation.value();
                    for (String requestMappingValue : requestMappingValues) {
                        webSocketMethodsHandler.put(requestMappingValue, new WebSocketMessageHandler(method, instance));
                    }
                }
            }
        });
        return webSocketMethodsHandler;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected ... " + session.getId());
    }


    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            System.out.println("msg: " + msg);
            Object methodReturnValue = invokeMatchingHandler(session.getRequestURI(), msg);
            session.getBasicRemote().sendText(jsonObjectMapper.writeValueAsString(methodReturnValue));
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e);
        }
    }

    private Object invokeMatchingHandler(URI requestURI, String msg) throws IOException, IllegalAccessException, InvocationTargetException {
        //find handler to invoke
        String requestKey = requestURI.toString().substring("/ws".length());
        WebSocketMessageHandler webSocketMessageHandler = requestUrisToHandlersMap.get(requestKey);
        Method method = webSocketMessageHandler.getMethod();
        //convert json message to matching object type
        Object jsonMessage = jsonObjectMapper.readValue(msg, method.getParameterTypes()[0]);
        //invoke handler
        return method.invoke(webSocketMessageHandler.getInstance(), jsonMessage);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }


}

