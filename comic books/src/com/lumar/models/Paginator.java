package com.lumar.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.lumar.dao.ArticleDAO;

public class Paginator {
	private int currentPage;
	private int listIndex;
	private int limit;
	private final int LIST_CAPACITY = 20;
	private ArticleDAO articleDAO;
	private List<Integer> pagesList;
	private List<Article> allArticles;
	private int offset = 0;

	public Paginator(int currentPage, int listIndex, int limit) {
		this.currentPage = currentPage;
		this.limit = limit;
		this.listIndex = listIndex;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Integer> getPagesList() {
		return this.pagesList;
	}

	public void setPagesList(List<Integer> pagesList) {
		this.pagesList = pagesList;
	}

	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}

	public void setArticlesDAO(ArticleDAO articleDAO) {

		this.articleDAO = articleDAO;
		offset = articleDAO.getMaxArticleId() + 1;
		String direction = "right";
		findArticles(offset, direction, 1);
		buildPages();
	}

	public void turnPage(int selectedPage) {

		int difference = Math.abs(selectedPage - currentPage);

		if (selectedPage > currentPage) {

			pageDirectionRight(difference, selectedPage);

		} else {
			pageDirectionLeft(difference, selectedPage);

		}
		listIndex = pagesList.indexOf(selectedPage) * limit;
		currentPage = selectedPage;
	}

	public void setArticlesList(List<Article> articles) {

		this.allArticles = articles;
	}

	public void updatePages(int page) {

		--page;
		for (int i = 0; i < pagesList.size(); i++) {
			pagesList.set(i, page);
			page++;
		}
	}

	// builds initial pages.
	public void buildPages() {

		int pages = allArticles.size() / limit;

		if (allArticles.size() % limit != 0) {
			pages++;
		}

		pagesList = new ArrayList<Integer>();
		for (int i = 1; i <= pages; i++) {
			pagesList.add(i);
		}
	}

	public List<Article> displayArticles() {

		int pageResultLimit = listIndex + limit;

		pageResultLimit = pageResultLimit > allArticles.size() ? allArticles.size() : pageResultLimit;

		return allArticles.subList(listIndex, pageResultLimit);
	}

	public void findArticles(int offsetId, String direction, int selectedPage) {

		List<Article> tempArticlesList = articleDAO.findArticles(offsetId, direction);
		if (currentPage == 1) {
			allArticles = tempArticlesList;
			return;
		}

		int pages = tempArticlesList.size() / limit;

		if (tempArticlesList.size() % limit != 0) {
			pages++;
		}
		if (pages < 3) {

			return;
		}

		if (pages < 4) {
			offset -= limit;
			List<Article> subList = allArticles.subList(offset, offset + limit);
			allArticles = subList;
			allArticles.addAll(tempArticlesList);
			updatePages(--selectedPage);
			listIndex = pagesList.indexOf(++selectedPage) * limit;
			return;
		}

		allArticles = tempArticlesList;
		updatePages(selectedPage);
		listIndex = limit;
	}

	public void pageDirectionRight(int difference, int selectedPage) {
		String direction = "right";
		currentPage = selectedPage;
		listIndex = pagesList.indexOf(selectedPage) * limit;

		int threshold = 1;
		if (pagesList.indexOf(selectedPage) > threshold) {

			int offsetPage = selectedPage - 1;

			offset = pagesList.indexOf(offsetPage) * limit - 1;
		
			int offsetId = allArticles.get(offset).getId();

			if (allArticles.size() == LIST_CAPACITY) {
				findArticles(offsetId, direction, selectedPage);
				listIndex = pagesList.indexOf(selectedPage) * limit;
			}
		}

	}

	public void pageDirectionLeft(int difference, int selectedPage) {
		String direction = "left";
		listIndex = pagesList.indexOf(selectedPage) * limit;
		int offsetId = allArticles.get(listIndex).getId();

		if (pagesList.get(0) != 1) {

			allArticles = articleDAO.findArticles(offsetId, direction);
			updatePages(selectedPage);

		}

	}
}
