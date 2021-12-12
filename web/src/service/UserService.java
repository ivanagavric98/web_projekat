package service;

import java.io.IOException;
import java.util.ArrayList;

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
		if(users.size()==0){
			usersDao.create(user);
			result=true;
		}else{
			for(User u : users){
				if(u.username.equals(user.username)){
				result= false;
				}else{
					usersDao.create(user);
					result=true;
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws JsonSyntaxException, IOException{
		return usersDao.getAll();
	}
	
	public String Proba() {
		return "proba uspjela";
	}
}
