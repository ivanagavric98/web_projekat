package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Menager;
import model.Restaurant;
import service.AddressService;
import service.LocationService;
import service.MenagerService;
import service.RestaurantService;

public class RestaurantController {
    private RestaurantService restourantService;
    private MenagerService menagerService;
    private AddressService addressService;
    private LocationService locationService;
	private static Gson gson = new Gson();

	public RestaurantController(RestaurantService restourantService) {
		this.restourantService = restourantService;
	}

		public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException{
            Boolean restourantSave=restourantService.register(restaurant);  
			return restourantSave;
	   }

        public ArrayList<Restaurant> restourantSearchByName(String restourantName) throws JsonSyntaxException, IOException {
            return restourantService.restourantSearchByName(restourantName);
        }

        public  ArrayList<Restaurant>  restourantSearchByType(String type) throws JsonSyntaxException, IOException {
			return restourantService.restourantSearchByType(type);   
	    }

        public  ArrayList<Restaurant>  restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
            return restourantService.restourantSearchByLocation(location);
        }

		public  List<Restaurant> restaurantSortByNameAsc() throws JsonSyntaxException, IOException {
            return restourantService.restaurantSortByNameAsc();
		}

        public  List<Restaurant> restaurantSortByNameDesc() throws JsonSyntaxException, IOException {
            return restourantService.restaurantSortByNameDesc();
        }

        public  List<Restaurant> restaurantSortByLocationAsc() throws JsonSyntaxException, IOException {
            return restourantService.restaurantSortByLocationAsc();
        }

        public  List<Restaurant> restaurantSortByLocationDesc() throws JsonSyntaxException, IOException {
            return restourantService.restaurantSortByLocationDesc();
        }

        public  List<Restaurant> restauranSortByGradeAsc() throws JsonSyntaxException, IOException {
            return restourantService.restauranSortByGradeAsc();
        }

        public  List<Restaurant> restauranSortByGradeDesc() throws JsonSyntaxException, IOException {
            return restourantService.restauranSortByGradeDesc();
        }

        public List<Restaurant> restaurantsFiltrateByType(String type) throws JsonSyntaxException, IOException {
            return restourantService.restaurantsFiltrateByType(type);
        }

        public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
            return restourantService.restaurantsFiltrateByStatus(status);
        }

        public List<Restaurant> combineSearchRestaurant(String type, String status) throws JsonSyntaxException, IOException {
            return restourantService.combineSearchRestaurant(type,status);
        }

        public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
            return restourantService.getRestaurantsOpenAndClosed();
        }
	
}
