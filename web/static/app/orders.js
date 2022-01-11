Vue.component("orders", {
	data: function () {
		return {
				orders: [],
				}
	},
	mounted() {

		 axios.get("/getMyOwnOrders/" + localStorage.getItem('username'))
	        .then(response => {
	            console.log(response.data)
	            this.orders = response.data;               
	        });
	},
	
	template: 
	`
	
    <div class="restaurant-list">
		
		<h2 class="text-center">My Own Orders</h2>
        <div :key="order.ID" v-for="order in orders">
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
	    
		    <div class="restaurant-info">
		        <h3 :key="article" v-for="article in order.articles">{{article}}</h3>
				<p class="restaurant-type">Order ID:{{order.ID}}</p>
		    </div>
		    <div class="restaurant-details">
		        <div class="restaurant-location">
		            <p>📍:Restaurant: {{order.restaurant}}</p>
		        </div>
		                    <div class="restaurant-status">
		           		    <p>Status: {{order.orderStatus}}</p>
		        </div>
		        <div class="grade">
		            <i class="fas fa-star" style="color: #FAE480"></i>
		            <label>Price: {{order.price}} DIN</label>
		        </div>
		    </div>
		    </div>
    </div>
	</div>
	`,
	computed : {
		
    },
	methods: {
		goToRestaurant: function(restaurant){
		},

	},
});