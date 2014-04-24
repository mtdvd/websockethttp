package com.mtdvd.sockhttp;

import java.lang.reflect.Method;

/**
 * @author maty.david
 * @since 4/20/2014
 */
class WebSocketMessageHandler {
    private Method method;
    private Object instance;

    WebSocketMessageHandler(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }
}
