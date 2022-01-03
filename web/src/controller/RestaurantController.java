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
	    private RestaurantService restaurantService;
	    private MenagerService menagerService;
	    private AddressService addressService;
	    private LocationService locationService;
		private static Gson gson = new Gson();
	
		public RestaurantController(RestaurantService restaurantService) {
			this.restaurantService = restaurantService;
		}

		public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException{
            Boolean restourantSave=restaurantService.register(restaurant);  
			return restourantSave;
	   }

        public ArrayList<Restaurant> restourantSearchByName(String restourantName) throws JsonSyntaxException, IOException {
            return restaurantService.restourantSearchByName(restourantName);
        }

        public  ArrayList<Restaurant>  restourantSearchByType(String type) throws JsonSyntaxException, IOException {
			return restaurantService.restourantSearchByType(type);   
	    }

        public  ArrayList<Restaurant>  restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
            return restaurantService.restourantSearchByLocation(location);
        }

		public  List<Restaurant> restaurantSortByNameAsc() throws JsonSyntaxException, IOException {
            return restaurantService.restaurantSortByNameAsc();
		}

        public  List<Restaurant> restaurantSortByNameDesc() throws JsonSyntaxException, IOException {
            return restaurantService.restaurantSortByNameDesc();
        }

        public  List<Restaurant> restaurantSortByLocationAsc() throws JsonSyntaxException, IOException {
            return restaurantService.restaurantSortByLocationAsc();
        }

        public  List<Restaurant> restaurantSortByLocationDesc() throws JsonSyntaxException, IOException {
            return restaurantService.restaurantSortByLocationDesc();
        }

        public  List<Restaurant> restauranSortByGradeAsc() throws JsonSyntaxException, IOException {
            return restaurantService.restauranSortByGradeAsc();
        }

        public  List<Restaurant> restauranSortByGradeDesc() throws JsonSyntaxException, IOException {
            return restaurantService.restauranSortByGradeDesc();
        }

        public List<Restaurant> restaurantsFiltrateByType(String type) throws JsonSyntaxException, IOException {
            return restaurantService.restaurantsFiltrateByType(type);
        }

        public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
            return restaurantService.restaurantsFiltrateByStatus(status);
        }
/*
        public List<Restaurant> combineSearchRestaurant(String type, String status) throws JsonSyntaxException, IOException {
            return restaurantService.combineSearchRestaurant(type,status);
        }
*/
        public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
            return restaurantService.getRestaurantsOpenAndClosed();
        }

		public Restaurant getRestaurantByName(String name) throws JsonSyntaxException, IOException {
			return restaurantService.getRestaurantByName(name);
		}

		public ArrayList<Restaurant> getOpenedRestaurants() throws JsonSyntaxException, IOException {
			return restaurantService.getOpenedRestaurants();
		}

		public ArrayList<Restaurant> restourantSearchByGrade(double grade) throws JsonSyntaxException, IOException {
			return restaurantService.restaurantSearchByGrade(grade);
		}
}
