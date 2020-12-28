package com.lumar.models;

public class Article {
    private int id;
	private String title;
	private String author;
	private String date;
	private String body;
	private String thumbnail;
	
	
	

	public Article( int id, String title, String author, String date, String body, String thumbnail) {
	    this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.body = body;
		this.thumbnail = thumbnail;
	}
	
	public Article(String title, String author, String date, String body, String thumbnail) {
	 
		this.title = title;
		this.author = author;
		this.date = date;
		this.body = body;
		this.thumbnail = thumbnail;
	}
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	

}
