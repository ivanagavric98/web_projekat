package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;

import org.eclipse.jetty.util.UrlEncoded;

import model.Customer;
import model.User;

public class UserDAO implements IDAO<User, String> {

	private String path;
	private ArrayList<User> users;

	public UserDAO(String path) {
		super();
		this.path = path;
		// this.users = new ArrayList<User>();
		try {
			getAll();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<User> getAll() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<User>>() {
		}.getType();
		BufferedReader br = new BufferedReader(new FileReader("data/users.json"));
		this.users = gson.fromJson(br, token);
		return users;
	}

	@Override
	public User getByID(String id) throws JsonSyntaxException, IOException {
		User wantedUser = null;
		ArrayList<User> users = (ArrayList<User>) getAll();
		if (users.size() != 0) {
			for (User user : users) {
				if (user.getUsername().equals(id)) {
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
		if (users == null) {
			users = new ArrayList<User>();
		}
		users.add(entity);
		saveAll(users);
	}

	@Override
	public void update(User entity) throws JsonSyntaxException, IOException {
		ArrayList<User> users = getAll();
		for (User user : users) {
			if (user.getUsername().equals(entity.getUsername())) {
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
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Customer>>() {
		}.getType());
		writer.println(allEntities);
		writer.close();

	}

	@Override
	public ArrayList<User> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> userSortByNameAsc(List<User> users) throws JsonSyntaxException, IOException {
		Set<User> toSort = new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());

		return resultList;
	}

	public List<User> userSortByNameDesc(List<User> users) throws JsonSyntaxException, IOException {
		List<User> usersToReturn=userSortByNameAsc(users);
		Collections.reverse(usersToReturn);

		return usersToReturn;
	}

	public List<User> userSortBySurnameAsc(List<User> users) throws JsonSyntaxException, IOException {
		Set<User> toSort = new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> e1.getSurname().compareTo(e2.getSurname()))
				.collect(Collectors.toList());

		return resultList;
	}

	public List<User> userSortBySurnameDesc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		List<User> usersToReturn=userSortByUsernameAsc(intersectionResult);
		Collections.reverse(usersToReturn);
		return usersToReturn;
	}

	public List<User> userSortByUsernameDesc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		List<User> usersToReturn=userSortByUsernameAsc(intersectionResult);
		Collections.reverse(usersToReturn);

		return usersToReturn;
	}

	public List<User> userSortByUsernameAsc(List<User> users) throws JsonSyntaxException, IOException {
		Set<User> toSort = new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> e1.getUsername().compareTo(e2.getUsername()))
				.collect(Collectors.toList());

		return resultList;
	}

}
