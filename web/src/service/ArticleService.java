package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.ArticleDAO;
import model.Article;

public class ArticleService {
    private ArticleDAO articleDAO;
	
	public ArticleService(ArticleDAO articleDAO) {
		this.articleDAO=articleDAO;
	}
	
	public Boolean addArticleToRestaurant(Article article) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles =  articleDAO.getAll();
		Boolean result=false;
        
		if(articles.size()==0){
			articleDAO.create(article);
			result=true;
		}else{
			for(Article u : articles){
				if(u.restaurant.equals(article.restaurant) && u.name.equals(article.name)){
				result= false;
				}else{
					articleDAO.create(article);
					result=true;
				}
			}
		}
		return result;
	}
	
	public ArrayList<Article> getAllArticles() throws JsonSyntaxException, IOException{
		return articleDAO.getAll();
	}

    public void updateArticle(Article article) throws JsonSyntaxException, IOException {
		 articleDAO.update(article);
    }
    

}
