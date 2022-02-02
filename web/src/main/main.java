package main;

import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AddressController;
import controller.ArticleController;
import controller.CommentController;
import controller.CustomerController;
import controller.CustomerTypeController;
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
import dao.CustomerTypeDAO;
import dao.LocationDAO;
import dao.MenagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dao.ShoppingCartDAO;
import dao.SupplierDAO;
import dao.SupplierRequestDAO;
import dao.UserDAO;
import dto.OrderFiltrateSortSearchDTO;
import dto.RestaurantSearchDTO;
import dto.RestaurantSearchSortFiltrateDTO;
import dto.SearchFiltrateSortUsersDTO;
import dto.RestaurantSearchSortFiltrateDTO;
import dto.UserLogInDTO;
import model.Article;
import model.Comment;
import model.CommentStatus;
import model.Customer;
import model.CustomerType;
import model.Menager;
import model.Order;
import model.OrderStatus;
import model.Restaurant;
import model.Role;
import model.ShoppingCart;
import model.ShoppingCartItem;
import model.Supplier;
import model.User;
import model.SupplierRequest;
import service.AddressService;
import service.ArticleService;
import service.CommentService;
import service.CustomerService;
import service.CustomerTypeService;
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

		MenagerDAO menagerDAO = new MenagerDAO("data/menagers.json");
		MenagerService menagerService = new MenagerService(menagerDAO);
		MenagerController menagerController = new MenagerController(menagerService);

		SupplierDAO supplierDAO = new SupplierDAO("data/suppliers.json");
		SupplierService supplierService = new SupplierService(supplierDAO);
		SupplierController supplierController = new SupplierController(supplierService);

		CustomerDAO customersDAO = new CustomerDAO("data/customers.json");
		ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO("data/shoppingCarts.json");
		OrderDAO orderDAO = new OrderDAO("data/orders.json");
		OrderService orderService = new OrderService(orderDAO, menagerDAO, supplierDAO, customersDAO, shoppingCartDAO);
		OrderController orderController = new OrderController(orderService);
		
		CustomerService customerService = new CustomerService(customersDAO, orderDAO);
		CustomerController customerController = new CustomerController(customerService);

		UserDAO usersDAO = new UserDAO("data/users.json");
		UserService usersService = new UserService(usersDAO, customersDAO, menagerDAO, supplierDAO);
		UserController usersController = new UserController(usersService);
		
		RestaurantDAO restaurantDAO = new RestaurantDAO("data/restaurants.json");
		RestaurantService restaurantService = new RestaurantService(restaurantDAO, menagerDAO);
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

		ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartDAO, customersDAO);
		ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartService);

		SupplierRequestDAO supplierRequestDAO = new SupplierRequestDAO("data/supplierRequest.json");
		SupplierRequestService supplierRequestService = new SupplierRequestService(supplierRequestDAO);
		SupplierRequestController supplierRequestController = new SupplierRequestController(supplierRequestService);

		CommentDAO commentDAO = new CommentDAO("data/comments.json");
		CommentService commentService = new CommentService(commentDAO, orderDAO, customersDAO);
		CommentController commentController = new CommentController(commentService);

		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO("data/customerTypes.json");
		CustomerTypeService customerTypeService = new CustomerTypeService(customerTypeDAO);
		CustomerTypeController customerTypeController = new CustomerTypeController(customerTypeService);

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
					return "";
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

		get("/customerSortByUserPointAsc", "application/json", (req, res) -> {
			res.type("application/json");
			return customerController.userSortByUserPointAsc();
		});

		get("/customerSortByUserPointsDesc", "application/json", (req, res) -> {
			res.type("application/json");
			return customerController.userSortByUserPointsDesc();
		});

		get("/usersFiltrateByType/:type", "application/json", (req, res) -> {
			res.type("application/json");
			String type = req.params("type");
			List<User> users = usersController.usersFiltrateByType(type); 
			return gson.toJson(users);
		});
		
		get("/usersFiltrateByRole/:role", "application/json", (req, res) -> {
			res.type("application/json");
			String role = req.params("role");
			List<User> users = usersController.usersFiltrateByRole(role);
			return gson.toJson(users);
		});

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

		get("/getArticlesByRestaurantName/:name", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Article> articles = articleController.gettArticlesByRestaurantName(req.params("name"));
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

		post("/delete", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			user.setDeleted(true);
			usersController.updatePersonalInfo(user);
			
			switch (user.getRole()) {
			case CUSTOMER:
				Customer customer = customersDAO.getByID(user.getUsername());
				customer.setDeleted(true);
				customersDAO.update(customer);
				break;
			case MENAGER:
				Menager menager = menagerDAO.getByID(user.getUsername());
				menager.setDeleted(true);
				menagerDAO.update(menager);
				break;
			case SUPPLIER:
				Supplier supplier = supplierDAO.getByID(user.getUsername());
				supplier.setDeleted(true);
				supplierDAO.update(supplier);
				break;
			default:
				break;
			}
			
			return gson.toJson(user);
		});

		post("/deleteRestaurant", (req, res) -> {
			res.type("application/json");
			Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
			restaurant.setDeleted(true);
			restaurantController.update(restaurant);
			
			return gson.toJson(restaurant);
		});
		
		
		get("/getRestaurantByName/:name", "application/json", (req, res) -> {
			res.type("application/json");
			Restaurant restaurant = restaurantController.getRestaurantByName(req.params("name"));
			return gson.toJson(restaurant);
		});

		get("/getRestaurantByManager/:username", "application/json", (req, res) -> {
			res.type("application/json");
			Restaurant restaurant = restaurantController.getRestaurantByManager(req.params("username"));
			return gson.toJson(restaurant);
		});		
		
		get("/getOpenedRestaurants", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Restaurant> allRestaurants = restaurantController.getAll();
			ArrayList<Restaurant> restaurants = restaurantController.getOpenedRestaurants(allRestaurants);
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
			Boolean r = shoppingCartController.addShoppingCart(shoppingCart);
			return gson.toJson(r);
		});

		post("/addArticlesCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double priceForArticle = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			ShoppingCart sc = shoppingCartService.addArticleToShoppingCart(priceForArticle, req.params("shoppingCartId"),
					shoppingCartItem);
			return gson.toJson(sc);
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

		post("/deleteArticlesFromCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double priceForArticle = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			ShoppingCart sc = shoppingCartService.deteleArticlesFromCart(priceForArticle, req.params("shoppingCartId"), shoppingCartItem);
		    return gson.toJson(sc);
		});

		post("/updateArticleFromCart/:shoppingCartId", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCartItem shoppingCartItem = gson.fromJson(req.body(), ShoppingCartItem.class);
			ShoppingCart shoppingCart = shoppingCartController.getById(req.params("shoppingCartId"));
			Double newPrice = articleController.getPricePerArticle(shoppingCart.restaurantName,
					shoppingCartItem);
			ShoppingCartItem oldShoppingCartItem = new ShoppingCartItem();
			for (ShoppingCartItem sci : shoppingCart.getItems()) {
				if (sci.getArticleName().equals(shoppingCartItem.getArticleName())) {
					oldShoppingCartItem = sci;
				}
			}
			Double priceToSub = articleController.getPricePerArticle(shoppingCart.restaurantName,
					oldShoppingCartItem);
			ShoppingCart sc = shoppingCartService.updateArticleFromCart(newPrice, priceToSub, req.params("shoppingCartId"),
					shoppingCartItem);
			 return gson.toJson(sc);
		});

		post("/addOrder/:username", "application/json", (req, res) -> {
			res.type("application/json");
			ShoppingCart shoppingCart = gson.fromJson(req.body(), ShoppingCart.class);

			Customer customer = customerController.getByUsername(shoppingCart.customer);
			Double newPrice = customerTypeController.getPriceWithDiscount(shoppingCart.getPrice(), customer);
			Order order = orderController.addOrder(shoppingCart, customer, newPrice);
			ArrayList<CustomerType> allTypes = customerTypeController.getAllTypes();
			
			customerController.updateUsersPoints(req.params("username"), newPrice, allTypes);
			return gson.toJson(order);
		});

		// mozda staviti neku listu samo onih porudybina koje su u statusu obrada pa
		// odatle da ih otkazuje
		post("/cancelOrder/:username/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToCanceled(req.params("orderId"));
			customerController.updateUsersPointsAferCancellation(req.params("username"), order.getPrice());
			return gson.toJson(order);
		});

		get("/getOrderWithStatusInPreparation", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrderWithStatusInPreparation();
			return gson.toJson(orders);
		});

		get("/getOrderWithStatusWaitingForSupplier", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrderWithStatusWaitingForSupplier();
			return gson.toJson(orders);
		});

		get("/getCustomersWithOrderFromRestaurant/:name", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Customer> customers = customerController.getCustomersWithOrderFromRestaurant(req.params("name"));
			return gson.toJson(customers);
		});
		
		post("/changeStatusToInPreparation/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToInPreparation(req.params("orderId"));
			return order;
		});

		post("/changeStatusToWaitingForSupplier/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToWaitingForSupplier(req.params("orderId"));
			return gson.toJson(order);
		});

		post("/changeStatusToInTransport/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToInTransport(req.params("orderId"));
			return gson.toJson(order);
		});

		post("/changeStatusToDelivered/:orderId/:username", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToDelivered(req.params("orderId"), req.params("username"));
			return gson.toJson(order);
		});

		post("/changeStatusToCanceled/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Order order = orderController.changeStatusToCanceled(req.params("orderId"));
			return order;
		});

		post("/addRequest/:username/:orderId", "application/json", (req, res) -> {
			res.type("application/json");
			Boolean result = supplierRequestController.addRequest(req.params("username"),
					req.params("orderId"));
			return result;
		});

		post("/processSupplierRequetst/:orderId/:username/:par", "application/json", (req, res) -> {
			res.type("application/json");
			// par salji sa fronta, vrijednost ili cancel ili approve
			Boolean result = supplierRequestController.processSupplierRequetst(req.params("orderId"),
					req.params("username"),
					req.params("par"));
			if (result) {
				Supplier supplier = supplierController.getByUsername(req.params("username"));
				Order order = orderController.getByID(req.params("orderId"));
				ArrayList<Order> orders = supplier.getOrders();
				System.out.println(orders);
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
		
		get("/getAllRequestsForDelivering", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<SupplierRequest> requests = supplierRequestController.getAllRequestsForDelivering();
			return gson.toJson(requests);
		});
		
		post("/addComment/:username/:name", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			Restaurant restaurant = restaurantController.getRestaurantByName(comment.getRestaurant());
			ArrayList<Comment> comments = commentController.getAllCommentsByRestaurant(req.params("name"));
			ArrayList<Integer> grades = restaurant.getGrade();
			if (grades == null) {
				grades = new ArrayList<>();
			}

			grades.add(comment.grade);
			restaurant.setGrade(grades);
			restaurantController.update(restaurant);
			comment.status = CommentStatus.Processing;
	        String uniqString = UUID.randomUUID().toString().substring(0, 10);

			for(Comment c: comments) {
				if(c.getId().equals(uniqString)) {
					comment.id = c.id;
				}
			}
			comment.id =  uniqString;
			Boolean result = commentController.addComment(req.params("username"), req.params("name"), comment);
			return result;
		});
		

		post("/approveComment", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			commentController.approveComment(comment);
			return gson.toJson(comment);
		});

		post("/rejectComment", "application/json", (req, res) -> {
			res.type("application/json");
			Comment comment = gson.fromJson(req.body(), Comment.class);
			commentController.rejectComment(comment);
			return gson.toJson(comment);
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
		
		get("/getAllCommentsByRestaurant/:name", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Comment> comments = commentController.getAllCommentsByRestaurant(req.params("name"));
			return gson.toJson(comments);
		});
		
		get("/getMyOwnOrders/:username", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getMyOwnOrders(req.params("username"));
			return gson.toJson(orders);
		});
		
		get("/getOrdersBySupplier/:username", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrdersBySupplier(req.params("username"));
			return gson.toJson(orders);
		});
		
		get("/getOrdersByRestaurantName", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrdersByManager();
			return gson.toJson(orders);
		});

		// za kupca i dostavljaca
		post("/searchFiltrateSortOrders", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Restaurant> restaurants = restaurantController.getAll();
			OrderFiltrateSortSearchDTO orderFiltrateSortSearchDTO = gson.fromJson(req.body(),
					OrderFiltrateSortSearchDTO.class);
			System.out.println(orderFiltrateSortSearchDTO.getSearchByrestaurantName());
			List<Order> orders = orderController.searchFiltreteSortOrders(orderFiltrateSortSearchDTO, restaurants);
			return gson.toJson(orders);
		});

		post("/searchFiltreteSortRestaurants", "application/json", (req, res) -> {
			res.type("application/json");
			RestaurantSearchSortFiltrateDTO restaurantSearchSortFiltrateDTO = gson.fromJson(req.body(),
			RestaurantSearchSortFiltrateDTO.class);
			List<Restaurant> restaurants = restaurantController.searchFiltreteSortRestaurants(restaurantSearchSortFiltrateDTO);
			return gson.toJson(restaurants);
			});


		get("/getOrdersByRestaurant/:name", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrdersByRestaurant(req.params("name"));
			return gson.toJson(orders);
		});

		get("/getOrdersByRestaurantWithStatusDelivered", "application/json", (req, res) -> {
			res.type("application/json");
			ArrayList<Order> orders = orderController.getOrdersByRestaurantWithStatusDelivered();
			return gson.toJson(orders);
		});
		
		post("/updateCustomerType", "application/json", (req, res) -> {
			res.type("application/json");
			CustomerType customerType = gson.fromJson(req.body(), CustomerType.class);
			customerTypeController.update(customerType);
			return customerType;
		});
	}
}
