package main;

import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AddressController;
import controller.ArticleController;
import controller.CommentController;
import controller.CustomerController;
import controller.LocationController;
import controller.MenagerController;
import controller.OrderController;
import controller.RestaurantController;
import controller.ShoppingCartController;
import controller.SupplierController;
import controller.SupplierRequestController;
import controller.UserController;
import dao.AddressDAO;
import dao.ArticleDAO;
import dao.CommentDAO;
import dao.CustomerDAO;
import dao.LocationDAO;
import dao.MenagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dao.ShoppingCartDAO;
import dao.SupplierDAO;
import dao.SupplierRequestDAO;
import dao.UserDAO;
import dto.UserLogInDTO;
import model.Article;
import model.Comment;
import model.CommentStatus;
import model.Customer;
import model.Menager;
import model.Order;
import model.OrderStatus;
import model.Restaurant;
import model.ShoppingCart;
import model.ShoppingCartItem;
import model.Supplier;
import model.User;
import service.AddressService;
import service.ArticleService;
import service.CommentService;
import service.CustomerService;
import service.LocationService;
import service.MenagerService;
import service.OrderService;
import service.RestaurantService;
import service.ShoppingCartService;
import service.SupplierRequestService;
import service.SupplierService;
import service.UserService;
import spark.Session;

public class main {

	private static Gson g = new Gson();
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

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
		RestaurantService restaurantService = new RestaurantService(restaurantDAO);
		RestaurantController restaurantController = new RestaurantController(restaurantService);

		AddressDAO addressDAO = new AddressDAO("data/addresses.json");
		AddressService addressService = new AddressService(addressDAO);
		AddressController addressController = new AddressController(addressService);

		LocationDAO locationDAO = new LocationDAO("data/locations.json");
		LocationService locationService = new LocationService(locationDAO);
		LocationController locationController = new LocationController(locationService);

