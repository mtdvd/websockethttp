package com.mtdvd.sockhttp;

/**
 * @author maty.david
 * @since 4/20/2014
 */
public class Greeting {

    private String content;
       
    public Greeting(String name) {
        this.content = "Hello " + name + "!";
    }

    public String getContent() {
        return content;
    }

}
