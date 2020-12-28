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
  	public void setupDAO(){
  		String url = "jdbc:mysql://localhost:3306/comicweb";
  		String username="root";
  		String password = "student";
  	    articleDAO = new ArticleDAO(url,username,password);
  	    
  	    paginator = new Paginator(1,0,5);
  	    paginator.setArticlesDAO(articleDAO);
  	}
  	
        @Test
    	public void updatePages() {
    	
    		paginator.setArticlesDAO(articleDAO);
    		paginator.turnPage(3);
    		List<Integer> listTester = new ArrayList<Integer>() {
    		
    		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{add(2);
    		 add(3);
    		 add(4);
    		 add(5);
    		 }
    		};
    		assert(listTester.equals(paginator.getPagesList()));
    	}

        
        
	@Test
	void turnPageTest() {
	
	
		List<Article> articlesList = new ArrayList<Article>();
		Article article = new Article(23, "article title", "article author", "Dec/20/2020", "loreum epsum", "http://mockimage.jpg");
		for(int i =23; i>3; i--) {
			article.setId(i);
			articlesList.add(article);
		}
		paginator.setArticlesList(articlesList);
		
		List<Integer> pagesList = new ArrayList<Integer>() {
			{add(1);
			 add(2);
			 add(3);
			 add(4);
			}
		};
		
		paginator.setPagesList(pagesList);
		
		paginator.turnPage(2);
		int listIndex = paginator.getListIndex();
		assertEquals(listIndex,5);
		
		paginator.turnPage(4);
		assertEquals(listIndex,5);
		
	}

	
	
}