		ArticleDAO articleDAO = new ArticleDAO("data/articles.json");
		ArticleService articleService = new ArticleService(articleDAO);
		ArticleController articleController = new ArticleController(articleService);

		ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO("data/shoppingCarts.json");
		ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartDAO);
		ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartService);

		OrderDAO orderDAO = new OrderDAO("data/orders.json");
		OrderService orderService = new OrderService(orderDAO);
		OrderController orderController = new OrderController(orderService);

		SupplierRequestDAO supplierRequestDAO = new SupplierRequestDAO("data/supplierRequest.json");
		SupplierRequestService supplierRequestService = new SupplierRequestService(supplierRequestDAO);
		SupplierRequestController supplierRequestController = new SupplierRequestController(supplierRequestService);

		CommentDAO commentDAO = new CommentDAO("data/comments.json");
		CommentService commentService = new CommentService(commentDAO);
		CommentController commentController = new CommentController(commentService);

		get("/test/", "text/html", (req, res) -> {
			return usersService.Proba();
		});

		post("/registerCustomer", "application/json", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			Boolean r = usersController.register(user);
			if (r) {
				Customer customer = new Customer(user);
				customerController.register(customer);
			}
			return r;

		});

		post("/login", "application/json", (req, res) -> {
			res.type("application/json");
			try {
				User loggedUser = usersService.login(gson.fromJson(req.body(), UserLogInDTO.class));
				if (loggedUser != null) {
					Session session = req.session(true);
					session.attribute("user", loggedUser);
					return gson.toJson(loggedUser);
				} else {
					return "Nije pronasao korisnika";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});

		post("/updatePersonalInfo", "application/json", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			usersController.updatePersonalInfo(user);
			Session session = req.session(true);
			session.attribute("user", user);

			return user;
		});

		post("/registerSupplier", "application/json", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			Boolean r = usersController.register(user);
			if (r) {
				Supplier supplier = new Supplier(user);
				supplierController.register(supplier);
			}
			return r;

		});
		// setovati na frontu da ako se samo registrujje menager kao restaurantName
		// salje x
		post("/registerManager", "application/json", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			Boolean r = usersController.register(user);
			if (r) {
				Menager menager = new Menager(user);
				menagerController.register(menager);
			}
			return r;

		});

		post("/registerRestaurant", "application/json", (req, res) -> {
			res.type("application/json");

			Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
			Boolean r = restaurantController.register(restaurant);
			return gson.toJson(restaurant);

		});

		get("/getAllUsers", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<User> users = usersController.getAllUsers();
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
			String name = req.params("name");
			ArrayList<User> users = usersController.usersSearchByName(name);
			return gson.toJson(users);
		});

		get("/usersSearchByUserName/:username", "application/json", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			ArrayList<User> users = usersController.usersSearchByUserName(username);
			return gson.toJson(users);
		});

		get("/usersSearchBySurname/:surname", "application/json", (req, res) -> {
			res.type("application/json");
			String surname = req.params("surname");
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
			String type = req.params("type");
			List<Customer> customers = customerController.customerFiltrateByType(type);
			return gson.toJson(customers);
		});

		get("/usersFiltrateByRole/:role", "application/json", (req, res) -> {
			res.type("application/json");
			String role = req.params("role");
			List<User> users = usersController.usersFiltrateByRole(role);
			return gson.toJson(users);
		});

		/*
		 * get("/combineSearchUser", "application/json", (req, res) -> {
		 * res.type("application/json");
		 * String name= req.queryParams("name");
		 * String username= req.params("username");
		 * String surname= req.params("surname");
		 * 
		 * return usersController.combineSearchUser(name,surname,username);
		 * });
		 */

		get("/getAllMenagersWithoutRestaurant", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Menager> menagers = menagerController.getAllManagersWithoutRestaurant();
			return gson.toJson(menagers);

		});

		get("/restourantSearchByName/:name", "application/json", (req, res) -> {
			res.type("application/json");
			String restourantName = req.params("name");
			ArrayList<Restaurant> restaurants = restaurantController.restourantSearchByName(restourantName);
			return gson.toJson(restaurants);
		});

		get("/restourantSearchByType/:type", "application/json", (req, res) -> {
			res.type("application/json");
			String type = req.params("type");
			ArrayList<Restaurant> restaurants = restaurantController.restourantSearchByType(type);
			return gson.toJson(restaurants);
		});

		get("/restourantSearchByLocation/:location", "application/json", (req, res) -> {
			res.type("application/json");
			String location = req.params("location");
			ArrayList<Restaurant> restaurants = restaurantController.restourantSearchByLocation(location);
			return gson.toJson(restaurants);
		});

		get("/restourantSearchByGrade/:grade", "application/json", (req, res) -> {
			res.type("application/json");
			String grade = req.params("grade");
			double grade1 = Double.parseDouble(grade);
			ArrayList<Restaurant> restaurants = restaurantController.restourantSearchByGrade(grade1);
			return gson.toJson(restaurants);
		});

		get("/restaurantSortByNameAsc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restaurantSortByNameAsc();
			return gson.toJson(restaurants);
		});

		get("/restaurantSortByNameDesc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restaurantSortByNameDesc();
			return gson.toJson(restaurants);
		});

		get("/restaurantSortByLocationAsc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restaurantSortByLocationAsc();
			return gson.toJson(restaurants);
		});

		get("/restaurantSortByLocationDesc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restaurantSortByLocationDesc();
			return gson.toJson(restaurants);
		});

		get("/restauranSortByGradeAsc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restauranSortByGradeAsc();
			return gson.toJson(restaurants);
		});

		get("/restauranSortByGradeDesc", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.restauranSortByGradeDesc();
			return gson.toJson(restaurants);
		});

		get("/restaurantsFiltrateByType/:type", "application/json", (req, res) -> {
			res.type("application/json");
			String type = req.params("type");
			List<Restaurant> restaurants = restaurantController.restaurantsFiltrateByType(type);
			return gson.toJson(restaurants);
		});

		get("/restaurantsFiltrateByStatus/:status", "application/json", (req, res) -> {
			res.type("application/json");
			String role = req.params("status");
			List<Restaurant> restaurants = restaurantController.restaurantsFiltrateByStatus(role);
			return gson.toJson(restaurants);
		});

		/*
		 * get("/combineSearchRestaurant", "application/json", (req, res) -> {
		 * res.type("application/json");
		 * String type= req.queryParams("type");
		 * String status= req.queryParams("status");
		 * 
		 * return restaurantController.combineSearchRestaurant(type,status);
		 * });
		 */
		get("/getRestaurantsOpenAndClosed", "application/json", (req, res) -> {
			res.type("application/json");
			List<Restaurant> restaurants = restaurantController.getRestaurantsOpenAndClosed();
			return gson.toJson(restaurants);
		});

		post("/addArticleToRestaurant", "application/json", (req, res) -> {
			res.type("application/json");
			Article article = gson.fromJson(req.body(), Article.class);
			Boolean r = articleController.addArticleToRestaurant(article);
			return r;
		});

		post("/updateArticle", "application/json", (req, res) -> {
			res.type("application/json");
			Article article = gson.fromJson(req.body(), Article.class);
			articleController.updateArticle(article);
			return article;
		});

		get("/getAllArticles", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Article> articles = articleController.getAll();
			return gson.toJson(articles);
		});

		post("/addRestaurantToManager/:username", (req, res) -> {
			res.type("application/json");

			Menager menager = menagerController.getMenagerByUsername(req.params("username"));
			Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);

			menager.setRestaurant(restaurant.getName());
			menagerService.update(menager);
			return gson.toJson(menager);
		});

		get("/getRestaurantByName/:name", "application/json", (req, res) -> {
			res.type("application/json");
			Restaurant restaurant = restaurantController.getRestaurantByName(req.params("name"));
			return gson.toJson(restaurant);
		});

		get("/getOpenedRestaurants", "application/json", (req, res) -> {
			ArrayList<Restaurant> restaurants = restaurantController.getOpenedRestaurants();
			return gson.toJson(restaurants);
		});

		get("/isRestaurantOpen", "application/json", (req, res) -> {
			Boolean result = restaurantController.isRestaurantOpen(req.params("restaurantName"));
			return gson.toJson(result);
		});

		get("/getArticlesFromRestaurant/:name", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Article> articles = articleController.gettArticlesByRestaurantName(req.params("name"));
			return gson.toJson(articles);
		});

		// kad napravis korpu, sacuvaj njen id u localStorage-u jer nam treba za metodu
		// koja popunjava korpu sa artiklima
		post("/createShoppingCart", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCart shoppingCart = gson.fromJson(req.body(), ShoppingCart.class);
			Boolean r = shoppingCartService.addShoppingCart(shoppingCart);
			return r;
		});

		post("/addArticlesCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double priceForArticle = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			return shoppingCartService.addArticleToShoppingCart(priceForArticle, req.params("shoppingCartId"),
					shoppingCartItem);
		});

		get("/getShoppingChartById/:id", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("id"));
			return gson.toJson(shoppingCart);
		});

		get("/getInfoAboutArticle/:aricleName/:restourantName", "application/json", (req, res) -> {
			res.type("application/json");
			Article article = articleController.getInfoAboutArticle(req.params("aricleName"),
					req.params("restourantName"));
			return gson.toJson(article);
		});

		post("/deteleArticlesFromCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double priceForArticle = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			return shoppingCartService.deteleArticlesFromCart(priceForArticle, req.params("shoppingCartId"),
					shoppingCartItem);
		});

		post("/updateArticleFromCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double newPrice = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			ShoppingCartItem oldShoppingCartItem = new ShoppingCartItem();
			for (ShoppingCartItem sci : shoppingCart.getItems()) {
				if (sci.getArticle().equals(shoppingCartItem.getArticle())) {
					oldShoppingCartItem = sci;
				}
			}
			Double priceToSub = articleController.getPricePerArticle(shoppingCart.restaurantName,
					oldShoppingCartItem);
			return shoppingCartService.updateArticleFromCart(newPrice, priceToSub, req.params("shoppingCartId"),
					shoppingCartItem);

		});

		post("/addOrder/:username", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCart shoppingCart = gson.fromJson(req.body(), ShoppingCart.class);
			Order order = orderController.addOrder(shoppingCart);

			// mora sa se username korisnika koji saljemo kao parametar u putanji,sacuvaj
			// cijelog korisnika ili njegovo ime u localStoragu kad se loguje i onda ovde
			// posalje
			customerController.updateUsersPoints(req.params("username"), shoppingCart.price);
			return order;
		});

		// mozda staviti neku listu samo onih porudybina koje su u statusu obrada pa
		// odatle da ih otkazuje
		post("/cancelOrder/:username/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToCanceled(req.params("orderId"));
			customerController.updateUsersPointsAferCancellation(req.params("username"), order.getPrice());
			return order;
		});

		get("/getOrderWithStatusInPreparation", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrderWithStatusInPreparation();
			return orders;
		});

		get("/getOrderWithStatusWaitingForSupplier", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrderWithStatusWaitingForSupplier();
			return orders;
		});

		post("/changeStatusToInPreparation/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToInPreparation(req.params("orderId"));
			return order;
		});

		post("/changeStatusToWaitingForSupplier/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToWaitingForSupplier(req.params("orderId"));
			return order;
		});

		post("/changeStatusToInTransport/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToInTransport(req.params("orderId"));
			return order;
		});

		post("/changeStatusToDelivered/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToDelivered(req.params("orderId"));
			return order;
		});

		post("/changeStatusToCanceled/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToCanceled(req.params("orderId"));
			return order;
		});

		post("/addRequest/:supplierUsername/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Boolean result = supplierRequestController.addRequest(req.params("supplierUsername"),
					req.params("orderId"));
			return result;
		});

		post("/processSupplierRequetst/:orderId/:supplierUsername/:par", "application/json", (req, res) -> {
			res.type("application/json");
			// par salji sa fronta, vrijednost ili cancel ili approve
			Boolean result = supplierRequestController.processSupplierRequetst(req.params("orderId"),
					req.params("supplierUsername"),
					req.params("par"));
			if (result) {
				Supplier supplier = supplierController.getByUsername(req.params("supplierUsername"));
				Order order = orderController.getByID(req.params("orderId"));
				ArrayList<Order> orders = supplier.getOrders();
				if (orders == null) {
					orders = new ArrayList<>();
				}
				order.setOrderStatus(OrderStatus.IN_TRANSPORT);
				orderController.update(order);
				orders.add(order);
				supplier.setOrders(orders);
				supplierController.update(supplier);

			}
			return result;
		});
		post("/addComment", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			Restaurant restaurant = restaurantController.getRestaurantByName(comment.getRestaurant());

			ArrayList<Integer> grades = restaurant.getGrade();
			if (grades == null) {
				grades = new ArrayList<>();
			}

			grades.add(comment.grade);
			restaurant.setGrade(grades);
			restaurantController.update(restaurant);
			comment.status = CommentStatus.Processing;
			commentController.addComment(comment);
			return comment;
		});

		post("/approveComment", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			commentController.approveComment(comment);
			return comment;
		});

		post("/rejectComment", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			commentController.rejectComment(comment);
			return comment;
		});

		get("/getCommentsWithStatusApproved", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Comment> comments = commentController.getCommentsWithStatusApproved();
			return gson.toJson(comments);
		});

		get("/getCommentsWithStatusRejected", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Comment> comments = commentController.getCommentsWithStatusRejected();
			return gson.toJson(comments);
		});

		get("/getCommentsWithStatusProcessing", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Comment> comments = commentController.getCommentsWithStatusProcessing();
			return gson.toJson(comments);
		});
	}
}
