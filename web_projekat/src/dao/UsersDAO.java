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

import model.User;

public class UsersDAO implements IDAO<User, String>{

	private String path;
	
	public UsersDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<User> getAll() throws JsonSyntaxException, IOException {
		ArrayList<User> users = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<User>>(){}.getType());
		
		if(users == null)
			users = new ArrayList<User>();
			
		return users;
	}

	@Override
	public User getByID(String id) throws JsonSyntaxException, IOException {
		User wantedUser = null;
		ArrayList<User> users = (ArrayList<User>) getAll();
		if(users.size()!=0)
		{
			for(User user : users) {
				if(user.getUsername().equals(id)) {
					wantedUser = user;
					break;
				}
			}
		}
		return wantedUser;
	}

	@Override
	public void create(User entity) throws JsonSyntaxException, IOException {
		ArrayList<User> users = getAll();
		users.add(entity);
		saveAll(users);	
	}

	@Override
	public void update(User entity) throws JsonSyntaxException, IOException {
		ArrayList<User> users = getAll();
		for(User user : users) {
			if(user.getUsername().equals(entity.getUsername())) {
				users.set(users.indexOf(user), entity);
				break;
			}
		}
		saveAll(users);
		
	}

	@Override
	public void delete(User entity) throws JsonSyntaxException, IOException {
		return;		
	}

	@Override
	public void save(User entity) throws JsonSyntaxException, IOException {
		ArrayList<User> users = getAll();
		users.add(entity);
		saveAll(users);	
		
	}

	@Override
	public void saveAll(ArrayList<User> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<User>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
	}

	@Override
	public ArrayList<User> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
