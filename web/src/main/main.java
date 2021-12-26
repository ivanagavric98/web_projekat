package main;

import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.Console;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.websocket.client.io.UpgradeConnection.SendUpgradeRequest;

import controller.AddressController;
import controller.ArticleController;
import controller.CustomerController;
import controller.LocationController;
import controller.MenagerController;
import controller.RestaurantController;
import controller.SupplierController;
import controller.UserController;
import dao.AddressDAO;
import dao.ArticleDAO;
import dao.CustomerDAO;
import dao.LocationDAO;
import dao.MenagerDAO;
import dao.RestaurantDAO;
import dao.SupplierDAO;
import dao.UserDAO;
import dto.UserLogInDTO;
import javaxt.utils.Date;
import model.Address;
import model.Article;
import model.Customer;
import model.Location;
import model.Menager;
import model.Restaurant;
import model.Supplier;
import model.User;
import service.AddressService;
import service.ArticleService;
import service.CustomerService;
import service.LocationService;
import service.MenagerService;
import service.RestaurantService;
import service.SupplierService;
import service.UserService;
import spark.Request;
import spark.Session;

public class main {

	
	private static Gson g = new Gson();
    private static Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		UserDAO usersDAO = new UserDAO("data/users.json");
		UserService usersService = new UserService(usersDAO);
		UserController usersController = new UserController(usersService); 

		CustomerDAO customersDAO = new CustomerDAO("data/customers.json");
		CustomerService customerService = new CustomerService(customersDAO);
		CustomerController customerController = new CustomerController(customerService); 
		
		SupplierDAO supplierDAO = new SupplierDAO("data/suppliers.json");
		SupplierService supplierService = new SupplierService(supplierDAO);
		SupplierController supplierController = new SupplierController(supplierService);

		MenagerDAO menagerDAO = new MenagerDAO("data/menagers.json");
		MenagerService menagerService = new MenagerService(menagerDAO);
		MenagerController menagerController = new MenagerController(menagerService);

		RestaurantDAO restaurantDAO = new RestaurantDAO("data/restaurants.json");
		RestaurantService restaurantService=new RestaurantService(restaurantDAO);
		RestaurantController restaurantController=new RestaurantController(restaurantService);

		AddressDAO addressDAO=new AddressDAO("data/addresses.json");
		AddressService addressService=new AddressService(addressDAO);
		AddressController addressController=new AddressController(addressService);

		LocationDAO locationDAO=new LocationDAO("data/locations.json");
		LocationService locationService=new LocationService(locationDAO);
		LocationController locationController=new LocationController(locationService);

		ArticleDAO articleDAO=new ArticleDAO("data/articles.json");
		ArticleService articleService=new ArticleService(articleDAO);
		ArticleController articleController=new ArticleController(articleService);
		
