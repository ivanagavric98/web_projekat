package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;

import model.User;

public class UserDAO implements IDAO<User, String>{

	private String path;
	
	public UserDAO(String path) {
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

	public ArrayList<User> searchByName(String name) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers=getAll();
		ArrayList<User> nameSearchList=new ArrayList<>();

		if(allUsers.size()!=0){
			for (User user : allUsers) {
				if(user.name.toLowerCase().equals(name.toLowerCase())){
					nameSearchList.add(user);
				}
			}
		}
		return nameSearchList;
	}

	public ArrayList<User> searchBySurname(String surname) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers=getAll();
		ArrayList<User> surnameSearchList=new ArrayList<>();

		if(allUsers.size()!=0){
			for (User user : allUsers) {
				if(user.surname.toLowerCase().equals(surname.toLowerCase())){
					surnameSearchList.add(user);
				}
			}
		}
		return surnameSearchList;
	}

	public ArrayList<User> searchByUsername(String username) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers=getAll();
		ArrayList<User> usernameSearchList=new ArrayList<>();

		if(allUsers.size()!=0){
			for (User user : allUsers) {
				if(user.username.toLowerCase().equals(username.toLowerCase())){
					usernameSearchList.add(user);
				}
			}
		}
		return usernameSearchList;
	}

	public List<User> userSortByNameAsc() throws JsonSyntaxException, IOException {
		ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
		
		return resultList;
		}

    public List<User> userSortByNameDesc() throws JsonSyntaxException, IOException {
		ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
		Collections.reverse(resultList);

		return resultList;
		}

    public List<User> userSortBySurnameAsc() throws JsonSyntaxException, IOException {
        ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getSurname().compareTo(e2.getSurname())).collect(Collectors.toList());
		
		return resultList;
    }

    public List<User> userSortBySurnameDesc() throws JsonSyntaxException, IOException {
        ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getSurname().compareTo(e2.getSurname())).collect(Collectors.toList());
		Collections.reverse(resultList);

		return resultList;
    }

    public List<User> userSortByUsernameDesc() throws JsonSyntaxException, IOException {
		ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getUsername().compareTo(e2.getUsername())).collect(Collectors.toList());
		Collections.reverse(resultList);

		return resultList;
    }

    public List<User> userSortByUsernameAsc() throws JsonSyntaxException, IOException {
        ArrayList<User> users=getAll();
		Set<User> toSort=new HashSet<>();

		for (User object : users) {
			toSort.add(object);
		}

		List<User> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getUsername().compareTo(e2.getUsername())).collect(Collectors.toList());

		return resultList;
    }

    public List<User> usersFiltrateByRole(String role) throws JsonSyntaxException, IOException {
		ArrayList<User> users=getAll();
		ArrayList<User> resultList=new ArrayList<>();
        for (User user : users) {
            if(user.getRole().toString().toLowerCase().equals(role.toLowerCase())){
                resultList.add(user);
            }
        }
        return resultList;
        }
    }


