Vue.component("managerRestaurant", {
    data: function () {
        return {
            restaurant: {},
            orders: [],
            customers: [],
            orderFiltrateSortSearchDTO : {},
            searchByrestaurantName : null,
            searchBypriceFrom : 0.0,
            searchBypriceTo : 0.0,
            searchBydateFrom : null,
            searchBydateTo : null,
            sortByRestaurantName : null,
            sortByPrice : null,
            sortByDate : null,
            filtrateByRestaurantType : null,
            filtrateByOrderStatus : null,
            editedOrder: {},
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
        
        
        <div class="input-group mb-3"  style = "width: 56%; margin-left: 22%;">
		  <input type="text" class="form-control" placeholder="Restaurant name" aria-label="" aria-describedby="basic-addon2" v-model="searchByrestaurantName" v-if="accessControlManager">
		  <input type="text" class="form-control" placeholder="Price From" aria-label="" aria-describedby="basic-addon2" v-model="searchBypriceFrom" >
		  <input type="text" class="form-control" placeholder="Price To" aria-label="" aria-describedby="basic-addon2" v-model="searchBypriceTo" >
		  <input type="text" class="form-control" placeholder="Date From" aria-label="" aria-describedby="basic-addon2" v-model="searchBydateFrom" >
		  <input type="text" class="form-control" placeholder="Date To" aria-label="" aria-describedby="basic-addon2" v-model="searchBydateTo" >
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" @click="search">Search</button>
		  </div>
		</div>

	 <div class="row mb-5" style = "width: 56%; margin-left: 22%;">
               <div class="container">
			    <div class="row">
			      
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
				  <select class="custom-select"  @ch
				  ange="search" v-model = "sortByRestaurantName">
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
                <button class="btn btn-light" @click="customersOverview(restaurant)" data-toggle="modal" data-target="#editModal"
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
				<button class="btn btn-success" v-if="!accessControlManager" v-on:click="changeOrderStatus(order)" data-toggle="modal" data-target="#editOrderModal"
						>Change order status<b></b></button>
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
        accessControlManager(){
            let role = localStorage.getItem('role')
            if(role !== "MENAGER"){
                return true;
            }else
                return false;
        },
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
        sortedClass(key) {
            return this.sort.key === key ? `sorted ${this.sort.isAsc ? 'asc' : 'desc'}` : '';
        },
        sortBy(key) {
            this.sort.isAsc = this.sort.key === key ? !this.sort.isAsc : false;
            this.sort.key = key;
        },
        selectRow(item) {
            this.selectedItem = item;
        },
        customersOverview(restaurant) {
            axios.get('/getCustomersWithOrderFromRestaurant/' + restaurant.name)
                .then(response => {
                    this.customers = response.data;
                });
        },
        search() {
            let role = localStorage.getItem('role')

            this.orderFiltrateSortSearchDTO = {
                user : null,
                searchByrestaurantName : this.restaurant.name,
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

            if (this.searchBypriceFrom === 0 || this.searchBypriceTo === 0) {
                this.searchBypriceFrom = 0.0;
                this.searchBypriceTo = 0.0;
            }

                axios.post('/searchFiltrateSortOrders', this.orderFiltrateSortSearchDTO)
                    .then(response => {
                        if (response.data.length !== 0) {
                            this.orders = response.data
                        } else {
                            alert("No results!")
                            location.reload()
                        }
                    });
            },
        changeOrderStatus(order){
            let role = localStorage.getItem('role');

            if(order.orderStatus == 'IN_PREPARATION') {

                axios.post('/changeStatusToWaitingForSupplier/' + order.ID, {})
                    .then(response => {
                        if (response.data) {
                            alert("You have successfully change order status!")
                            location.reload()
                        } else
                            alert("That article already exists!")
                    });
            }else if(order.orderStatus == 'PROCESSING'){

                axios.post('/changeStatusToInPreparation/' + order.ID, {})
                    .then(response => {
                        if (response.data) {
                            alert("You have successfully change order status!")
                            location.reload()
                        } else
                            alert("That article already exists!")
                    });
            }else {
                alert('You cannot change order status when status is ' + order.orderStatus);
            }

        },
        setEditableOrder(order) {
            this.editedOrder = order;
        },
        }
});