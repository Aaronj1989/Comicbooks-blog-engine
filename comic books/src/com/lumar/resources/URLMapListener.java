package com.lumar.resources;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class URLMapListener  implements ServletContextListener {
	
	
	public void contextInitialized(ServletContextEvent event){

		ServletContext sc = event.getServletContext();
		
		sc.setAttribute("urlMap", new HashMap<String,Integer>());
	
	}


}
