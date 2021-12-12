package controller;

import service.UserService;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.User;

public class UserController {
	private UserService userService;
	private static Gson gson = new Gson();

	public UserController(UserService usersService) {
		this.userService = usersService;
	}

		public Boolean register(User user) throws JsonSyntaxException, IOException{			
			return userService.register(user);
	   }
	
}
