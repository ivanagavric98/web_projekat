package controller;

import service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dto.SearchFiltrateSortUsersDTO;
import dto.UserLogInDTO;
import model.Customer;
import model.User;

public class UserController {
	private UserService userService;

	public UserController(UserService usersService) {
		this.userService = usersService;
	}

	public Boolean register(User user) throws JsonSyntaxException, IOException {
		return userService.register(user);
	}

	public User login(UserLogInDTO userLogInDTO) throws JsonSyntaxException, IOException {
		return userService.login(userLogInDTO);
	}

	public void updatePersonalInfo(User user) throws JsonSyntaxException, IOException {
		userService.updatePersonalInfo(user);
	}

	public ArrayList<User> getAllUsers() throws JsonSyntaxException, IOException {
		return userService.getAllUsers();
	}

	public ArrayList<User> usersSearchByName(String name) throws JsonSyntaxException, IOException {
		return userService.usersSearchByName(name);
	}

	public ArrayList<User> usersSearchByUserName(String username) throws JsonSyntaxException, IOException {
		return userService.usersSearchByUserName(username);
	}

	public ArrayList<User> usersSearchBySurname(String surname) throws JsonSyntaxException, IOException {
		return userService.usersSearchBySurname(surname);
	}

	// public List<User> userSortByNameAsc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortByNameAsc();
	// }

	// public List<User> userSortByNameDesc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortByNameDesc();
	// }

	// public List<User> userSortBySurnameAsc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortBySurnameAsc();
	// }

	// public List<User> userSortBySurnameDesc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortBySurnameDesc();
	// }

	// public List<User> userSortByUsernameDesc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortByUsernameDesc();
	// }

	// public List<User> userSortByUsernameAsc() throws JsonSyntaxException, IOException {
	// 	return userService.userSortByUsernameAsc();
	// }

	// public List<User> usersFiltrateByRole(String role) throws JsonSyntaxException, IOException {
	// 	return userService.usersFiltrateByRole(role);
	// }

    public List<User> searchFiltreteSortUsers(SearchFiltrateSortUsersDTO searchFiltrateSortUsersDTO, List<Customer> customers) throws JsonSyntaxException, IOException {
        return userService.searchFiltreteSortUsers(searchFiltrateSortUsersDTO,customers);
    }

	/*
	 * public List<User> combineSearchUser(String name, String surname, String
	 * username) throws JsonSyntaxException, IOException {
	 * return userService.combineSearchUser(name,surname,username);
	 * }
	 */

}
