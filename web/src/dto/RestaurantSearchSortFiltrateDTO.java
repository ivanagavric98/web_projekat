package dto;

public class RestaurantSearchSortFiltrateDTO {
    private String searchByrestaurantName;
    private String searchByRestaurantType;
    private String searchByLocation;
    private Double searchByAverageGrade;  

    private String sortByRestaurantName;
    private String sortByLocation;
    private String sortByAverageGrade;

    private String filtrateByRestaurantType;// stavi sve tipove koje imamo pa odatle da bira
    private String filtrateByRestaurantStatusOpen; //moze da ima samo vrijednost open,u suprotnom je null

    public String getSearchByrestaurantName() {
        return searchByrestaurantName;
    }

    public void setSearchByrestaurantName(String searchByrestaurantName) {
        this.searchByrestaurantName = searchByrestaurantName;
    }

    public String getSearchByRestaurantType() {
        return searchByRestaurantType;
    }

    public void setSearchByRestaurantType(String searchByRestaurantType) {
        this.searchByRestaurantType = searchByRestaurantType;
    }

    public String getSearchByLocation() {
        return searchByLocation;
    }

    public void setSearchByLocation(String searchByLocation) {
        this.searchByLocation = searchByLocation;
    }

    public Double getSearchByAverageGrade() {
        return searchByAverageGrade;
    }

    public void setSearchByAverageGrade(Double searchByAverageGrade) {
        this.searchByAverageGrade = searchByAverageGrade;
    }

    public String getSortByRestaurantName() {
        return sortByRestaurantName;
    }

    public void setSortByRestaurantName(String sortByRestaurantName) {
        this.sortByRestaurantName = sortByRestaurantName;
    }

    public String getSortByLocation() {
        return sortByLocation;
    }

    public void setSortByLocation(String sortByLocation) {
        this.sortByLocation = sortByLocation;
    }

    public String getSortByAverageGrade() {
        return sortByAverageGrade;
    }

    public void setSortByAverageGrade(String sortByAverageGrade) {
        this.sortByAverageGrade = sortByAverageGrade;
    }

    public String getFiltrateByRestaurantType() {
        return filtrateByRestaurantType;
    }

    public void setFiltrateByRestaurantType(String filtrateByRestaurantType) {
        this.filtrateByRestaurantType = filtrateByRestaurantType;
    }

    public String getFiltrateByRestaurantStatusOpen() {
        return filtrateByRestaurantStatusOpen;
    }

    public void setFiltrateByRestaurantStatusOpen(String filtrateByRestaurantStatusOpen) {
        this.filtrateByRestaurantStatusOpen = filtrateByRestaurantStatusOpen;
    }

    public RestaurantSearchSortFiltrateDTO(String searchByrestaurantName, String searchByRestaurantType,
            String searchByLocation, Double searchByAverageGrade, String sortByRestaurantName, String sortByLocation,
            String sortByAverageGrade, String filtrateByRestaurantType, String filtrateByRestaurantStatusOpen) {
        this.searchByrestaurantName = searchByrestaurantName;
        this.searchByRestaurantType = searchByRestaurantType;
        this.searchByLocation = searchByLocation;
        this.searchByAverageGrade = searchByAverageGrade;
        this.sortByRestaurantName = sortByRestaurantName;
        this.sortByLocation = sortByLocation;
        this.sortByAverageGrade = sortByAverageGrade;
        this.filtrateByRestaurantType = filtrateByRestaurantType;
        this.filtrateByRestaurantStatusOpen = filtrateByRestaurantStatusOpen;
    }

}
