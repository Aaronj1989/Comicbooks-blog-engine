package com.lumar.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.lumar.dao.ArticleDAO;

public class Paginator {
	private int currentPage;
	private int dbIndex;
	private int maxArticlesPerPage; // maxArticlesPerPage
	private final int LIST_CAPACITY = 20;
	private ArticleDAO articleDAO;
	private List<Integer> pagesList;
	private List<Article> allArticles;
	private int offset = 0;

	public Paginator(int currentPage, int dbIndex, int maxArticlesPerPage) {
		this.currentPage = currentPage;
		this.maxArticlesPerPage = maxArticlesPerPage;
		this.dbIndex = dbIndex;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return maxArticlesPerPage;
	}

	public void setLimit(int maxArticlesPerPage) {
		this.maxArticlesPerPage = maxArticlesPerPage;
	}

	public List<Integer> getPagesList() {
		return this.pagesList;
	}

	public void setPagesList(List<Integer> pagesList) {
		this.pagesList = pagesList;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
//sets the initial first results when the application starts
	public void setArticlesDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
		offset = articleDAO.getMaxArticleId() + 1;
		String direction = "right";
		findArticles(offset, direction, 1);
		buildPages();
	}
    //turns page to user selected page
	public void turnPage(int selectedPage) {
		int difference = Math.abs(selectedPage - currentPage);

		if (selectedPage > currentPage) {
			pageDirectionRight(difference, selectedPage);
		} else {
			pageDirectionLeft(difference, selectedPage);
		}
		dbIndex = pagesList.indexOf(selectedPage) * maxArticlesPerPage;
		currentPage = selectedPage;
	}

	public void setArticlesList(List<Article> articles) {
		this.allArticles = articles;
	}

	// updates the page numbers at their respective indexes. 
	//the page that the user selects will always be second in the updated index.
	public void updatePages(int page) {
		--page;
		for (int i = 0; i < pagesList.size(); i++) {
			pagesList.set(i, page);
			page++;
		}
	}

	// builds page numbers depending on the amount of articles that are retrieved
	// from the database
	public void buildPages() {
		int pages = allArticles.size() / maxArticlesPerPage;
		if (allArticles.size() % maxArticlesPerPage != 0) {
			pages++;
		}
		pagesList = new ArrayList<Integer>();
		for (int i = 1; i <= pages; i++) {
			pagesList.add(i);
		}
	}
 //returns the current articles being displayed to the user
	public List<Article> displayArticles() {
		int pageResultLimit = dbIndex + maxArticlesPerPage;
		pageResultLimit = pageResultLimit > allArticles.size() ? allArticles.size() : pageResultLimit;
		return allArticles.subList(dbIndex, pageResultLimit);
	}

//finds more articles depending on which page the user clicks and dynamically changes page amount
//the size of the page numbers change dynamically depending on the amount of results that come back from the database
	public void findArticles(int offsetId, String direction, int selectedPage) {
		List<Article> tempArticlesList = articleDAO.findArticles(offsetId, direction);
		if (currentPage == 1) {
			allArticles = tempArticlesList;
			return;
		}
		int pageCount = tempArticlesList.size() / maxArticlesPerPage;
		if (tempArticlesList.size() % maxArticlesPerPage != 0) {
			pageCount++;
		}
		if (pageCount < 3) {
			return;
		}
		if (pageCount < 4) {
			offset -= maxArticlesPerPage;
			List<Article> subList = allArticles.subList(offset, offset + maxArticlesPerPage);
			allArticles = subList;
			allArticles.addAll(tempArticlesList);
			updatePages(--selectedPage);
			dbIndex = pagesList.indexOf(++selectedPage) * maxArticlesPerPage;
			return;
		}
		allArticles = tempArticlesList;
		updatePages(selectedPage);
		dbIndex = maxArticlesPerPage;
	}

//in ascending order finds the difference between the current page and the next page the user clicked
	// if the user clicked a page at an index greater than 1 the page numbers update
	public void pageDirectionRight(int difference, int selectedPage) {
		String direction = "right";
		currentPage = selectedPage;
		dbIndex = pagesList.indexOf(selectedPage) * maxArticlesPerPage;

		int threshold = 1;
		if (pagesList.indexOf(selectedPage) > threshold) {
			int offsetPage = selectedPage - 1;
			offset = pagesList.indexOf(offsetPage) * maxArticlesPerPage - 1;
			int offsetId = allArticles.get(offset).getId();

			if (allArticles.size() == LIST_CAPACITY) {
				findArticles(offsetId, direction, selectedPage);
				dbIndex = pagesList.indexOf(selectedPage) * maxArticlesPerPage;
			}
		}
	}

	//in descending order finds the difference between the current page and the page the user clicked and 
	//if the page number in the first index isn't 1, then update the page numbers in the index.
	public void pageDirectionLeft(int difference, int selectedPage) {
		String direction = "left";
		dbIndex = pagesList.indexOf(selectedPage) * maxArticlesPerPage;
		int offsetId = allArticles.get(dbIndex).getId();
		if (pagesList.get(0) != 1) {
			allArticles = articleDAO.findArticles(offsetId, direction);
			updatePages(selectedPage);
		}
	}
}
