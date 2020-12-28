package com.lumar.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lumar.dao.ArticleDAO;
import com.lumar.models.Article;

public class NewsFeedListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event){
	 ServletContext sc = event.getServletContext();

	}
}
