package com.lumar.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lumar.dao.ArticleDAO;

class PaginatorTest {
	static Paginator paginator;
	ArticleDAO articleDAO;

	@BeforeEach
	public void setupDAO() {
		String url = "jdbc:mysql://localhost:3306/comicweb";
		String username = "root";
		String password = "password";
		articleDAO = new ArticleDAO(url, username, password);

		paginator = new Paginator(1, 0, 5);
		paginator.setArticlesDAO(articleDAO);
	}

	@Test  //asserts the indexes are updated with the correct page numbers.  
	public void updatePageIndexes() {
		paginator.setArticlesDAO(articleDAO);
		paginator.turnPage(3);
		List<Integer> listTester = new ArrayList<Integer>() {
			{
				add(2);
				add(3);
				add(4);
				add(5);
			}
		};
		assert (listTester.equals(paginator.getPagesList()));
	}

	@Test // asserts that the page is turned when turnPage() method is called and the articles index is correct
	void turnPageTest() {
		List<Article> articlesList = new ArrayList<Article>();
		Article article = null;
		for (int i = 23; i > 3; i--) {
			article = new Article(i, "article title", "article author", "Dec/20/2020", "loreum epsum",
					"http://mockimage.jpg");
			articlesList.add(article);
		}

		paginator.setArticlesList(articlesList);
		List<Integer> pagesList = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(3);
				add(4);
			}
		};
		paginator.setPagesList(pagesList);
		paginator.turnPage(2);

		int articlesIndex = paginator.getDbIndex();
		assertEquals(articlesIndex, 5);

		paginator.turnPage(4);
		articlesIndex = paginator.getDbIndex();
		assertEquals(articlesIndex, 15);

	}

}
