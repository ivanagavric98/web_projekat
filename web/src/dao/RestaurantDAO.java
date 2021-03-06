package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Restaurant;

public class RestaurantDAO  implements IDAO<Restaurant, String>{

	private String path;
	
	public RestaurantDAO(String path) {
		super();
		this.path = path;
	}

	@Override
	public ArrayList<Restaurant> getAll() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = new Gson().fromJson((Files.readAllLines(Paths.get(path), 
				Charset.defaultCharset()).size() == 0) ? "" : 
					Files.readAllLines(Paths.get(path),
							Charset.defaultCharset()).get(0), 
					new TypeToken<List<Restaurant>>(){}.getType());
		
		if(restaurants == null)
        restaurants = new ArrayList<Restaurant>();
			
		return restaurants;
	}

	@Override
	public Restaurant getByID(String id) throws JsonSyntaxException, IOException {
		Restaurant wantedRestaurant = null;
		ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) getAll();
		if(restaurants.size()!=0)
		{
			for(Restaurant restaurant : restaurants) {
				if(restaurant.getName().equals(id)) {
					wantedRestaurant = restaurant;
					break;
				}
			}
		}
		return wantedRestaurant;
	}

	@Override
	public void create(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants= getAll();
		restaurants.add(entity);
		saveAll(restaurants);	
	}

	@Override
	public void update(Restaurant entity) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants = getAll();
		for(Restaurant res : restaurants) {
			if(res.getName().equals(entity.getName())) {
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
		String allEntities = new Gson().toJson(entities, new TypeToken<List<Restaurant>>(){}.getType());
		writer.println(allEntities);
		writer.close();		
	}

	@Override
	public ArrayList<Restaurant> getAllNonDeleted() throws JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

    public ArrayList<Restaurant> restourantSearchByName(String restaurantName) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants=getAll();
		ArrayList<Restaurant> nameSearchList=new ArrayList<>();

		if(allRestaurants.size()!=0){
			for (Restaurant restaurant : allRestaurants) {
				if(restaurant.name.toLowerCase().equals(restaurantName.toLowerCase())){
					nameSearchList.add(restaurant);
				}
			}
		}
		return nameSearchList;
    }

    public ArrayList<Restaurant> restourantSearchByType(String type) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants=getAll();
		ArrayList<Restaurant> typeSearchList=new ArrayList<>();

		if(allRestaurants.size()!=0){
			for (Restaurant restaurant : allRestaurants) {
				if(restaurant.type.toLowerCase().equals(type.toLowerCase())){
					typeSearchList.add(restaurant);
				}
			}
		}
		return typeSearchList;
    }

    public ArrayList<Restaurant> restourantSearchByLocation(String location) throws JsonSyntaxException, IOException {
        ArrayList<Restaurant> allRestaurants=getAll();
		ArrayList<Restaurant> locationSearchList=new ArrayList<>();

		if(allRestaurants.size()!=0){
			for (Restaurant restaurant : allRestaurants) {
				if(restaurant.location.getAddress().getCity().toLowerCase().equals(location.toLowerCase()) || 
				restaurant.location.getAddress().getCountry().toLowerCase().equals(location.toLowerCase())){
					locationSearchList.add(restaurant);
				}
			}
		}
		return locationSearchList;
    }

    public List<Restaurant> restaurantSortByNameAsc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
		
		return resultList;
    }

    public List<Restaurant> restaurantSortByNameDesc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;   
	 }

    public List<Restaurant> restaurantSortByLocationAsc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getLocation().getAddress().getCity().compareTo(e2.getLocation().getAddress().getCity())).collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;  
	   }

    public List<Restaurant> restauranSortByGradeAsc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		e1.getLocation().getAddress().getCity().compareTo(e2.getLocation().getAddress().getCity())).collect(Collectors.toList());
		Collections.reverse(resultList);
		Collections.reverse(resultList);
		return resultList;  
	   }

    public List<Restaurant> restauranSortByGradeDesc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		Double.valueOf(e1.getGrade()).compareTo(Double.valueOf(e2.getGrade()))).collect(Collectors.toList());
		
		return resultList;
    }

    public List<Restaurant> restaurantSortByLocationDesc() throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		Set<Restaurant> toSort=new HashSet<>();

		for (Restaurant object : restaurants) {
			toSort.add(object);
		}

		List<Restaurant> resultList = toSort.stream().sorted((e1, e2) -> 
		Double.valueOf(e1.getGrade()).compareTo(Double.valueOf(e2.getGrade()))).collect(Collectors.toList());
		Collections.reverse(resultList);
		return resultList;  
	  }

	public List<Restaurant> restaurantsFiltrateByType(String type) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> restaurants=getAll();
		ArrayList<Restaurant> resultList=new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getType().toLowerCase().equals(type.toLowerCase())){
                resultList.add(restaurant);
            }
        }
        return resultList;
	}

    public List<Restaurant> restaurantsFiltrateByStatus(String status) throws JsonSyntaxException, IOException {
        ArrayList<Restaurant> restaurants=getAll();
		ArrayList<Restaurant> resultList=new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getStatus().toString().toLowerCase().equals(status.toLowerCase())){
                resultList.add(restaurant);
            }
        }
        return resultList;
    }

    public List<Restaurant> combineSearchRestaurant(String type, String status) throws JsonSyntaxException, IOException {
		ArrayList<Restaurant> allRestaurants=getAll();
		List<Restaurant> typeList=new ArrayList<Restaurant>();
		List<Restaurant> statusList=new ArrayList<Restaurant>();

		if(type==null || type.isBlank())
			typeList=allRestaurants;
		else	
			typeList=restaurantsFiltrateByType(type);

		if(status==null || status.isBlank())
			statusList=allRestaurants;
		else	
		statusList=restaurantsFiltrateByStatus(status);

			List<Restaurant> intersectionResult=new ArrayList<Restaurant>();

			for(Restaurant restaurant :typeList){
				for(Restaurant restaurant2: statusList){
					if(restaurant.getName().equals(restaurant2.getName())  ){
						intersectionResult.add(restaurant);
					}
				}
			}
			
		return  intersectionResult;
	}

    public List<Restaurant> getRestaurantsOpenAndClosed() throws JsonSyntaxException, IOException {
		List<Restaurant> resultList=new ArrayList<>();
		List<Restaurant> openList=restaurantsFiltrateByStatus("OPEN");
		List<Restaurant> closedList=restaurantsFiltrateByStatus("CLOSED");
		resultList=Stream.concat(openList.stream(), closedList.stream())
		.collect(Collectors.toList());
		return resultList;
    }

    public Restaurant getRestaurantByName(String restaurantName) throws JsonSyntaxException, IOException {
        Restaurant result=new Restaurant();
		ArrayList<Restaurant> restaurants=getAll();
		for (Restaurant restaurant : restaurants) {
			if(restaurant.name.trim().toLowerCase().equals(restaurantName.trim().toLowerCase())){
				result = restaurant;
			}
		}
		return result;
    }
	
}
