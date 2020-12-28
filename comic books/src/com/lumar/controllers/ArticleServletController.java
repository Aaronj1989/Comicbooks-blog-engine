package com.lumar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.lumar.dao.ArticleDAO;
import com.lumar.models.Article;

import org.commonmark.node.*;

public class ArticleServletController extends HttpServlet {

	private ArticleDAO articleDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String,Integer> articleResourceMap = (HashMap<String, Integer>) request.getServletContext().getAttribute("urlMap");
		Integer articleId = articleResourceMap.get(request.getPathInfo().substring(1));
         
		if (articleDAO == null) {
			ServletContext sc = request.getServletContext();
			String url = sc.getInitParameter("jdbcURL");
			String username = sc.getInitParameter("username");
			String password = sc.getInitParameter("password");
			articleDAO = new ArticleDAO(url, username, password);
		}
		if (articleId == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);

		} else {
			
			Article article = articleDAO.findById(articleId);
			request.setAttribute("article", article);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/article.jsp");
			dispatcher.forward(request, response);
		}
	}


	/*
	 * @Override protected void doPost(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * 
	 * }
	 */
}
