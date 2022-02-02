package service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jws.soap.SOAPBinding.Use;

import com.google.gson.JsonSyntaxException;

import dao.CustomerDAO;
import dao.MenagerDAO;
import dao.SupplierDAO;
import dao.UserDAO;
import dto.SearchFiltrateSortUsersDTO;
import dto.UserLogInDTO;
import model.Customer;
import model.Menager;
import model.Supplier;
import model.User;
import spark.CustomErrorPages;

public class UserService {
	private UserDAO usersDao;
	private CustomerDAO customerDAO;
	private MenagerDAO menagerDAO;
	private SupplierDAO supplierDAO;
	
	public UserService(UserDAO usersDao, CustomerDAO customerDAO, MenagerDAO menagerDAO, SupplierDAO supplierDAO) {
		this.usersDao = usersDao;
		this.customerDAO = customerDAO;
		this.menagerDAO = menagerDAO;
		this.supplierDAO = supplierDAO;
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
		ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		ArrayList<User> users = new ArrayList<User>();
		
		users.addAll(customers);
		users.addAll(menagers);
		users.addAll(suppliers);
	
		return users;
	}

	public void updatePersonalInfo(User entity) throws JsonSyntaxException, IOException {
		usersDao.update(entity);
	}

	public String Proba() {
		return "proba uspjela";
	}

	public ArrayList<User> usersSearchByName(String name) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers = new ArrayList<User>();
		ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		
		allUsers.addAll(customers);
		allUsers.addAll(menagers);
		allUsers.addAll(suppliers);
		
		ArrayList<User> nameSearchList = new ArrayList<>();

		if (allUsers.size() != 0) {
			for (User user : allUsers) {
				if (user.name.toLowerCase().contains(name.toLowerCase())) {
					nameSearchList.add(user);
				}
			}
		}
		return nameSearchList;
	}

	public ArrayList<User> usersSearchByUserName(String username) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers = new ArrayList<User>();
		ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		
		allUsers.addAll(customers);
		allUsers.addAll(menagers);
		allUsers.addAll(suppliers);

		ArrayList<User> usernameSearchList = new ArrayList<>();

		if (allUsers.size() != 0) {
			for (User user : allUsers) {
				if (user.username.toLowerCase().contains(username.toLowerCase())) {
					usernameSearchList.add(user);
				}
			}
		}
		return usernameSearchList;
	}

	public ArrayList<User> usersSearchBySurname(String surname) throws JsonSyntaxException, IOException {
		ArrayList<User> allUsers = new ArrayList<User>();
		ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		
		allUsers.addAll(customers);
		allUsers.addAll(menagers);
		allUsers.addAll(suppliers);

		ArrayList<User> surnameSearchList = new ArrayList<>();

		if (allUsers.size() != 0) {
			for (User user : allUsers) {
				if (user.surname.toLowerCase().contains(surname.toLowerCase())) {
					surnameSearchList.add(user);
				}
			}
		}
		return surnameSearchList;

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

	public List<User> usersFiltrateByType(String type) throws JsonSyntaxException, IOException {
	    ArrayList<User> users = new ArrayList<User>();
	    ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		
	    users.addAll(customers);
	    users.addAll(menagers);
	    users.addAll(suppliers);
	    
        ArrayList<User> resultList = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getType().type.toString().toLowerCase().equals(type.toLowerCase())) {
                resultList.add(customer);
            }
        }
        return resultList;
	}
	public List<User> usersFiltrateByRolee(String role) throws JsonSyntaxException, IOException {
	    ArrayList<User> users = new ArrayList<User>();
	    ArrayList<Customer> customers = customerDAO.getAllNonDeleted();
		ArrayList<Supplier> suppliers = supplierDAO.getAllNonDeleted();
		ArrayList<Menager> menagers = menagerDAO.getAllNonDeleted();
		
	    users.addAll(customers);
	    users.addAll(menagers);
	    users.addAll(suppliers);
	    
        ArrayList<User> resultList = new ArrayList<>();
        for (User customer : users) {
            if (customer.getRole().toString().toLowerCase().equals(role.toLowerCase())) {
                resultList.add(customer);
            }
        }
        return resultList;
	}
}
