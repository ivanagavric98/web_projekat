package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dao.AddressDAO;
import dao.LocationDAO;
import dao.RestaurantDAO;
import model.Address;
import model.Location;
import model.Menager;
import model.Restaurant;
import model.RestaurantStatus;
import service.Base64ToImage;

public class RestaurantService {
    private RestaurantDAO restaurantDAO;
    private LocationDAO locationDAO;
    private AddressDAO addressDAO;
    private LocationService locationService;
    private AddressService addressService;
    private MenagerService menagerService;
	private Base64ToImage decoder = new Base64ToImage();

	
	public RestaurantService(RestaurantDAO restaurantDAO) {
		this.restaurantDAO=restaurantDAO;
	}

	public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant>restaurants=getAllRestaurants();
		Boolean result=false;
       
		String image = new String();
		String path = "images/restaurants/" + restaurant.getName() + ".jpg";
		decoder.Base64DecodeAndSave(restaurant.getLogo(), path);
		path = "./" + "images/restaurants/" + restaurant.getName() + ".jpg";
		restaurant.setLogo(path);
		
        if(restaurants == null){
            restaurantDAO.create(restaurant);
            result=true;
        }else{
            for(Restaurant u : restaurants){
                if(u.name.equals(restaurant.name)){
                   return result= false;
                }
            }
            restaurantDAO.create(restaurant);
            result=true;

        }
		return result;
	}
	
	public ArrayList<Restaurant> getAllRestaurants() throws JsonSyntaxException, IOException{
		return restaurantDAO.getAll();
	}

    public ArrayList<Restaurant> restourantSearchByName(String restaurantName) throws JsonSyntaxException, IOException {
        return restaurantDAO.restourantSearchByName(restaurantName);
    }

    public ArrayList<Restaurant> restourantSearchByType(String type) throws JsonSyntaxException, IOException {
        return restaurantDAO.restourantSearchByType(type);
    }

    public ArrayList<Restaurant> restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
        return restaurantDAO.restourantSearchByLocation(location);
    }

    public List<Restaurant> restaurantSortByNameAsc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByNameAsc();
    }

    public List<Restaurant> restaurantSortByNameDesc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByNameDesc();
    }

    public List<Restaurant> restaurantSortByLocationAsc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByLocationAsc();
    }

    public List<Restaurant> restauranSortByGradeAsc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restauranSortByGradeAsc();
    }

    public List<Restaurant> restauranSortByGradeDesc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restauranSortByGradeDesc();
    }

    public List<Restaurant> restaurantSortByLocationDesc() throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByLocationDesc();
    }

    public List<Restaurant> restaurantsFiltrateByType(String type) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantsFiltrateByType(type);
    }

    public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantsFiltrateByStatus(status);
    }
/*
    public List<Restaurant> combineSearchRestaurant(String type, String status) throws JsonSyntaxException, IOException {
        return restaurantDAO.combineSearchRestaurant(type,status);
    }
*/
    public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
        return restaurantDAO.getRestaurantsOpenAndClosed();
    }

	public Restaurant getRestaurantByName(String name) throws JsonSyntaxException, IOException {
		return restaurantDAO.getByID(name);
	}

	public ArrayList<Restaurant> getOpenedRestaurants() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants = restaurantDAO.getAll();
		ArrayList<Restaurant> openedRestaurants = new ArrayList<Restaurant>();
		
		for(Restaurant r: allRestaurants) {
			if(r.getStatus().equals(RestaurantStatus.OPEN)) {
				 openedRestaurants.add(r);
			}
		}
		return openedRestaurants;
	}

	public ArrayList<Restaurant> restaurantSearchByGrade(double grade) throws JsonSyntaxException, IOException {
		return restaurantDAO.restaurantSearchByGrade(grade);
	}

}
