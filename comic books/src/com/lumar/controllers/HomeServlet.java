package com.lumar.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lumar.dao.ArticleDAO;
import com.lumar.models.Article;
import com.lumar.models.Paginator;

/**
 * Servlet implementation class HomeServlet
 */

public class HomeServlet extends HttpServlet {

	private Paginator paginator;
	private ArticleDAO articleDAO;
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public HomeServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method
		
         if (paginator == null) {
			ServletContext sc = request.getServletContext();
			String url = sc.getInitParameter("jdbcURL");
			String username = sc.getInitParameter("username");
			String password = sc.getInitParameter("password");
			articleDAO = new ArticleDAO(url, username, password);
			
			paginator = new Paginator(1, 0, 5);
			paginator.setArticlesDAO(articleDAO);

		}

         if (String.valueOf(request.getParameter("pageId")).equals("null")){
        	paginator = new Paginator(1,0,5);
        	 paginator.setArticlesDAO(articleDAO);
        	 
         }
		List<Article> articles = paginator.displayArticles();
	
		
		Map<String, Integer> articleMap = (HashMap) request.getServletContext().getAttribute("urlMap");
		articleMap.clear();

		for (Article a : articles) {
			articleMap.put(a.getTitle().replace(" ", "-"), a.getId());
		}

		request.setAttribute("articles", articles);
		request.setAttribute("paginator", paginator);
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int selectedPage = Integer.parseInt(request.getParameter("page"));
		paginator.turnPage(selectedPage);
		
		response.sendRedirect(request.getContextPath() + "/home?pageId="+selectedPage);
	}

}
