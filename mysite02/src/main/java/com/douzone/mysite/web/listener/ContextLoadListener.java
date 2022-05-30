package com.douzone.mysite.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class ContextLoadListener
 *
 */
public class ContextLoadListener implements ServletContextListener {

	
	public void contextInitialized(ServletContextEvent sce)  { 
	ServletContext sc =sce.getServletContext();
	String string =sc.getInitParameter("contextConfigLocation");
	
	System.out.println(string);
	
   	 System.out.println("애플리케이션 시작");
   }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
        
    }
    
}
