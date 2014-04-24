package com.mtdvd.sockhttp;

import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

import javax.servlet.ServletException;
import java.io.File;

/**
 * @author maty.david
 * @since 4/20/2014
 */
public class Application {

    public static void main(String[] args) {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        try {
            configureTomcatWebApp(webappDirLocation, tomcat);            
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException | ServletException e) {
            e.printStackTrace();
        } 
    }

    private static void configureTomcatWebApp(String webappDirLocation, Tomcat tomcat) throws ServletException {
        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        Container[] containers = tomcat.getService().getContainer().findChildren();
        StandardHost host = (StandardHost)containers[0];
        containers = host.findChildren();
        StandardContext ctx = (StandardContext)containers[0];
        //declare an alternate location for your "WEB-INF/classes" dir:     
        File additionWebInfClasses = new File("target/classes");
        VirtualDirContext resources = new VirtualDirContext();
        resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
        ctx.setResources(resources);
    }
}