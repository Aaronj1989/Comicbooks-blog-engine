package com.lumar.models;

public class ComicBook {
private String title;
private String author;
private String publisher;
private double price;
private String img;
private int issue;



public ComicBook(String title, String author, String publisher, double price, String img,int issue) {
	this.title = title;
	this.author = author;
	this.publisher = publisher;
	this.price = price;
	this.img = img;
	this.issue= issue;
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
public String getPublisher() {
	return publisher;
}
public void setPublisher(String publisher) {
	this.publisher = publisher;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}

public int getIssue() {
	return this.issue;
}

public void setIssue(int issue) {
	this.issue = issue;
}

}
