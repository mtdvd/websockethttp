websockethttp
=============

A Java based project that shows an example of using Spring's [@RequestMapping](http://docs.spring.io/spring-framework/docs/4.0.x/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) annotation for serving not only http requests but also as a websocket endpoint.

## Implementation
A Controller that needs to serve both websocket and http requests should simply extends `WebSocketEndpoint`
   
## Running the example
### Prerequisites
* Java 7 is installed on your machine (For just running the example you may download only the JRE from [here](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html))
* Environment variable `JAVA_HOME` is set with the location of a JAVA home dir

### Building & Executing


#### Instructions:
To compile and run the tests, open your command line console in the project root and type:
 
    mvnw install

For executing the demo:

1. type:

    `mvnw exec:java`
		
2. Open your browser and navigate to `http://localhost:8080/index.jsp`
	
#### Noteworthy:
To facilitate the compile process the project uses [maven-wrapper-plugin](https://github.com/bdemers/maven-wrapper).
    
To make the execution easy it uses embedded [tomcat](http://tomcat.apache.org/) and [Exec Maven Plugin](http://mojo.codehaus.org/exec-maven-plugin/)








