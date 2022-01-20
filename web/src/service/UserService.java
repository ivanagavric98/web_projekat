package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jws.soap.SOAPBinding.Use;

import com.google.gson.JsonSyntaxException;

import dao.UserDAO;
import dto.SearchFiltrateSortUsersDTO;
import dto.UserLogInDTO;
import model.Customer;
import model.User;
import spark.CustomErrorPages;

public class UserService {
	private UserDAO usersDao;

	public UserService(UserDAO usersDao) {
		this.usersDao = usersDao;
	}

	public Boolean register(User user) throws JsonSyntaxException, IOException {
		ArrayList<User> users = getAllUsers();
		Boolean result = false;
		if (users == null) {
			usersDao.create(user);
			result = true;
		} else {
			for (User u : users) {
				if (u.username.equals(user.username)) {
					return result = false;
				}
			}
			usersDao.create(user);
			result = true;
		}

		return result;
	}

	public User login(UserLogInDTO userLogInDTO) throws JsonSyntaxException, IOException {

		User user = null;

		if (userLogInDTO.getUsername() != null) {
			user = usersDao.getByID(userLogInDTO.getUsername());
		}

		if (user != null) {
			if (userLogInDTO.getPassword().equals(user.getPassword())) {
				return user;
			}
		}

		return null;
	}

	public ArrayList<User> getAllUsers() throws JsonSyntaxException, IOException {
		return usersDao.getAll();
	}

	public void updatePersonalInfo(User entity) throws JsonSyntaxException, IOException {
		usersDao.update(entity);
	}

