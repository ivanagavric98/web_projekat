package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Article;
import model.Restaurant;
import model.ShoppingCart;
import model.ShoppingCartItem;

public class ArticleDAO implements IDAO<Article, String> {

	private String path;
	private ArrayList<Article> articles;

	public ArticleDAO(String path) {
		super();
		this.path = path;

		try {
			getAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Article> getAll() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Article>>() {
		}.getType();
		BufferedReader br = new BufferedReader(new FileReader("web/data/articles.json"));
		this.articles = gson.fromJson(br, token);
		return articles;
	}

	@Override
	public Article getByID(String name) throws JsonSyntaxException, IOException {
		Article wantedArticle = null;
		ArrayList<Article> articles = (ArrayList<Article>) getAll();
		if (articles.size() != 0) {
			for (Article article : articles) {
				if (article.getName().equals(name)) {
					wantedArticle = article;
					break;
				}
			}
		}
		return wantedArticle;
	}

	@Override
	public void create(Article entity) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = getAll();
		articles.add(entity);
		saveAll(articles);
	}

	@Override
	public void update(Article entity) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = getAll();
		for (Article res : articles) {
			if (res.getName().equals(entity.getName())) {
				articles.set(articles.indexOf(res), entity);
				break;
			}
		}
		saveAll(articles);
	}

	@Override
	public void delete(Article entity) throws JsonSyntaxException, IOException {
		return;
	}

	@Override
	public void save(Article entity) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = getAll();
		articles.add(entity);
		saveAll(articles);

	}

	@Override
	public void saveAll(ArrayList<Article> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Article>>() {
		}.getType());
		writer.println(allEntities);
		writer.close();

	}

	@Override
	public ArrayList<Article> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Article> getArticlesByRestaurantName(String restaurantName)
			throws JsonSyntaxException, IOException {
		ArrayList<Article> allArticles = getAll();
		ArrayList<Article> result = new ArrayList<>();
		for (Article a : allArticles) {
			if (a.getRestaurant().trim().toLowerCase().equals(restaurantName.trim().toLowerCase())) {
				result.add(a);
			}
		}
		return result;

	}

	public Double getPricePerArticle(String restaurantName, ShoppingCartItem shoppingCartItem)
			throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = getAll();
		Double price = 0.0;
		for (Article a : articles) {
			if (a.getRestaurant().trim().toLowerCase().equals(restaurantName.trim().toLowerCase()) &&
					a.getName().trim().toLowerCase().equals(shoppingCartItem.articleName.trim().toLowerCase())) {
				price += shoppingCartItem.quantity * a.price;
			}
		}
		return price;

	}

}
