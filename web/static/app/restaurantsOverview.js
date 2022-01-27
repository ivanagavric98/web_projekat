Vue.component("restaurants", {
	data: function () {
		return {
				restaurants: [],
				searchQuery: null,
				filterParam: null,
                restaurantSearchSortFiltrateDTO : {},
                searchByrestaurantName: "",
                searchByLocation: "",
                searchByRestaurantType: "",
                searchByAverageGrade: 0.0,
                sortByRestaurantName: "",
                sortByLocation: "",
                sortByAverageGrade: "",
                filtrateByRestaurantType: "",
                filtrateByRestaurantStatusOpen: ""
				}
	},
	mounted() {
		axios.get("getRestaurantsOpenAndClosed")
           .then(response => {
               console.log(response.data)
               this.restaurants = response.data;               
           });
    },
	
	template: 
	`
	
    <div class="restaurant-list">
    
    	<div class="input-group mb-3" style = "width: 56%; margin-left: 22%;">
		  <input type="text" class="form-control" placeholder="Restaurant name" aria-label="" aria-describedby="basic-addon2" v-model="searchByrestaurantName">
		  <input type="text" class="form-control" placeholder="Location" aria-label="" aria-describedby="basic-addon2" v-model="searchByLocation">
		  <input type="text" class="form-control" placeholder="Type" aria-label="" aria-describedby="basic-addon2" v-model="searchByRestaurantType">
		  <input type="text" class="form-control" placeholder="Grade" aria-label="" aria-describedby="basic-addon2" v-model="searchByAverageGrade">
		  <div class="input-group-append">
		    <button class="btn btn-success" @click="search" type="button" >Search</button>
		  </div>
		</div>


		 <div class="row mb-5" style = "width: 56%; margin-left: 22%;">
				  <div class="col-3">
				  
				  <h6>Restaurant type: </h6>
				  <select class="custom-select" @change="search" v-model = "filtrateByRestaurantType">
				    <option value="" disabled selected>Select restaurant type...</option>
				    <option value="International"> International</option>
				    <option value="Fast Food"> Fast food</option>
				    <option value="Traditional Food"> Traditional food</option>
				    <option value="Chinese Food"> Chinese food</option>
				  </select>
				  <h6> Restaurant by status: </h6>
				  <select class="custom-select" @change="search" v-model = "filtrateByRestaurantStatusOpen">
				    <option value="" disabled selected>Select restaurant status...</option>
				    <option value="OPEN"> Open</option>
				  </select>
				  <h6> Sort by name: </h6>
				  <select class="custom-select" @change="search" v-model = "sortByRestaurantName">
				    <option value="" disabled selected>Select restaurant status...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
		</div>
		  
	
    
        <div  :key="restaurant.name" v-for="restaurant in restaurants" >
		    <div  @click= "goToRestaurant(restaurant)">
		    <div class="container" name="rest" style= "margin-top:10px;
			    color: #42405F;
			    display: flex;
			    flex-direction: row;
			    background-color: white;
			    padding: 17px;
			    border-radius: 20px;
			    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
			    cursor: pointer;
			    min-width: 800px;
			    width: 80%;">
	        
		    <div class="picture" name="rest">
		       <img class="rounded-image" v-bind:src="restaurant.logo">
		    </div>
		    <div class="restaurant-info">
		        <h3>{{restaurant.name}}</h3>
		        <p class="restaurant-type">{{restaurant.type}}</p>
		    </div>
		    <div class="restaurant-details">
		        <div class="restaurant-location">
		            <p>📍:  {{restaurant.location.address.street}} {{restaurant.location.address.number}}, {{restaurant.location.address.city}}</p>
		        </div>
		                    <div class="restaurant-status">
		            {{restaurant.status}}
		        </div>
		        <div class="grade">
		            <i class="fas fa-star" style="color: #FAE480"></i>
		            <label>{{restaurant.grade}}</label>
		        </div>
		    </div>
		    </div>
		    </div>
		    <!-- <div data-toggle="modal" data-target="#exampleModalCenter" >
		        <button class="btn btn-success" 
		        		>Add a comment<b></b></button
					>
					</div> -->
    </div>
	</div>
	`,
	computed : {
    },
	methods: {
		goToRestaurant: function(restaurant){
      	  localStorage.setItem('restaurant', JSON.stringify(restaurant.name));
          this.$router.push("adminRestaurant")
		},
		
		search(){

            this.restaurantSearchSortFiltrateDTO = {
                searchByrestaurantName: this.searchByrestaurantName,
                searchByLocation: this.searchByLocation,
                searchByRestaurantType: this.searchByRestaurantType,
                searchByAverageGrade: this.searchByAverageGrade,
                sortByRestaurantName: this.sortByRestaurantName,
                sortByLocation: this.sortByLocation,
                sortByAverageGrade: this.sortByAverageGrade,
                filtrateByRestaurantType: this.filtrateByRestaurantType,
                filtrateByRestaurantStatusOpen: this.filtrateByRestaurantStatusOpen
            }

			axios.post("/searchFiltreteSortRestaurants", this.restaurantSearchSortFiltrateDTO)
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
		},
		searchRestaurantsByType(){
			axios.get("/restourantSearchByType/" + this.searchQuery)
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
		},
		searchRestaurantsByLocation(){
			axios.get("/restourantSearchByLocation/" + this.searchQuery)
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
		},
		
		searchRestaurantsByGrade(){
			axios.get("/restourantSearchByGrade/" + this.searchQuery)
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
		},
		
		getOpenedRestaurants(){
			axios.get("/getOpenedRestaurants")
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
		},
		
		filtrate(){
            axios.get("/restaurantsFiltrateByType/" + this.filterParam)
            .then(response => {
                console.log(response.data)
                if(response.data.length !== 0) {
                    this.restaurants = response.data
                }
                else
                    alert("No results!")
            })
                
        }
	},
});