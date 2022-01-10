package com.lumar.resources;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lumar.dao.ComicDAO;
import com.lumar.models.ComicBook;
//displays featured items  
public class FeatureDisplayService implements ServletContextListener {

	
public void contextInitialized(ServletContextEvent event){

		ServletContext sc = event.getServletContext();
		String url = sc.getInitParameter("jdbcURL");
	    String username = sc.getInitParameter("username");
	    String password = sc.getInitParameter("password");
	   
	    ComicDAO comicDAO = new ComicDAO(url,username,password);
	    ComicBook[] featuredComics = comicDAO.getFeaturedComics();
	   
		sc.setAttribute("featuredComics", featuredComics);
	}
	
}
