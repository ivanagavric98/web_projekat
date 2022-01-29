Vue.component("managerRestaurant", {
    data: function () {
        return {
            restaurant: {},
            orders: [],
            customers: [],
            sort: {
                key: '',
                isAsc: false,
                selectedItem: -1
            }
        }
    },
    mounted() {
        axios.get("/getRestaurantByManager/" + localStorage.getItem("username"))
            .then(response =>{
                this.restaurant = response.data;

                axios.get("/getOrdersByRestaurant/" + this.restaurant.name)
                    .then(response => {
                        this.orders = response.data;
                    });
            });
    },
    template:
        `
    <div class="restaurant-list">
        <div style="background-color: ;">
		    <h2 class="text-center">My Restaurant</h2>
        </div>
        
        <div>
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
                <button class="btn btn-light" @click="customersOverview" data-toggle="modal" data-target="#editModal"
						><b>Customers who made order from {{restaurant.name}}</b></button
					>		    </div>
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
    
    <!-- MODAL SHOW CUSTOMERS-->
    
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
		  <div class="modal-content">
			<div class="modal-header">
			  <h5 class="modal-title" id="exampleModalLongTitle">Customers who made order</h5>
			  <button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				x
			  </button>
			</div>
			<div class="modal-body">
				<form name='new-item-form'>
			  <div class="form-group row mb-2">
				  <div class="col-sm-10">
				  <table class="table sortable table-striped table-hover table-light"
                        id="table"
                     >
                    <thead class="thead-dark">
                      <tr class="clickable-row">
                        <th
                          :class="sortedClass('username')"
                          @click="sortBy('username')"
                        >
                          Username
                        </th>
                        <th
                          :class="sortedClass('name')"
                          @click="sortBy('name')"
                        >
                          Name
                        </th>
                        <th
                          :class="sortedClass('surname')"
                          @click="sortBy('surname')"
                        >
                          Surname
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr
                        v-for="item in sortedItems"
                        :key = "item.username"
                      >
                        <td>{{ item.username }}</td>
                        <td>{{ item.name }}</td>
                        <td>{{ item.surname }}</td>
                      </tr>
                    </tbody>
                  </table>
				  </div>			  
				</div>
			<div class="modal-footer">
			</div>
		  </form>
		  </div>
		</div>
		</div>	
		</div>
    
    
    <div class="items-list" >
        <div>
			<h3>Orders</h3>
		</div>
    
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
	    
		    <div class="restaurant-info" >
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
		        <div class="restaurant-status">
		           		    <p>Date: {{order.dateAndTime}}</p>
		        </div>
		        
		        <div class="grade">
		            <i class="fas fa-star" style="color: #FAE480"></i>
		            <label>Price: {{order.price}} DIN</label>
		        </div>
    	
		    </div>
		    </div>		  
	</div>
	</div>
	</div>
	`,
    computed : {
        sortedItems () {
            const list = this.customers.slice();
            if (!!this.sort.key) {
                list.sort((a, b) => {
                    a = a[this.sort.key]
                    b = b[this.sort.key]

                    return (a === b ? 0 : a > b ? 1 : -1) * (this.sort.isAsc ? 1 : -1)
                });
            }
            return list;
        }
    },
    methods: {
        sortedClass (key) {
            return this.sort.key === key ? `sorted ${this.sort.isAsc ? 'asc' : 'desc' }` : '';
        },
        sortBy (key) {
            this.sort.isAsc = this.sort.key === key ? !this.sort.isAsc : false;
            this.sort.key = key;
        },
        selectRow(item){
            this.selectedItem = item;
        },
        customersOverview() {
            axios.get('/getCustomersWithOrderFromRestaurant/'+ JSON.parse(localStorage.getItem("restaurant")))
                .then(response => {
                    this.customers = response.data;
                });
        }
    },
});