	public String Proba() {
		return "proba uspjela";
	}
	/*
	 * public ArrayList<User> searchUsers(String name, String surname, String
	 * username) {
	 * return usersDao.searchUsers(name,surname,username);
	 * }
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


	public List<User> userSortByNameAsc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortByNameAsc(intersectionResult);
	}

	public List<User> userSortByNameDesc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortByNameDesc(intersectionResult);
	}

	public List<User> userSortBySurnameAsc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortBySurnameAsc(intersectionResult);
	}

	public List<User> userSortBySurnameDesc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortBySurnameDesc(intersectionResult);
	}

	public List<User> userSortByUsernameDesc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortByUsernameDesc(intersectionResult);
	}

	public List<User> userSortByUsernameAsc(List<User> intersectionResult) throws JsonSyntaxException, IOException {
		return usersDao.userSortByUsernameAsc(intersectionResult);
	}

	public List<User> usersFiltrateByRole(String role, List<User> sortedList) throws JsonSyntaxException, IOException {
		return usersDao.usersFiltrateByRole(role,sortedList);
	}

    public List<User> searchFiltreteSortUsers(SearchFiltrateSortUsersDTO searchFiltrateSortUsersDTO, List<Customer> customers) throws JsonSyntaxException, IOException {
		List<User> searchByName = new ArrayList<User>();
		List<User> searchBySurname = new ArrayList<User>();
		List<User> searchByUsername = new ArrayList<User>();

		if (searchFiltrateSortUsersDTO.getSearchByName() != null) {
			searchByName = usersSearchByName(searchFiltrateSortUsersDTO.getSearchByName());
		} else {
			searchByName =getAllUsers();
		}

		if (searchFiltrateSortUsersDTO.getSearchBySurname() != null) {
			searchBySurname = usersSearchBySurname(searchFiltrateSortUsersDTO.getSearchBySurname());          
		} else {
			searchBySurname =  getAllUsers();
		}

		if (searchFiltrateSortUsersDTO.getSearchByUsername() != null) {
			searchByUsername = usersSearchByUserName(searchFiltrateSortUsersDTO.getSearchByUsername());
		} else {
			searchByUsername =  getAllUsers();
		}


		List<User> intersectionResult = new ArrayList<User>();
		List<User> intersectionResult1 = new ArrayList<User>();
		for (User user : searchByName) {
			for (User user1 : searchBySurname) {
				if (user.getUsername().equals(user1.getUsername())) {
					intersectionResult.add(user);
				}
			}
		}

		for (User user : intersectionResult) {
			for (User user1 : searchByUsername) {
				if (user.getUsername().equals(user1.getUsername())) {
					intersectionResult1.add(user);
				}
			}
		}

		List<User> sortedList = new ArrayList<User>();
		if (searchFiltrateSortUsersDTO.getSortByName() != null) {
			if (searchFiltrateSortUsersDTO.getSortByName().equals("ascending")) {
				sortedList = userSortByNameAsc(intersectionResult1);
			} else {
				sortedList = userSortByNameDesc(intersectionResult1);
			}
		}

		if (searchFiltrateSortUsersDTO.getSortBySurname() != null) {
			if (searchFiltrateSortUsersDTO.getSortBySurname().equals("ascending")) {
				sortedList = userSortBySurnameAsc(intersectionResult1);
			} else {
				sortedList = userSortBySurnameDesc(intersectionResult1);
			}
		}

		if (searchFiltrateSortUsersDTO.getSortByUsername() != null) {
			if (searchFiltrateSortUsersDTO.getSortByUsername().equals("ascending")) {
				sortedList = userSortByUsernameAsc(intersectionResult1);
			} else {
				sortedList = userSortBySurnameDesc(intersectionResult1);
			}
		}

		if (searchFiltrateSortUsersDTO.getSortByPoints() != null) {
			if (searchFiltrateSortUsersDTO.getSortByPoints().equals("ascending")) {
				Set<Customer> toSort = new HashSet<>();
				for (Customer object : customers) {
					toSort.add(object);
				}
				List<Customer> resultList = toSort.stream()
						.sorted((e1, e2) -> Double.valueOf(e1.getPoints()).compareTo(Double.valueOf(e2.getPoints())))
						.collect(Collectors.toList());
				for(Customer customer:resultList){
					for(User user:intersectionResult1){
						if(user.getUsername().equals(customer.getUsername())){
							sortedList.add(user);
						}
					}
				}
			} else {
				Set<Customer> toSort = new HashSet<>();
				for (Customer object : customers) {
					toSort.add(object);
				}
				List<Customer> resultList = toSort.stream()
						.sorted((e1, e2) -> Double.valueOf(e1.getPoints()).compareTo(Double.valueOf(e2.getPoints())))
						.collect(Collectors.toList());
				Collections.reverse(resultList);
				for(Customer customer:resultList){
					for(User user:intersectionResult1){
						if(user.getUsername().equals(customer.getUsername())){
							sortedList.add(user);
						}
					}
				}
			}
		}

		if (searchFiltrateSortUsersDTO.getSortByName() == null
				&& searchFiltrateSortUsersDTO.getSortBySurname() == null
				&& searchFiltrateSortUsersDTO.getSortByUsername() == null && searchFiltrateSortUsersDTO.getSortByPoints()==null) {
			sortedList = intersectionResult1;
		}

		List<User> filtrateByRole = new ArrayList<User>();
		if (searchFiltrateSortUsersDTO.getFiltrateByRole() != null) {
			filtrateByRole = usersFiltrateByRole(searchFiltrateSortUsersDTO.getFiltrateByRole(),
					sortedList);
		} else {
			filtrateByRole = sortedList;
		}

		List<User> filtrateByType = new ArrayList<User>();
		if (searchFiltrateSortUsersDTO.getFiltrateByType() != null) {
			ArrayList<Customer> filtratedUser=new ArrayList<>();
			for(Customer c : customers){
				if(c.getType().getType().toString().equals(searchFiltrateSortUsersDTO.getFiltrateByType())){
					filtratedUser.add((c));
				}
			}
			for(User user : intersectionResult1){
				for(Customer customer : filtratedUser){
					if(user.getUsername().equals(customer.getUsername())){
						filtrateByType.add(user);
					}
				}
			}
		} else {
			filtrateByType = sortedList;
		}

		List<User> result = new ArrayList<User>();
		for (User user : filtrateByRole) {
			for (User user1 : filtrateByType) {
				if (user.getUsername().equals(user1.getUsername())) {
					result.add(user);
				}
			}
		}
		return result;   
	 }

	/*
	 * public List<User> combineSearchUser(String name, String surname, String
	 * username) throws JsonSyntaxException, IOException {
	 * return usersDao.combineSearchUser(name,surname,username); }
	 */

}
