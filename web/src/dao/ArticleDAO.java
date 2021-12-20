package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Article;

public class ArticleDAO  implements IDAO<Article, String>{

	private String path;
	
	public ArticleDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Article> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Article>>(){}.getType());
		
		if(articles == null)
        articles = new ArrayList<Article>();
			
		return articles;
	}

	@Override
	public Article getByID(String street) throws JsonSyntaxException, IOException {
	/*	Address wantedAddress = null;
		ArrayList<Address> addresses = (ArrayList<Address>) getAll();
		if(addresses.size()!=0)
		{
			for(Address address : addresses) {
				if(address.get.equals(id)) {
					wantedRestaurant = restaurant;
					break;
				}
			}
		}
		return wantedRestaurant;
        */return null;
	}

	@Override
	public void create(Article entity) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles= getAll();
		articles.add(entity);
		saveAll(articles);	
	}

	@Override
	public void update(Article entity) throws JsonSyntaxException, IOException {
		ArrayList<Article> articles = getAll();
		for(Article res : articles) {
			if(res.getName().equals(entity.getName())) {
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
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Article>>(){}.getType());
		writer.println(allEntities);
		writer.close();	
        	
	}

	@Override
	public ArrayList<Article> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
