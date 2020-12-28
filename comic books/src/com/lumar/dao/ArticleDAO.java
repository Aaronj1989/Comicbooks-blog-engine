package com.lumar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lumar.models.Article;
import com.lumar.models.ComicBook;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class ArticleDAO {
	private String url;
	private String username;
	private String password;
	private Connection connection;

	public ArticleDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public ArticleDAO() {
	}

	public int getMaxArticleId() {
		int id = 0;
		String query = "select id from articles order by id desc limit 1";
		connection = getConnection();
		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				id = rs.getInt("id");
			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return id;
	}

	public List<Article> findArticles(int offset, String direction) {

		String rightDirectionQuery = "Select * from articles where id < " + offset + " order by id desc limit 20";
		String leftDirectionQuery = "(select * from articles where id > " + offset + " order by id desc limit 5)"
				+ " union" + "(select * from articles where id <= " + offset + " order by id desc limit 15)";
		String query = direction.equals("right") ? rightDirectionQuery : leftDirectionQuery;

		List<Article> articles = new ArrayList<Article>();
		connection = getConnection();
		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String date = rs.getString("date");
				String body = rs.getString("body");
				String thumbnail = rs.getString("thumbnail");

				articles.add(new Article(id, title, author, date, body, thumbnail));

			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return articles;
	}

	public Article findByTitle(String title) {

		String query = "Select * from articles where title = ?";
		Article article = null;
		connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String articleTitle = rs.getString("title");
				String author = rs.getString("author");
				String date = rs.getString("date");
				String body = rs.getString("body");
				String thumbnail = rs.getString("thumbnail");

				article = new Article(id, articleTitle, author, date, body, thumbnail);

			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return article;
	}

	public Article findById(int id) {

		String query = "Select * from articles where id = ?";
		Article article = null;
		connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				String articleTitle = rs.getString("title");
				String author = rs.getString("author");
				String date = rs.getString("date");
				String body = rs.getString("body");
				String thumbnail = rs.getString("thumbnail");

				article = new Article(id, articleTitle, author, date, body, thumbnail);

			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return article;
	}

	public void saveArticle(Article article) {

		String query = "insert into articles (title, author, date, body, thumbnail) values (?,?,?,?,?)";

		connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, article.getTitle());
			ps.setString(2, article.getAuthor());
			ps.setString(3, article.getDate());
			ps.setString(4, article.getBody());
			ps.setString(5, article.getThumbnail());

			ps.execute();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void updateArticle(Article article) {

		String query = "update articles set title=?, body=?, thumbnail=? where id = ?";

		connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, article.getTitle());
			ps.setString(2, article.getBody());
			ps.setString(3, article.getThumbnail());
			ps.setInt(4, article.getId());
			ps.execute();

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void deleteArticle(int articleId) {

		String query = "Delete from articles where id = ?";

		connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, articleId);
			ps.execute();

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {

				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

}
