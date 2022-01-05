package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.ArticleDAO;
import model.Article;
import model.ShoppingCartItem;

public class ArticleService {
	private ArticleDAO articleDAO;

	public ArticleService(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public Boolean addArticleToRestaurant(Article article) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = articleDAO.getAll();
		Boolean result = false;

		if (articles.size() == 0) {
			articleDAO.create(article);
			result = true;
		} else {
			for (Article u : articles) {
				if (u.name.equals(article.name))
					result = false;
			}
			articleDAO.create(article);
			result = true;

		}
		return result;
	}

	public ArrayList<Article> getAllArticles() throws JsonSyntaxException, IOException {
		return articleDAO.getAll();
	}

	public void updateArticle(Article article) throws JsonSyntaxException, IOException {
		articleDAO.update(article);
	}

	public ArrayList<Article> getArticlesByRestaurantName(String restaurantName)
			throws JsonSyntaxException, IOException {
		return articleDAO.getArticlesByRestaurantName(restaurantName);
	}

	public Double getPricePerArticle(String restaurantName, ShoppingCartItem shoppingCartItem)
			throws JsonSyntaxException, IOException {
		return articleDAO.getPricePerArticle(restaurantName, shoppingCartItem);
	}

	public Article getInfoAboutArticle(String articleName, String restaurantName)
			throws JsonSyntaxException, IOException {
		return articleDAO.getInfoAboutArticle(articleName, restaurantName);
	}

}
