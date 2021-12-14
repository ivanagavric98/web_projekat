package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.LocationDAO;
import model.Location;

public class LocationService {
    
    private LocationDAO locationDAO;
	
	public LocationService(LocationDAO locationDAO) {
		this.locationDAO=locationDAO;
	}
	
	public Boolean register(Location location) throws JsonSyntaxException, IOException {
		ArrayList<Location>locations=getAllLocations();
		Boolean result=false;
		if(locations.size()==0){
			locationDAO.create(location);
			result=true;
		}else{
			for(Location u : locations){
				if(u.longitude.equals(location.longitude) && u.latitude.equals(location.latitude)){
				result= false;
				}else{
					locationDAO.create(location);
					result=true;
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Location> getAllLocations() throws JsonSyntaxException, IOException{
		return locationDAO.getAll();
	}
}
