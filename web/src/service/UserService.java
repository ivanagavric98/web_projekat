package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonSyntaxException;

import dao.UserDAO;
import model.User;

public class UserService {
	private UserDAO usersDao;
	
	public UserService(UserDAO usersDao) {
		this.usersDao=usersDao;
	}
	
	public Boolean register(User user) throws JsonSyntaxException, IOException {
		ArrayList<User>users=getAllUsers();
		Boolean result=false;
		if(users==null){
			usersDao.create(user);
			result=true;
		}else{
			for(User u : users){
				if(u.username.equals(user.username)){
				 return result= false;
				}
			}
			usersDao.create(user);
			result=true;
		}
		
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws JsonSyntaxException, IOException{
		return usersDao.getAll();
	}
	
	public String Proba() {
		return "proba uspjela";
	}
/*
    public ArrayList<User> searchUsers(String name, String surname, String username) {
		return usersDao.searchUsers(name,surname,username);
    }
	*/

    public ArrayList<User> usersSearchByName(String name) throws JsonSyntaxException, IOException {
		return usersDao.searchByName(name);
    }

	public ArrayList<User> usersSearchByUserName(String username) throws JsonSyntaxException, IOException {
		return usersDao.searchByUsername(username);
		}

    public ArrayList<User> usersSearchBySurname(String surname) throws JsonSyntaxException, IOException {
		return usersDao.searchBySurname(surname); 
	  }

	public List<User> userSortByNameAsc() throws JsonSyntaxException, IOException {
		return usersDao.userSortByNameAsc();  
	  }

	public List<User> userSortByNameDesc() throws JsonSyntaxException, IOException {
		return usersDao.userSortByNameDesc();  
	}

	public List<User> userSortBySurnameAsc() throws JsonSyntaxException, IOException {
		return usersDao.userSortBySurnameAsc();  
	}

    public List<User> userSortBySurnameDesc() throws JsonSyntaxException, IOException {
        return usersDao.userSortBySurnameDesc();
    }

    public List<User> userSortByUsernameDesc() throws JsonSyntaxException, IOException {
        return usersDao.userSortByUsernameDesc();
    }

    public List<User> userSortByUsernameAsc() throws JsonSyntaxException, IOException {
        return usersDao.userSortByUsernameAsc();
    }

	public List<User> usersFiltrateByRole(String role) throws JsonSyntaxException, IOException {
        return usersDao.usersFiltrateByRole(role);
	}

   /* public List<User> combineSearchUser(String name, String surname, String username) throws JsonSyntaxException, IOException {
		return usersDao.combineSearchUser(name,surname,username);    }
*/
  
}
