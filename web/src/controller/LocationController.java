package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Location;
import service.LocationService;

public class LocationController {
    private LocationService locationService;
	private static Gson gson = new Gson();

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

		public Boolean register(Location location) throws JsonSyntaxException, IOException{			
			return locationService.register(location);
	   }
	
}
