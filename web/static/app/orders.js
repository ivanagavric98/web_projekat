Vue.component("orders", {
	data: function () {
		return {
				role: "",
				orders: [],
				editedOrder: {}
				}
	},
	mounted() {
        this.role = window.localStorage.getItem('role');

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
		  <input type="text" class="form-control" placeholder="Search..." aria-label="" aria-describedby="basic-addon2" >
		  <div class="input-group-append">
		    <button class="btn btn-success" v-if= "role === 'CUSTOMER' || role === 'SUPPLIER'" type="button" @click= "searchByRestaurant">Search By Restaurant</button>
		    <button class="btn btn-success"  type="button" @click= "searchByPrice">Search By Price</button>
		    <button class="btn btn-success"  type="button" @click= "searchByDate">Search By Date</button>
		  </div>
		</div>


		 <div class="row mb-5" style = "width: 56%; margin-left: 22%;">
				  <div class="col-3">
				  
				    <h6>Restaurant type: </h6>
				  <select class="custom-select" >
				    <option value="" disabled selected>Select restaurant type...</option>
				    <option value="International"> International</option>
				    <option value="Fast Food"> Fast food</option>
				    <option value="Traditional Food"> Traditional food</option>
				    <option value="Chinese Food"> Chinese food</option>
				  </select></div>
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
	    
		    <div class="restaurant-info" data-toggle="modal" data-target="#editModal">
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
							  	<option value="WAITING_FOR_SUPPLIER" >Waiting For Supplier</option>
							  	<option value="IN_PREPARATION" >In Preparation</option>
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
		
	
	</div>
	`,
	computed : {
		
    },
	methods: {
		searchByRestaurant(){
			
		},
		searchByPrice(){
			
		},
		searchByDate(){
			
		},
		updateOrder(){
            axios.post('/changeStatusToWaitingForSupplier/'+ this.editedOrder.ID, {})
                .then(response => {
                    if(response.data){
                        alert("You have successfully change order status!")
                        location.reload()
                    }else
                        alert("That article already exists!")
                });   
		},
		setEditableOrder(order) {
			this.editedOrder = order;
			this.updateOrder();
		}

	},
});