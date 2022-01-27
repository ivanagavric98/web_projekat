package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import dao.AddressDAO;
import dao.LocationDAO;
import dao.RestaurantDAO;
import dto.RestaurantSearchDTO;
import dto.RestaurantSearchSortFiltrateDTO;
import model.Address;
import model.Article;
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
        this.restaurantDAO = restaurantDAO;
    }

    public Boolean register(Restaurant restaurant) throws JsonSyntaxException, IOException {
        ArrayList<Restaurant> restaurants = getAllRestaurants();
        Boolean result = false;

        String image = new String();
        String path = "images/restaurants/" + restaurant.getName() + ".jpg";
        decoder.Base64DecodeAndSave(restaurant.getLogo(), path);
        path = "./" + "images/restaurants/" + restaurant.getName() + ".jpg";
        restaurant.setLogo(path);

        if (restaurants == null) {
            restaurantDAO.create(restaurant);
            result = true;
        } else {
            for (Restaurant u : restaurants) {
                if (u.name.equals(restaurant.name)) {
                    return result = false;
                }
            }
            restaurantDAO.create(restaurant);
            result = true;

        }
        return result;
    }

    public ArrayList<Restaurant> getAllRestaurants() throws JsonSyntaxException, IOException {
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

    public List<Restaurant> restaurantSortByNameAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByNameAsc(restaurants);
    }

    public List<Restaurant> restaurantSortByNameDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByNameDesc(restaurants);
    }

    public List<Restaurant> restaurantSortByLocationAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByLocationAsc(restaurants);
    }

    public List<Restaurant> restauranSortByGradeAsc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restauranSortByGradeAsc(restaurants);
    }

    public List<Restaurant> restauranSortByGradeDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restauranSortByGradeDesc(restaurants);
    }

    public List<Restaurant> restaurantSortByLocationDesc(List<Restaurant> restaurants) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSortByLocationDesc(restaurants);
    }

    public List<Restaurant> restaurantsFiltrateByType(String type, List<Restaurant> sortedList) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantsFiltrateByType(type,sortedList);
    }

    public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantsFiltrateByStatus(status);
    }

    /*
     * public List<Restaurant> combineSearchRestaurant(String type, String status)
     * throws JsonSyntaxException, IOException {
     * return restaurantDAO.combineSearchRestaurant(type,status);
     * }
     */
    public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
        return restaurantDAO.getRestaurantsOpenAndClosed();
    }

    public Restaurant getRestaurantByName(String name) throws JsonSyntaxException, IOException {
        return restaurantDAO.getByID(name);
    }
    public List<Restaurant> getRestaurantsByName(String name) throws JsonSyntaxException, IOException {
       ArrayList<Restaurant> restaurants=getAllRestaurants();
       List<Restaurant> resultList=new ArrayList<>();
       for(Restaurant res :restaurants){
           if(res.getName().toLowerCase().contains(name.toLowerCase())){
               resultList.add(res);
           }
       }
       return resultList;
    }

    public ArrayList<Restaurant> getOpenedRestaurants(List<Restaurant> allRestaurants) throws JsonSyntaxException, IOException {
        ArrayList<Restaurant> openedRestaurants = new ArrayList<Restaurant>();

        for (Restaurant r : allRestaurants) {
            if (r.getStatus().equals(RestaurantStatus.OPEN)) {
                openedRestaurants.add(r);
            }
        }
        return openedRestaurants;
    }
    public ArrayList<Restaurant> getRestaurantsByType(String type) throws JsonSyntaxException, IOException {
        ArrayList<Restaurant> allRestaurants = restaurantDAO.getAll();
        ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();

        for (Restaurant r : allRestaurants) {
            if (r.getType().toLowerCase().contains(type.toLowerCase())) {
                resultList.add(r);
            }
        }
        return resultList;
    }

    public ArrayList<Restaurant> restaurantSearchByGrade(double grade) throws JsonSyntaxException, IOException {
        return restaurantDAO.restaurantSearchByGrade(grade);
    }

    public Boolean isRestaurantOpen(String params) throws JsonSyntaxException, IOException {
        return restaurantDAO.isRestaurantOpen(params);
    }

    public ArrayList<Article> getArticlesFromRestaurant(String params) throws JsonSyntaxException, IOException {
        return restaurantDAO.getArticlesFromRestaurant(params);
    }

    public void update(Restaurant restaurant) throws JsonSyntaxException, IOException {
        restaurantDAO.update(restaurant);
    }

    public List<Restaurant> searchFiltreteSortRestaurants(
            RestaurantSearchSortFiltrateDTO restaurantSearchSortFiltrateDTO) throws JsonSyntaxException, IOException {
                List<Restaurant> searchByRestaurantName = new ArrayList<Restaurant>();
                List<Restaurant> searchByRestaurantType = new ArrayList<Restaurant>();
                List<Restaurant> searchByLocation = new ArrayList<Restaurant>();
                List<Restaurant> searchByGrade = new ArrayList<Restaurant>();

                if (restaurantSearchSortFiltrateDTO.getSearchByrestaurantName() != "") {
                    searchByRestaurantName = getRestaurantsByName(restaurantSearchSortFiltrateDTO.getSearchByrestaurantName());
                    System.out.println(restaurantSearchSortFiltrateDTO.getSearchByrestaurantName());
                } else {
                    searchByRestaurantName = restaurantDAO.getAll();
                }
        
                if (restaurantSearchSortFiltrateDTO.getSearchByRestaurantType() != "") {
                    searchByRestaurantType = getRestaurantsByType(restaurantSearchSortFiltrateDTO.getSearchByRestaurantType()); 
                    System.out.println(restaurantSearchSortFiltrateDTO.getSearchByRestaurantType());
                } else {
                    searchByRestaurantType =  restaurantDAO.getAll();
                }
        
                if (restaurantSearchSortFiltrateDTO.getSearchByLocation() != "") {
                    searchByLocation = restourantSearchByLocation(restaurantSearchSortFiltrateDTO.getSearchByLocation());
                } else {
                    searchByLocation =  restaurantDAO.getAll();
                }

                if (restaurantSearchSortFiltrateDTO.getSearchByAverageGrade() != 0.0) {
                    searchByGrade = restaurantSearchByGrade(restaurantSearchSortFiltrateDTO.getSearchByAverageGrade());
                } else {
                    searchByGrade =  restaurantDAO.getAll();
                }
        
        
                List<Restaurant> intersectionResult = new ArrayList<Restaurant>();
                List<Restaurant> intersectionResult1 = new ArrayList<Restaurant>();
                List<Restaurant> intersectionResult2 = new ArrayList<Restaurant>();

                for (Restaurant restaurant : searchByRestaurantName) {
                    for (Restaurant restaurant1 : searchByRestaurantType) {
                        if (restaurant.getName().equals(restaurant1.getName())) {
                            intersectionResult.add(restaurant);
                        }
                    }
                }
                System.out.println(intersectionResult.size());
                for (Restaurant restaurant : intersectionResult) {
                    for (Restaurant restaurant2 : searchByLocation) {
                        if (restaurant.getName().equals(restaurant2.getName())) {
                            intersectionResult1.add(restaurant);
                        }
                    }
                }
                System.out.println(intersectionResult1.size());
                for (Restaurant restaurant : intersectionResult1) {
                    for (Restaurant restaurant2 : searchByGrade) {
                        if (restaurant.getName().equals(restaurant2.getName())) {
                            intersectionResult2.add(restaurant);
                        }
                    }
                }
                System.out.println(intersectionResult2.size());

                List<Restaurant> sortedList = new ArrayList<Restaurant>();
                if (restaurantSearchSortFiltrateDTO.getSortByRestaurantName() != null) {
                    if (restaurantSearchSortFiltrateDTO.getSortByRestaurantName().equals("ascending")) {
                        sortedList = restaurantSortByNameAsc(intersectionResult2);
                    } else {
                        sortedList = restaurantSortByNameDesc(intersectionResult2);
                    }
                }
        
                System.out.println(sortedList.size());
                if (restaurantSearchSortFiltrateDTO.getSortByLocation() != null) {
                    if (restaurantSearchSortFiltrateDTO.getSortByLocation().equals("ascending")) {
                        sortedList = restaurantSortByLocationAsc(intersectionResult2);
                    } else {
                        sortedList = restaurantSortByLocationDesc(intersectionResult2);
                    }
                }
                System.out.println(sortedList.size());
                if (restaurantSearchSortFiltrateDTO.getSortByAverageGrade() != null) {
                    if (restaurantSearchSortFiltrateDTO.getSortByAverageGrade().equals("ascending")) {
                        sortedList = restauranSortByGradeAsc(intersectionResult2);
                    } else {
                        sortedList = restauranSortByGradeDesc(intersectionResult2);
                        System.out.println(sortedList.size() + "eh");
                    }
                }
        
                System.out.println(sortedList.size());
                if (restaurantSearchSortFiltrateDTO.getSortByRestaurantName() == null
                        && restaurantSearchSortFiltrateDTO.getSortByAverageGrade() == null
                        && restaurantSearchSortFiltrateDTO.getSortByLocation() == null) {
                    sortedList = intersectionResult2;
                }
        
                System.out.println(sortedList.size());
                List<Restaurant> filtrateByRestaurantType = new ArrayList<Restaurant>();
                if (restaurantSearchSortFiltrateDTO.getFiltrateByRestaurantType() != null) {
                    filtrateByRestaurantType = restaurantsFiltrateByType(restaurantSearchSortFiltrateDTO.getFiltrateByRestaurantType(),
                            sortedList);
                } else {
                    filtrateByRestaurantType = sortedList;
                }
        
                System.out.println("aa" + filtrateByRestaurantType.size());
                List<Restaurant> filtrateByRestoranStatusOpen = new ArrayList<Restaurant>();
                if (restaurantSearchSortFiltrateDTO.getFiltrateByRestaurantStatusOpen() != null && restaurantSearchSortFiltrateDTO.getFiltrateByRestaurantStatusOpen().equals("OPEN")) {
                    filtrateByRestoranStatusOpen = getOpenedRestaurants(sortedList);
                } else {
                    filtrateByRestoranStatusOpen = sortedList;
                }
        
                System.out.println(filtrateByRestoranStatusOpen.size());
                List<Restaurant> result = new ArrayList<Restaurant>();
                for (Restaurant restaurant : filtrateByRestaurantType) {
                    for (Restaurant restaurant1 : filtrateByRestoranStatusOpen) {
                        if (restaurant.getName().equals(restaurant1.getName())) {
                            result.add(restaurant);
                        }
                    }
                }

                List<RestaurantSearchDTO> resultListToReturn=new ArrayList<>();
                for(Restaurant res : result){
                    Double grade=restaurantDAO.getRestauratAverageGrade(res);
                    resultListToReturn.add(new RestaurantSearchDTO(res.getName(), res.getType(), res.getLocation(), res.getLogo(),grade));
                }
                System.out.println(result.size());
                return result;   
             }
}

