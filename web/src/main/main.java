package main;

import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.Console;
import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.CustomerController;
import controller.UserController;
import dao.CustomerDAO;
import dao.UserDAO;
import javaxt.utils.Date;
import model.Customer;
import model.User;
import service.CustomerService;
import service.UserService;
import spark.Request;
import spark.Session;

public class main {

	
	private static Gson g = new Gson();
    private static Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		UserDAO usersDAO = new UserDAO("./data/users.json");
		UserService usersService = new UserService(usersDAO);
		UserController usersController = new UserController(usersService); 
		CustomerDAO customersDAO = new CustomerDAO("./data/customers.json");
		CustomerService customerService = new CustomerService(customersDAO);
		CustomerController customerController = new CustomerController(customerService); 
		
		
		get("/test/", "text/html", (req, res) -> {
			return usersService.Proba();
		});
		
		
		post("/register/","application/json", (req,res) -> {
			res.type("application/json");	
				User user=gson.fromJson(req.body(), User.class);
				Boolean r=usersController.register(user);
				if(r){
					Customer customer=new Customer(user);
					customerController.register(customer);
				}
			return r;
			
		});
		
	}
}
		/*
	
		
		get("/rest/proizvodi/getJustProducts", (req, res) -> {
			res.type("application/json");
			return g.toJson(products.values());
		});
		
		get("/rest/proizvodi/getJustSc", (req, res) -> {
			res.type("application/json");
			return g.toJson(getSc(req).getItems());
		});
		
		get("/rest/proizvodi/getTotal", (req, res) -> {
			res.type("application/json");
			return g.toJson(getSc(req).getTotal());
		});
		
		post("/rest/proizvodi/add", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			ProductToAdd pd = g.fromJson(payload, ProductToAdd.class);
			getSc(req).addItem(products.getProduct(pd.id), pd.count);
			return ("OK");
		});
		
		post("/rest/proizvodi/clearSc", (req, res) -> {
			res.type("application/json");
			getSc(req).getItems().clear();
			return "OK";
		});
	}
	
	private static ShoppingCart getSc(Request req) {
		Session ss = req.session(true);
		ShoppingCart sc = ss.attribute("sc"); 
		if (sc == null) {
			sc = new ShoppingCart();
			ss.attribute("sc", sc);
		}
		return sc;
	}

}*/