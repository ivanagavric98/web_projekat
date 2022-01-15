package dto;

public class OrderFiltrateSortSearchDTO {
    // za svaku stavi da default bude null ako se ne salje pramametar
    private String searchByrestaurantName; // ili se salje parametar ili je null,
    // namjesti da mora upisati obje vrijednosti
    private Double searchBypriceFrom;
    private Double searchBypriceTo;
    // namjesti da mora upisati obje vrijednosti
    private String searchBydateFrom;
    private String searchBydateTo;
    private String sortByRestaurantName; // dozvoliti jedino vrijednost ascending ili descending
    private String sortByPrice; // dozvoliti jedino vrijednost ascending ili descending
    private String sortByDate; // dozvoliti jedino vrijednost ascending ili descending
    // dozvoliti da se sortira samo po jednom parametru pa nek bira po kom,znaci
    // jedan ako je popunje ostala dva su null!

    private String filtrateByRestaurantType; // staviti listu da bira, sve tipove koje mi imamo u bazi
    private String filtrateByOrderStatus; // staviti listu da bira,sve tipove koje mi imamo u bazi

    public String getSearchByrestaurantName() {
        return searchByrestaurantName;
    }

    public void setSearchByrestaurantName(String searchByrestaurantName) {
        this.searchByrestaurantName = searchByrestaurantName;
    }

    public Double getSearchBypriceFrom() {
        return searchBypriceFrom;
    }

    public void setSearchBypriceFrom(Double searchBypriceFrom) {
        this.searchBypriceFrom = searchBypriceFrom;
    }

    public Double getSearchBypriceTo() {
        return searchBypriceTo;
    }

    public void setSearchBypriceTo(Double searchBypriceTo) {
        this.searchBypriceTo = searchBypriceTo;
    }

    public String getSearchBydateFrom() {
        return searchBydateFrom;
    }

    public void setSearchBydateFrom(String searchBydateFrom) {
        this.searchBydateFrom = searchBydateFrom;
    }

    public String getSearchBydateTo() {
        return searchBydateTo;
    }

    public void setSearchBydateTo(String searchBydateTo) {
        this.searchBydateTo = searchBydateTo;
    }

    public String getSortByRestaurantName() {
        return sortByRestaurantName;
    }

    public void setSortByRestaurantName(String sortByRestaurantName) {
        this.sortByRestaurantName = sortByRestaurantName;
    }

    public String getSortByPrice() {
        return sortByPrice;
    }

    public void setSortByPrice(String sortByPrice) {
        this.sortByPrice = sortByPrice;
    }

    public String getSortByDate() {
        return sortByDate;
    }

    public void setSortByDate(String sortAscByDate) {
        this.sortByDate = sortAscByDate;
    }

    public String getFiltrateByRestaurantType() {
        return filtrateByRestaurantType;
    }

    public void setFiltrateByRestaurantType(String filtrateByRestaurantType) {
        this.filtrateByRestaurantType = filtrateByRestaurantType;
    }

    public String getFiltrateByOrderStatus() {
        return filtrateByOrderStatus;
    }

    public void setFiltrateByOrderStatus(String filtrateByOrderStatus) {
        this.filtrateByOrderStatus = filtrateByOrderStatus;
    }

    public OrderFiltrateSortSearchDTO(String searchByrestaurantName, Double searchBypriceFrom, Double searchBypriceTo,
            String searchBydateFrom, String searchBydateTo, String sortByRestaurantName, String sortByPrice,
            String sortByDate, String filtrateByRestaurantType, String filtrateByOrderStatus) {
        this.searchByrestaurantName = searchByrestaurantName;
        this.searchBypriceFrom = searchBypriceFrom;
        this.searchBypriceTo = searchBypriceTo;
        this.searchBydateFrom = searchBydateFrom;
        this.searchBydateTo = searchBydateTo;
        this.sortByRestaurantName = sortByRestaurantName;
        this.sortByPrice = sortByPrice;
        this.sortByDate = sortByDate;
        this.filtrateByRestaurantType = filtrateByRestaurantType;
        this.filtrateByOrderStatus = filtrateByOrderStatus;
    }

    public OrderFiltrateSortSearchDTO() {
    }

}
