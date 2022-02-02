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
                filtrateByRestaurantStatusOpen: "",
                comment : {},
                customer: "",
                restaurant: "",
                text: "",
                grade: 0,
                restName: null,
                commentLabel: null,
                averageGrade: 0.0,
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
		  <input type="text" class="form-control" placeholder="Restaurant name" aria-label="" aria-describedby="basic-addon2" v-model="searchByrestaurantName" v-if="accessControlCustomerAndDeliverer">
		  <input type="text" class="form-control" placeholder="Location" aria-label="" aria-describedby="basic-addon2" v-model="searchByLocation">
		  <input type="text" class="form-control" placeholder="Type" aria-label="" aria-describedby="basic-addon2" v-model="searchByRestaurantType">
		  <input type="text" class="form-control" placeholder="Grade" aria-label="" aria-describedby="basic-addon2" v-model="searchByAverageGrade">
		  <div class="input-group-append">
		    <button class="btn btn-success" @click="search" type="button" >Search</button>
		  </div>
		</div>

		 <div class="row mb-5" style = "width: 56%; margin-left: 22%;">
               <div class="container">
			    <div class="row">
			      <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "filtrateByRestaurantType">
				    <option value="" disabled selected>Filtrate by name..</option>
				    <option value="International"> International</option>
				    <option value="Fast Food"> Fast food</option>
				    <option value="Traditional Food"> Traditional food</option>
				    <option value="Chinese Food"> Chinese food</option>
				  </select>
				  </div>
				  <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "filtrateByRestaurantStatusOpen">
				    <option value="" disabled selected>Filtrate opened...</option>
				    <option value="OPEN"> Open</option>
				  </select>
				  </div>
				  <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "sortByRestaurantName">
				    <option value="" disabled selected>Sort by name...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
				   <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "sortByLocation">
				    <option value="" disabled selected>Sort by location...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
				   <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "sortByAverageGrade">
				    <option value="" disabled selected>Sort by average grade...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
                </div>
			</div>
		</div>
    
        <div  :key="restaurant.name" v-for="restaurant in restaurants" >
		    <div>
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
		        <button type="button" class="btn btn-success" @click= "goToRestaurant(restaurant)">Go to {{restaurant.name}}</button>
		        <button type="button" class="btn btn-danger" v-if= "accessControlAdmin" @click="isDeleted(restaurant)">Delete</button>
		        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addComment" @click="setTemporaryRestaurant(restaurant)" v-if="accessControlCustomer">Add comment</button>
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
		            <label>{{restaurant.averageGrade}}</label>
		        </div>
		    </div>
		    </div>
	<!-- ADD COMMENT -->

	<div class="modal fade" id="addComment" tabindex="-1" role="dialog" aria-labelledby="addComment" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
		  <div class="modal-content">
			<div class="modal-header">
			  <h5 class="modal-title" id="exampleModalLongTitle">Add comment</h5>
			  <button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
                x
			  </button>
			</div>
			<div class="modal-body">
				<form name='new-item-form'>
			  <div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Comment:</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Comment"  ref="name" v-model="text">
			  </div>
			</div>
			<div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Grade:</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Grade"  ref="price" v-model="grade">
				   <small id="emailHelp" class="form-text text-muted">From 1 to 5.</small>
			  </div>
			</div>
				   <small id="emailHelp" class="form-text text-muted">{{commentLabel}}</small>	
			<div class="modal-footer">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			  <button type="button" class="btn btn-primary"  data-dismiss="modal" @click= "addComment" >Save changes</button>
			</div>
		  </form>
		  </div>
		</div>
		</div>
		</div>
   </div>
		   
		   
    </div>
	</div>
	`,
	computed : {
        accessControlCustomer(){
            let role = localStorage.getItem("role");
            if(role == 'CUSTOMER'){
                return true;
            }else
                return false;
        },
        accessControlCustomerAndDeliverer(){
            let role = localStorage.getItem("role");
            if(role == 'CUSTOMER' || role == 'DELIVERER'){
                return true;
            }else
                return false;
        },

        accessControlAdmin(){
            let role = localStorage.getItem('role')
            if(role == 'ADMIN'){
                return true;
            }else
                return false;
        }
    },
	methods: {
        setTemporaryRestaurant(restaurant) {
            this.restName = restaurant.name;
        } ,
        goToRestaurant: function (restaurant) {
            localStorage.setItem('restaurant', JSON.stringify(restaurant.name));
            this.$router.push("adminRestaurant")
        },

        addComment(){
            let comment = {
                customer: localStorage.getItem("username"),
                restaurant: this.restName,
                text: this.text,
                grade: this.grade
            }
            if(comment.grade < 1 || comment.grade > 5){
                alert("Grade should be between 1 and 5.")
            }else{
                axios.post("/addComment/" + localStorage.getItem("username") + "/" + this.restName, comment)
                    .then(response => {
                        console.log(response.data)
                        if (response.data) {
                            alert('You are successfully sent your comment.');
                        } else {
                            alert('You cannot leave comment on this restaurant.');
                        }
                    })
            }
        },

        search() {
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
                    if (response.data.length !== 0) {
                        this.restaurants = response.data
                    } else {
                        alert("No results!")
                        location.reload()
                    }
                })
        },
        isDeleted(restaurant){
            axios.post("/deleteRestaurant", restaurant)
                .then(response => {
                    location.reload()

                })

        }
	},
});