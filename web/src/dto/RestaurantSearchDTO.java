package dto;

import model.Location;
import model.Type;

public class RestaurantSearchDTO {
    private String restaurantName;
    private String restauranType;
    private Location location;
    private String logo;
    private Double averageGrade;
    
    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public String getRestauranType() {
        return restauranType;
    }
    public void setRestauranType(String restauranType) {
        this.restauranType = restauranType;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public Double getAverageGrade() {
        return averageGrade;
    }
    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }
    public RestaurantSearchDTO(String restaurantName, String restauranType, Location location, String logo,
            Double averageGrade) {
        this.restaurantName = restaurantName;
        this.restauranType = restauranType;
        this.location = location;
        this.logo = logo;
        this.averageGrade = averageGrade;
    }

    

}
