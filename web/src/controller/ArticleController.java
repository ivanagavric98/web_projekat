package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Article;
import service.ArticleService;

public class ArticleController {
    private ArticleService articleService;
	private static Gson gson = new Gson();

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

    public void updateArticle(Article article) throws JsonSyntaxException, IOException {
         articleService.updateArticle(article);
    }
	
	public Boolean addArticleToRestaurant(Article article) throws JsonSyntaxException, IOException {
		try {
			return articleService.addArticleToRestaurant(article);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public ArrayList<Article> getAll() throws JsonSyntaxException, IOException {
		return articleService.getAllArticles();
	}
    
}
