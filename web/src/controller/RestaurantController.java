package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dto.RestaurantSearchDTO;
import dto.RestaurantSearchSortFiltrateDTO;
import model.Article;
import model.Menager;
import model.Restaurant;
import service.AddressService;
import service.LocationService;
import service.MenagerService;
import service.RestaurantService;

public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException {
        Boolean restourantSave = restaurantService.register(restaurant);
        return restourantSave;
    }

    public ArrayList<Restaurant> restourantSearchByName(String restourantName) throws JsonSyntaxException, IOException {
        return restaurantService.restourantSearchByName(restourantName);
    }

    public ArrayList<Restaurant> getAll() throws JsonSyntaxException, IOException {
        return restaurantService.getAllRestaurants();
    }

    public ArrayList<Restaurant> restourantSearchByType(String type) throws JsonSyntaxException, IOException {
        return restaurantService.restourantSearchByType(type);
    }

    public ArrayList<Restaurant> restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
        return restaurantService.restourantSearchByLocation(location);
    }

    public List<Restaurant> restaurantSortByNameAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restaurantSortByNameAsc(restaurants);
    }

    public List<Restaurant> restaurantSortByNameDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restaurantSortByNameDesc(restaurants);
    }

    public List<Restaurant> restaurantSortByLocationAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restaurantSortByLocationAsc(restaurants);
    }

    public List<Restaurant> restaurantSortByLocationDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantService.restaurantSortByLocationDesc(restaurants);
    }

    public List<Restaurant> restauranSortByGradeAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restauranSortByGradeAsc(restaurants);
    }

    public List<Restaurant> restauranSortByGradeDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restauranSortByGradeDesc(restaurants);
    }

    public List<Restaurant> restaurantsFiltrateByType(String type, List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
         return restaurantService.restaurantsFiltrateByType(type, restaurants);
    }

    public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
        return restaurantService.restaurantsFiltrateByStatus(status);
    }

    public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
        return restaurantService.getRestaurantsOpenAndClosed();
    }

    public Restaurant getRestaurantByName(String name) throws JsonSyntaxException, IOException {
        return restaurantService.getRestaurantByName(name);
    }

    public ArrayList<Restaurant> getOpenedRestaurants(List<Restaurant> allRestaurants) throws JsonSyntaxException, IOException {
         return restaurantService.getOpenedRestaurants(allRestaurants);
    }

    public ArrayList<Restaurant> restourantSearchByGrade(double grade) throws JsonSyntaxException, IOException {
        return restaurantService.restaurantSearchByGrade(grade);
    }

    public Boolean isRestaurantOpen(String params) throws JsonSyntaxException, IOException {
        return restaurantService.isRestaurantOpen(params);
    }

    public ArrayList<Article> getArticlesFromRestaurant(String params) throws JsonSyntaxException, IOException {
        return restaurantService.getArticlesFromRestaurant(params);
    }

    public void update(Restaurant restaurant) throws JsonSyntaxException, IOException {
        restaurantService.update(restaurant);
    }

    public List<Restaurant> searchFiltreteSortRestaurants(
            RestaurantSearchSortFiltrateDTO restaurantSearchSortFiltrateDTO) throws JsonSyntaxException, IOException {
        return restaurantService.searchFiltreteSortRestaurants(restaurantSearchSortFiltrateDTO);
    }

	public Restaurant getRestaurantByManager(String username) throws JsonSyntaxException, IOException {
		return restaurantService.getRestaurantByManager(username);
	}
}
