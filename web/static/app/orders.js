Vue.component("orders", {
	data: function () {
		return {
				role: "",
				orders: [],
			    restaurants: [],
				editedOrder: {},
			    orderFiltrateSortSearchDTO : {},
			    searchByrestaurantName : "",
			    searchBypriceFrom : 0.0,
			    searchBypriceTo : 0.0,
			    searchBydateFrom : null,
			    searchBydateTo : null,
			    sortByRestaurantName : null,
			    sortByPrice : null,
			    sortByDate : null,
			    filtrateByRestaurantType : null,
			    filtrateByOrderStatus : null,
				searchQuery: null,
				commentText: null,
				grade: null
				}
	},
	mounted() {
        this.role = window.localStorage.getItem('role');

		axios.get("/getOpenedRestaurants")
			.then(response => {
				console.log(response.data)
				this.restaurants = response.data;
			});


		if(this.role == "CUSTOMER"){
			 axios.get("/getMyOwnOrders/" + localStorage.getItem('username'))
		        .then(response => {
		            console.log(response.data)
		            this.orders = response.data;               
		        });
		}else if(this.role == "SUPPLIER"){
			 axios.get("/getOrdersBySupplier/" + localStorage.getItem('username'))
		        .then(response => {
		            console.log(response.data)
		            this.orders = response.data;               
		        });
			
			 axios.get("/getOrderWithStatusWaitingForSupplier")

		        .then(response => {
		            console.log(response.data)
		            this.orders = response.data;               
		        });

		 }else {
			 axios.get("/getOrdersByManager/" + localStorage.getItem('username'))
		        .then(response => {
		            console.log(response.data)
		            this.orders = response.data;               
		        });
		 }
		 
		 
	},
	
	template: 
	`
	
    <div class="restaurant-list">

		<h2 class="text-center">My Own Orders</h2>

		   
    	<div class="input-group mb-3"  style = "width: 56%; margin-left: 22%;">
		  <input type="text" class="form-control" placeholder="Restaurant name" aria-label="" aria-describedby="basic-addon2" v-model="searchByrestaurantName" >
		  <input type="text" class="form-control" placeholder="Price From" aria-label="" aria-describedby="basic-addon2" v-model="searchBypriceFrom" >
		  <input type="text" class="form-control" placeholder="Price To" aria-label="" aria-describedby="basic-addon2" v-model="searchBypriceTo" >
		  <input type="text" class="form-control" placeholder="Date From" aria-label="" aria-describedby="basic-addon2" v-model="searchBydateFrom" >
		  <input type="text" class="form-control" placeholder="Date To" aria-label="" aria-describedby="basic-addon2" v-model="searchBydateTo" >
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" @click= "search">Search</button>
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
				  <select class="custom-select"  @change="search" v-model = "filtrateByOrderStatus">
				    <option value="" disabled selected>Filtrate by status...</option>
				    <option value="PROCESSING"> Processing</option>
				    <option value="IN_PREPARATION"> In preparation</option>
				    <option value="WAITING_FOR_SUPPLIER"> Waiting for deliverer</option>
				    <option value="IN_TRANSPORT"> In transport</option>
				    <option value="DELIVERED"> Delivered</option>
				    <option value="CANCELED"> Canceled</option>
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
				  <select class="custom-select"  @change="search" v-model = "sortByPrice">
				    <option value="" disabled selected>Sort by location...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
				   <div class="col-sm">
				  <select class="custom-select"  @change="search" v-model = "sortByDate">
				    <option value="" disabled selected>Sort by average grade...</option>
				    <option value="ascending"> Ascending</option>
				    <option value="descending"> Descending</option>
				  </select>
				  </div>
                </div>
			</div>
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
				<button class="btn btn-danger" @click="cancelOrder(order)" v-if="accessControlCustomer"
						>Cancel<b></b></button
					>
				<button class="btn btn-success" v-if="accessControlManager" data-toggle="modal" data-target="#editModal"
						>Edit<b></b></button
					>
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
		
		<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
		  <div class="modal-content">
			<div class="modal-header">
			  <h5 class="modal-title" id="exampleModalLongTitle">Change order status</h5>
			  <button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				<i class="fas fa-times"></i>
			  </button>
			</div>
			<div class="modal-body">
				<form name='new-item-form'>
			  <div class="form-group row mb-2">
				  <label for="staticEmail" class="col-sm-2 col-form-label">Order Status</label>
				  <div class="col-sm-10">
								<select id="form3Example1q"  class="form-control"  v-model="order.orderStatus">
									<option value="WAITING_FOR_SUPPLIER" v-if="accessControlManagerAndDeliverer">Waiting For Supplier</option>
									<option value="IN_PREPARATION" v-if="accessControlManager">In Preparation</option>
									<option value="IN_TRANSPORT" v-if="accessControlDeliverer">In Transport</option>
									<option value="DELIVERED" v-if="accessControlDeliverer">Delivered</option>
								</select>
					  </div>			  
				</div>
				
			<div class="modal-footer">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			  <button type="button" class="btn btn-primary"  data-dismiss="modal" @click=setEditableOrder(order)>Save changes</button>
			</div>
		  </form>
		  </div>
		</div>
		</div>	
		</div>
    </div>   


		<!--ADD COMMENT -->		
			<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalLongTitle">Add comment</h5>
        	<button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				<i class="fas fa-times"></i>
		  	</button>
		</div>
	<div class="modal-body">
		  <form name='new-item-form'>
		  
		<div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Your comment:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Name"  ref="name" v-model="commentText">
			</div>
		  </div>
		  <div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Name"  ref="name" v-model="grade">
			</div>
		  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary"  data-dismiss="modal" >Save changes</button>
      </div>
	</form>
    </div>	
		
		</div>
  </div>
  </div>


	
	</div>
	`,
	computed : {
		accessControlCustomer(){
			let role = localStorage.getItem('role')
			if(role == 'CUSTOMER'){
				return true;
			}else
				return false;
		},
		accessControlManager(){
			let role = localStorage.getItem('role')
			if(role == 'MENAGER'){
				return true;
			}else
				return false;
		},
		accessControlDeliverer(){
			let role = localStorage.getItem('role')
			if(role == 'SUPPLIER'){
				return true;
			}else
				return false;
		},
		accessControlManagerAndDeliverer(){
			let role = localStorage.getItem('role')
			if(role == 'SUPPLIER' || role == 'MENAGER'){
				return true;
			}else
				return false;
		}
    },
	methods: {
		search(){
			let role = localStorage.getItem('role')

			this.orderFiltrateSortSearchDTO = {
				user : localStorage.getItem("username"),
				searchByrestaurantName : this.searchByrestaurantName,
				searchBypriceFrom : this.searchBypriceFrom,
				searchBypriceTo : this.searchBypriceTo,
				searchBydateFrom : this.searchBydateFrom,
				searchBydateTo : this.searchBydateTo,
				sortByRestaurantName : this.sortByRestaurantName,
				sortByPrice : this.sortByPrice,
				sortByDate : this.sortByDate,
				filtrateByRestaurantType : this.filtrateByRestaurantType,
				filtrateByOrderStatus : this.filtrateByOrderStatus
			}

			if(role == 'CUSTOMER' || role == 'SUPPLIER') {
				if (this.searchBypriceFrom == "" || this.searchBypriceTo == "") {
					this.searchBypriceFrom = 0.0;
					this.searchBypriceTo = 0.0;

					axios.post('/searchFiltrateSortOrders', this.orderFiltrateSortSearchDTO)
						.then(response => {
							if (response.data.length !== 0) {
								this.orders = response.data
							} else {
								alert("No results!")
								location.reload()
							}
						});
				}
			}
		},
		searchByPrice(){
			
		},
		searchByDate(){
			
		},
		changeOrderStatus(){
			let role = localStorage.getItem('role')
			if(role == 'MENAGER') {
				axios.post('/changeStatusToWaitingForSupplier/' + this.editedOrder.ID, {})
					.then(response => {
						if (response.data) {
							alert("You have successfully change order status!")
							location.reload()
						} else
							alert("That article already exists!")
					});
			}else if(role == 'SUPPLIER'){
				axios.post('/changeStatusToDelivered/' + this.editedOrder.ID, {})
					.then(response => {
						if (response.data) {
							alert("You have successfully change order status!")
							location.reload()
						} else
							alert("That article already exists!")
					});
			}
		},
		setEditableOrder(order) {
			this.editedOrder = order;
			this.changeOrderStatus();
		},
		cancelOrder(order) {
			axios.post('/cancelOrder/'+ localStorage.getItem("username") + "/" + order.ID)
				.then(response => {
					if(response.data){
						alert("You have successfully cancel order!")
						location.reload()
					}else
						alert("That article already exists!")
				});
		}


	},
});