package dto;

public class SearchFiltrateSortUsersDTO {
    private String searchByName;
    private String searchBySurname;
    private String searchByUsername;
    private String sortByName;
    private String sortBySurname;
    private String sortByUsername;
    private String sortByPoints;
    private String filtrateByRole;
    private String filtrateByType;
    
    public String getSearchByName() {
        return searchByName;
    }
    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }
    public String getSearchBySurname() {
        return searchBySurname;
    }
    public void setSearchBySurname(String searchBySurname) {
        this.searchBySurname = searchBySurname;
    }
    public String getSearchByUsername() {
        return searchByUsername;
    }
    public void setSearchByUsername(String searchByUsername) {
        this.searchByUsername = searchByUsername;
    }
    public String getSortByName() {
        return sortByName;
    }
    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }
    public String getSortBySurname() {
        return sortBySurname;
    }
    public void setSortBySurname(String sortBySurname) {
        this.sortBySurname = sortBySurname;
    }
    public String getSortByUsername() {
        return sortByUsername;
    }
    public void setSortByUsername(String sortByUsername) {
        this.sortByUsername = sortByUsername;
    }
    public String getSortByPoints() {
        return sortByPoints;
    }
    public void setSortByPoints(String sortByPoints) {
        this.sortByPoints = sortByPoints;
    }
    public String getFiltrateByRole() {
        return filtrateByRole;
    }
    public void setFiltrateByRole(String filtrateByRole) {
        this.filtrateByRole = filtrateByRole;
    }
    public String getFiltrateByType() {
        return filtrateByType;
    }
    public void setFiltrateByType(String filtrateByType) {
        this.filtrateByType = filtrateByType;
    }
    public SearchFiltrateSortUsersDTO(String searchByName, String searchBySurname, String searchByUsername,
            String sortByName, String sortBySurname, String sortByUsername, String sortByPoints, String filtrateByRole,
            String filtrateByType) {
        this.searchByName = searchByName;
        this.searchBySurname = searchBySurname;
        this.searchByUsername = searchByUsername;
        this.sortByName = sortByName;
        this.sortBySurname = sortBySurname;
        this.sortByUsername = sortByUsername;
        this.sortByPoints = sortByPoints;
        this.filtrateByRole = filtrateByRole;
        this.filtrateByType = filtrateByType;
    }

    
}