		get("/test/", "text/html", (req, res) -> {
			return usersService.Proba();
		});
		
		
		post("/registerCustomer","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				Boolean r=usersController.register(user);
				if(r){
					Customer customer=new Customer(user);
					customerController.register(customer);
				}
			return r;
			
		});
		
		post("/login","application/json", (req,res) -> {
			res.type("application/json");	
			try {
				User loggedUser= usersService.login(gson.fromJson(req.body(), UserLogInDTO.class));
				if(loggedUser != null){
					Session session = req.session(true);
					session.attribute("user", loggedUser);
					return gson.toJson(loggedUser);
				} else {
					return "Nije pronasao korisnika";
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				return "";
			}			
		});
		

		 post("/updatePersonalInfo","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				usersController.updatePersonalInfo(user);
				Session session = req.session(true);
				session.attribute("user", user);
				
		   return user;
		});
		

		post("/registerSupplier","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				Boolean r=usersController.register(user);
				if(r){
					Supplier supplier=new Supplier(user);
					supplierController.register(supplier);
				}
			return r;
			
		});
		//setovati na frontu da ako se samo registrujje menager kao restaurantName salje x 
		post("/registerManager","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				Boolean r=usersController.register(user);
				if(r){
					Menager menager=new Menager(user);
					menagerController.register(menager);
				}
			return r;
			
		});

		post("/registerRestaurant","application/json", (req,res) -> {
			res.type("application/json");	
			
			Restaurant restaurant= gson.fromJson(req.body(), Restaurant.class);
			Boolean r = restaurantController.register(restaurant);
			return gson.toJson(restaurant);
		
		});

		
		get("/getAllUsers", "application/json", (req, res) -> {
			res.type("application/json");	
			ArrayList<User> users =  usersController.getAllUsers();
			return gson.toJson(users);

		});
		
		get("/getUser", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User loggedUser = session.attribute("user");
				return gson.toJson(loggedUser);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
		
		get("/usersSearchByName/:name", "application/json", (req, res) -> {
			res.type("application/json");	
			String name= req.params("name");
			ArrayList<User> users = usersController.usersSearchByName(name);
			return gson.toJson(users);
		});

		get("/usersSearchByUserName/:username", "application/json", (req, res) -> {
			res.type("application/json");	
			String username= req.params("username");
			ArrayList<User> users = usersController.usersSearchByUserName(username);
			return gson.toJson(users);
		});

		get("/usersSearchBySurname/:surname", "application/json", (req, res) -> {
			res.type("application/json");	
			String surname= req.params("surname");
			ArrayList<User> users = usersController.usersSearchBySurname(surname);
			return gson.toJson(users);

		});

		get("/userSortByNameAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortByNameAsc();
		});

		get("/userSortByNameDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortByNameDesc();
		});

		get("/userSortBySurnameAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortBySurnameAsc();
		});

		get("/userSortBySurnameDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortBySurnameDesc();
		});

		get("/userSortByUsernameAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortByUsernameAsc();
		});

		get("/userSortByUsernameDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.userSortByUsernameDesc();
		});

		get("/customerSortByUserPointAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return customerController.userSortByUserPointAsc();
		});

		get("/customerSortByUserPointsDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return customerController.userSortByUserPointsDesc();
		});

		
		get("/customersFiltrateByType/:type", "application/json", (req, res) -> {
			res.type("application/json");	
			String type= req.params("type");
			List<Customer> customers = customerController.customerFiltrateByType(type);
			return gson.toJson(customers);
		});

		get("/usersFiltrateByRole/:role", "application/json", (req, res) -> {
			res.type("application/json");	
			String role= req.params("role");
			List<User> users = usersController.usersFiltrateByRole(role);
			return gson.toJson(users);
		});


	/*	get("/combineSearchUser", "application/json", (req, res) -> {
			res.type("application/json");	
			String name= req.queryParams("name");
			String username= req.params("username");
			String surname= req.params("surname");

			return usersController.combineSearchUser(name,surname,username);
		});
*/

		get("/getAllMenagersWithoutRestaurant", "application/json", (req, res) -> {
			res.type("application/json");	
			ArrayList<Menager> menagers =  menagerController.getAllManagersWithoutRestaurant();
			return gson.toJson(menagers);

		});
		
		
		get("/restourantSearchByName/:name", "application/json", (req, res) -> {
			res.type("application/json");	
			String restourantName= req.params("name");
			return restaurantController.restourantSearchByName(restourantName);
		});

		get("/restourantSearchByType/:type", "application/json", (req, res) -> {
			res.type("application/json");	
			String type= req.params("type");
			return restaurantController.restourantSearchByType(type);
		});

		get("/restourantSearchByLocation/:location", "application/json", (req, res) -> {
			res.type("application/json");	
			String location= req.params("location");
			return restaurantController.restourantSearchByLocation(location);
		});

		get("/restaurantSortByNameAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restaurantSortByNameAsc();
		});

		get("/restaurantSortByNameDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restaurantSortByNameDesc();
		});

		get("/restaurantSortByLocationAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restaurantSortByLocationAsc();
		});

		get("/restaurantSortByLocationDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restaurantSortByLocationDesc();
		});

		get("/restauranSortByGradeAsc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restauranSortByGradeAsc();
		});

		get("/restauranSortByGradeDesc", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.restauranSortByGradeDesc();
		});

		get("/restaurantsFiltrateByType/:type", "application/json", (req, res) -> {
			res.type("application/json");	
			String type= req.params("type");

			return restaurantController.restaurantsFiltrateByType(type);
		});
		
		get("/restaurantsFiltrateByStatus/:status", "application/json", (req, res) -> {
			res.type("application/json");	
			String role= req.params("status");

			return restaurantController.restaurantsFiltrateByStatus(role);
		});

		/*get("/combineSearchRestaurant", "application/json", (req, res) -> {
			res.type("application/json");	
			String type= req.queryParams("type");
			String status= req.queryParams("status");

			return restaurantController.combineSearchRestaurant(type,status);
		});
*/
		get("/getRestaurantsOpenAndClosed", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.getRestaurantsOpenAndClosed();
		});

		 post("/registerArticle","application/json", (req,res) -> {
		 	res.type("application/json");	
		 		Article article=gson.fromJson(req.body(), Article.class);
		 		Boolean r=articleController.register(article);
			return r;
		 });

		 post("/upadateArticle","application/json", (req,res) -> {
			res.type("application/json");	
				Article article=gson.fromJson(req.body(), Article.class);
				articleController.updateArticle(article);
		   return article;
		});
/*
		get("/getRestaurantByName", "application/json", (req, res) -> {
			res.type("application/json");	
			String name= req.queryParams("name");
			return restaurantController.gerRestaurantByName(name);
		});*/
		 
		 
		 post("/addRestaurantToManager/:username", (req, res) -> {
			res.type("application/json");
			 
			Menager menager = menagerController.getMenagerByUsername(req.params("username"));
   			Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);

   			menager.setRestaurant(restaurant.getName());
   			menagerService.update(menager);
   			return gson.toJson(menager);
		 });
		 
	}
}


