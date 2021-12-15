package main;

import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.Console;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.websocket.client.io.UpgradeConnection.SendUpgradeRequest;

import controller.AddressController;
import controller.CustomerController;
import controller.LocationController;
import controller.MenagerController;
import controller.RestaurantController;
import controller.SupplierController;
import controller.UserController;
import dao.AddressDAO;
import dao.CustomerDAO;
import dao.LocationDAO;
import dao.MenagerDAO;
import dao.RestaurantDAO;
import dao.SupplierDAO;
import dao.UserDAO;
import javaxt.utils.Date;
import model.Address;
import model.Customer;
import model.Location;
import model.Menager;
import model.Restaurant;
import model.Supplier;
import model.User;
import service.AddressService;
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
		UserDAO usersDAO = new UserDAO("web/data/users.json");
		UserService usersService = new UserService(usersDAO);
		UserController usersController = new UserController(usersService); 

		CustomerDAO customersDAO = new CustomerDAO("web/data/customers.json");
		CustomerService customerService = new CustomerService(customersDAO);
		CustomerController customerController = new CustomerController(customerService); 
		
		SupplierDAO supplierDAO = new SupplierDAO("web/data/suppliers.json");
		SupplierService supplierService = new SupplierService(supplierDAO);
		SupplierController supplierController = new SupplierController(supplierService);

		MenagerDAO menagerDAO = new MenagerDAO("web/data/menagers.json");
		MenagerService menagerService = new MenagerService(menagerDAO);
		MenagerController menagerController = new MenagerController(menagerService);

		RestaurantDAO restaurantDAO = new RestaurantDAO("web/data/restaurants.json");
		RestaurantService restaurantService=new RestaurantService(restaurantDAO);
		RestaurantController restaurantController=new RestaurantController(restaurantService);

		AddressDAO addressDAO=new AddressDAO("web/data/addresses.json");
		AddressService addressService=new AddressService(addressDAO);
		AddressController addressController=new AddressController(addressService);

		LocationDAO locationDAO=new LocationDAO("web/data/locations.json");
		LocationService locationService=new LocationService(locationDAO);
		LocationController locationController=new LocationController(locationService);
		
		get("/test/", "text/html", (req, res) -> {
			return usersService.Proba();
		});
		
		
		post("/registerCustomer/","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				Boolean r=usersController.register(user);
				if(r){
					Customer customer=new Customer(user);
					customerController.register(customer);
				}
			return r;
			
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
		post("/registerMenager/:restaurantName","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				String restaurant= req.params("restaurantName");
				Boolean r=usersController.register(user);
				if(r){
					Menager menager=new Menager(user);
					menagerController.register(menager,restaurant);
				}
			return r;
			
		});

		get("/getAllUsers", "application/json", (req, res) -> {
			res.type("application/json");	
			return usersController.getAllUsers();
		});

		get("/usersSearchByName/:name", "application/json", (req, res) -> {
			res.type("application/json");	
			String name= req.params("name");
			return usersController.usersSearchByName(name);
		});

		get("/usersSearchByUserName/:username", "application/json", (req, res) -> {
			res.type("application/json");	
			String username= req.params("username");
			return usersController.usersSearchByUserName(username);
		});

		get("/usersSearchBySurname/:surname", "application/json", (req, res) -> {
			res.type("application/json");	
			String surname= req.params("surname");
			return usersController.usersSearchBySurname(surname);
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

			return customerController.customerFiltrateByType(type);
		});

		
		get("/usersFiltrateByRole/:role", "application/json", (req, res) -> {
			res.type("application/json");	
			String role= req.params("role");

			return usersController.usersFiltrateByRole(role);
		});


		get("/combineSearchUser", "application/json", (req, res) -> {
			res.type("application/json");	
			String name= req.queryParams("name");
			String username= req.params("username");
			String surname= req.params("surname");

			return usersController.combineSearchUser(name,surname,username);
		});

		
		post("/registerRestaurant","application/json", (req,res) -> {
			res.type("application/json");	
			String address= req.queryParams("address");
			String location= req.queryParams("location");
			String restaurant= req.queryParams("restaurant");
			String menager= req.queryParams("menager");
			Restaurant restaurant1= gson.fromJson(restaurant, Restaurant.class);
			Menager menager1=gson.fromJson(menager,Menager.class);
			Location location1=gson.fromJson(location,Location.class);
			Address address1=gson.fromJson(address,Address.class);
			menager1.setRestaurant(restaurant1.getName());

			location1.setAddress(address1);
			restaurant1.setLocation(location1);
			Boolean r=false;
			Boolean addressSave=addressService.register(restaurant1.getLocation().getAddress());	
            Boolean locationSave=locationService.register(restaurant1.getLocation());
			if(addressSave && locationSave){
				 r=restaurantController.register(restaurant1);
				 if(r){
					 menagerService.update(menager1);
				 }
			}
			return r;
			
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

		get("/combineSearchRestaurant", "application/json", (req, res) -> {
			res.type("application/json");	
			String type= req.queryParams("type");
			String status= req.queryParams("status");

			return restaurantController.combineSearchRestaurant(type,status);
		});

		get("/getRestaurantsOpenAndClosed", "application/json", (req, res) -> {
			res.type("application/json");	
			return restaurantController.getRestaurantsOpenAndClosed();
		});


	}
}


