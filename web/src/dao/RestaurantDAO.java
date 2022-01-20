package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Article;
import model.Restaurant;
import model.RestaurantStatus;
import model.User;

public class RestaurantDAO implements IDAO<Restaurant, String> {

	private String path;
	private ArrayList<Restaurant> restaurants;

	public RestaurantDAO(String path) {
		super();
		this.path = path;
		try {
			getAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Restaurant> getAll() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Restaurant>>() {
		}.getType();
		BufferedReader br = new BufferedReader(new FileReader("data/restaurants.json"));
		this.restaurants = gson.fromJson(br, token);
		return restaurants;
	}

	@Override
	public Restaurant getByID(String id) throws JsonSyntaxException, IOException {
		Restaurant wantedRestaurant = null;
		ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) getAll();
		if (restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if (restaurant.getName().equals(id)) {
					wantedRestaurant = restaurant;
					break;
				}
			}
		}
		return wantedRestaurant;
	}

	@Override
	public void create(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		if (restaurants == null) {
			restaurants = new ArrayList<Restaurant>();
		}

		restaurants.add(entity);
		saveAll(restaurants);
	}

	@Override
	public void update(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		for (Restaurant res : restaurants) {
			if (res.getName().equals(entity.getName())) {
				restaurants.set(restaurants.indexOf(res), entity);
				break;
			}
		}
		saveAll(restaurants);

	}

	@Override
	public void delete(Restaurant entity) throws JsonSyntaxException, IOException {
		return;
	}

	@Override
	public void save(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		restaurants.add(entity);
		saveAll(restaurants);

	}

	@Override
	public void saveAll(ArrayList<Restaurant> entities) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Restaurant>>() {
		}.getType());
		writer.println(allEntities);
		writer.close();
	}

	@Override
	public ArrayList<Restaurant> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Restaurant> restourantSearchByName(String restaurantName) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants = getAll();
		ArrayList<Restaurant> nameSearchList = new ArrayList<>();

		if (allRestaurants.size() != 0) {
			for (Restaurant restaurant : allRestaurants) {
				if (restaurant.name.toLowerCase().contains(restaurantName.toLowerCase())) {
					nameSearchList.add(restaurant);
				}
			}
		}
		return nameSearchList;
	}

	public ArrayList<Restaurant> restourantSearchByType(String type) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants = getAll();
		ArrayList<Restaurant> typeSearchList = new ArrayList<>();

		if (allRestaurants.size() != 0) {
			for (Restaurant restaurant : allRestaurants) {
				if (restaurant.type.toLowerCase().contains(type.toLowerCase())) {
					typeSearchList.add(restaurant);
				}
			}
		}
		return typeSearchList;
	}

	public ArrayList<Restaurant> restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants = getAll();
		ArrayList<Restaurant> locationSearchList = new ArrayList<>();

		if (allRestaurants.size() != 0) {
			for (Restaurant restaurant : allRestaurants) {
				if (restaurant.location.getAddress().getCountry().toLowerCase().contains(location.toLowerCase()) ||
						restaurant.location.getAddress().getCity().toLowerCase().contains(location.toLowerCase())) {
					locationSearchList.add(restaurant);
				}
			}
		}
		return locationSearchList;
	}

	public List<Restaurant> restaurantSortByNameAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		Set<Restaurant> toSort = new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());

		return resultList;
	}

	public List<Restaurant> restaurantSortByNameDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		Set<Restaurant> toSort = new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;
	}

	public List<Restaurant> restaurantSortByLocationAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		Set<Restaurant> toSort = new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted(
				(e1, e2) -> e1.getLocation().getAddress().getCity().compareTo(e2.getLocation().getAddress().getCity()))
				.collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;
	}

	public List<Restaurant> restauranSortByGradeAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		Set<Restaurant> toSort = new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}
		HashMap<Double, String> restaurantGrades = new HashMap<>();

		for (Restaurant restaurant : restaurants) {
			int grade = 0;
			Double gradePerRestaurant = 0.0;
			for (int g : restaurant.getGrade()) {
				grade += g;
			}
			gradePerRestaurant = (double) (grade * 1.0 / (restaurant.getGrade().size()));
			restaurantGrades.put(gradePerRestaurant, restaurant.getName());
		}
		List<Double> grades = new ArrayList<>(restaurantGrades.keySet());
		Collections.sort(grades);
		ArrayList<Restaurant> resultList = new ArrayList<>();
		for (Double i : grades) {
			Restaurant r = getRestaurantByName(restaurantGrades.get(i));
			resultList.add(r);
		}
		return resultList;
	}

	public Double getRestauratAverageGrade(Restaurant restaurant) throws JsonSyntaxException, IOException {
		Double averageGrade=0.0;
		Integer totalGrade=0;
		for(Integer i : restaurant.getGrade()){
			totalGrade+=i;
		}
		averageGrade=(double) (totalGrade/restaurant.getGrade().size());
		return averageGrade;
	}


	public List<Restaurant> restauranSortByGradeDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		List<Restaurant> restaurants2 = restauranSortByGradeAsc(restaurants);
		Collections.reverse(restaurants2);
		return restaurants2;
	}

	public List<Restaurant> restaurantSortByLocationDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		List<Restaurant> restaurants2 = restaurantSortByLocationAsc(restaurants);
		Collections.reverse(restaurants2);
		return restaurants2;
	}

	public List<Restaurant> restaurantsFiltrateByType(String type, List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> resultList = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			if (restaurant.getType().toLowerCase().equals(type.toLowerCase())) {
				resultList.add(restaurant);
			}
		}
		return resultList;
	}

	public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> resultList = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			if (restaurant.getStatus().toString().toLowerCase().equals(status.toLowerCase())) {
				resultList.add(restaurant);
			}
		}
		return resultList;
	}

	/*
	 * public List<Restaurant> combineSearchRestaurant(String type, String status)
	 * throws JsonSyntaxException, IOException {
	 * ArrayList<Restaurant> allRestaurants=getAll();
	 * List<Restaurant> typeList=new ArrayList<Restaurant>();
	 * List<Restaurant> statusList=new ArrayList<Restaurant>();
	 * 
	 * if(type==null || type.isBlank())
	 * typeList=allRestaurants;
	 * else
	 * typeList=restaurantsFiltrateByType(type);
	 * 
	 * if(status==null || status.isBlank())
	 * statusList=allRestaurants;
	 * else
	 * statusList=restaurantsFiltrateByStatus(status);
	 * 
	 * List<Restaurant> intersectionResult=new ArrayList<Restaurant>();
	 * 
	 * for(Restaurant restaurant :typeList){
	 * for(Restaurant restaurant2: statusList){
	 * if(restaurant.getName().equals(restaurant2.getName()) ){
	 * intersectionResult.add(restaurant);
	 * }
	 * }
	 * }
	 * 
	 * return intersectionResult;
	 * }
	 */

	public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
		List<Restaurant> resultList = new ArrayList<>();
		List<Restaurant> openList = restaurantsFiltrateByStatus("OPEN");
		List<Restaurant> closedList = restaurantsFiltrateByStatus("CLOSED");
		resultList = Stream.concat(openList.stream(), closedList.stream())
				.collect(Collectors.toList());
		return resultList;
	}

	public Restaurant getRestaurantByName(String restaurantName) throws JsonSyntaxException, IOException {
		Restaurant result = new Restaurant();
		ArrayList<Restaurant> restaurants = getAll();
		for (Restaurant restaurant : restaurants) {
			if (restaurant.name.trim().toLowerCase().equals(restaurantName.trim().toLowerCase())) {
				result = restaurant;
			}
		}
		return result;
	}

	public ArrayList<Restaurant> restaurantSearchByGrade(double gradeToCompare) throws JsonSyntaxException, IOException {
		Set<Restaurant> toSort = new HashSet<>();
		ArrayList<Restaurant> restaurants=getAll();
		for (Restaurant object : restaurants) {
			toSort.add(object);
		}
		HashMap<Double, String> restaurantGrades = new HashMap<>();

		for (Restaurant restaurant : restaurants) {
			int grade = 0;
			Double gradePerRestaurant = 0.0;
			for (int g : restaurant.getGrade()) {
				grade += g;
			}
			gradePerRestaurant = (double) (grade * 1.0 / (restaurant.getGrade().size()));
			restaurantGrades.put(gradePerRestaurant, restaurant.getName());
		}
		List<Double> grades = new ArrayList<>(restaurantGrades.keySet());
		Collections.sort(grades);
		ArrayList<Restaurant> resultList = new ArrayList<>();
		for (Double i : grades) {
			Restaurant r = getRestaurantByName(restaurantGrades.get(i));
			if(i==gradeToCompare){
				resultList.add(r);
			}
		}

		return resultList;
	}

	public Boolean isRestaurantOpen(String params) throws JsonSyntaxException, IOException {
		Restaurant restaurant = getRestaurantByName(params);
		if (restaurant.status.equals(RestaurantStatus.OPEN)) {
			return true;
		}
		return false;
	}

	public ArrayList<Article> getArticlesFromRestaurant(String params) throws JsonSyntaxException, IOException {
		Restaurant restaurant = getRestaurantByName(params);
		return restaurant.getArticles();

	}

}
