websockethttp
=============

A Java based project that shows an example of using Spring's [@RequestMapping](http://docs.spring.io/spring-framework/docs/4.0.x/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html) annotation for serving websocket endpoints as well.

## Implementation
A Controller that needs to serve both websocket and http requests should simply extends `WebSocketEndpoint`
   
## Running the example
### Prerequisites
* Make sure you have java 7 installed and that it is in your path

### Building & Executing


#### Instructions:
To compile and run the tests, open your command line console in the project root and type:
 
    mvnw install

To see the live demo, type:

	mvnw exec:java
	
#### Worth mentioning:
To facilitate the compile process the project uses [maven-wrapper-plugin](https://github.com/bdemers/maven-wrapper).
    
To make the execution easy it uses embedded [tomcat](http://tomcat.apache.org/) and [Exec Maven Plugin](http://mojo.codehaus.org/exec-maven-plugin/)








