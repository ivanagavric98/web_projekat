package controller;

import java.io.IOException;

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

		public Boolean register(Article article) throws JsonSyntaxException, IOException{			
			return articleService.register(article);
	   }

        public void updateArticle(Article article) throws JsonSyntaxException, IOException {
             articleService.updateArticle(article);
        }
	
}
