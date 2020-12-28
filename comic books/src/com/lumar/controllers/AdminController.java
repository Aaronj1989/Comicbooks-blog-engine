package com.lumar.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.lumar.dao.ArticleDAO;
import com.lumar.models.Article;

public class AdminController extends HttpServlet {
	ArticleDAO articleDAO;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getPathInfo();

		switch (action) {

		case "/create": {
			createArticle(request, response);
		}
		case "/edit": {
			editArticle(request, response);
		}
			break;
		case "/publish": {
			publishArticle(request, response);
		}
			break;
		case "/delete": {
			deleteArticle(request, response);
		}
			break;
		case "/update": {
			updateArticle(request, response);
		}
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);

		}
	}

	public void createArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/article_creator.jsp");
		dispatcher.forward(request, response);
	}

	public void publishArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String author = "anonymous";
		String body = renderArticleBody(request.getParameter("article-body"));
		Calendar cal = new GregorianCalendar();
		String date = new SimpleDateFormat("d MMM yyyy hh:mm a").format(cal.getTime());
		String thumbnail = request.getParameter("thumbnail");

		Article article = new Article(title, author, date, body, thumbnail);
		articleDAO.saveArticle(article);

		response.sendRedirect(request.getContextPath() + "/home");
	}

	public void editArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Article article = articleDAO.findById(Integer.parseInt(request.getParameter("article-id")));

		request.setAttribute("article", article);
		createArticle(request, response);
	}

	public void updateArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = "anonymous";
		String body = renderArticleBody(request.getParameter("article-body"));
		Calendar cal = new GregorianCalendar();
		String date = new SimpleDateFormat("d MMM yyyy hh:mm a").format(cal.getTime());
		String thumbnail = request.getParameter("thumbnail");
		int id = Integer.parseInt(request.getParameter("articleID"));
		Article article = new Article(id, title, author, date, body, thumbnail);
		articleDAO.updateArticle(article);
		response.sendRedirect(request.getContextPath() + "/home");

	}

	public String renderArticleBody(String body) {

		Parser parser = Parser.builder().build();
		Node document = parser.parse(body);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		body = renderer.render(document);

		return body;
	}

	public void deleteArticle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("article-id"));
		articleDAO.deleteArticle(id);

		response.sendRedirect(request.getContextPath() + "/home");
	}

	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		String url = context.getInitParameter("jdbcURL");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");

		articleDAO = new ArticleDAO(url, username, password);

	}

}
