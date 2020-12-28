package com.lumar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lumar.models.ComicBook;

public class ComicDAO {
	private Connection connection;
	private String url;
	private String username;
	private String password;

	public ComicDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

//get featured comics
	public ComicBook[] getFeaturedComics() {

		String query = "Select * from comics LIMIT 8";
		ComicBook[] featuredComics = new ComicBook[8];
		connection = getConnection();
		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);
			int x = 0;
			while (rs.next()) {

				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				double price = rs.getDouble("price");
				String img = rs.getString("img");
				int issue = rs.getInt("issue");

				featuredComics[x] = new ComicBook(title, author, publisher, price, img, issue);
				x++;
			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return featuredComics;
	}

//get comics in general

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