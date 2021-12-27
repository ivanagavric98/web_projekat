Vue.component("restaurants", {
	data: function () {
		return {
				restaurants: []
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
        <div  :key="restaurant.name" v-for="restaurant in restaurants">
    <div class="container" >
    <div class="picture">
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
	</div>
	`,
	computed : {
		
    },
	methods: {
		/*
		goToRestaurant(){
			
		}*/
	},
});