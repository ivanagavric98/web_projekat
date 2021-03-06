Vue.component("adminUsers", {
	data: function () {
		return {
            sort: {
                key: '',
                isAsc: false,
                selectedItem: -1
            },
            searchQuery: null,
            filterParam: null,
            items: [],
            users: [],
            all: []
        }
	},
	mounted() {
		   $('#table').on('click', 'tbody tr', function(event) {
	            $(this).addClass('highlight').siblings().removeClass('highlight');
	        });

		   axios.get("/getAllUsers")
           .then(response => {
               console.log(response.data)
               this.users = response.data;               
           });
		   
	},
	template: 
	`   
		
		<div class="container">
		
		<h2 class= "text-center">USERS OVERVIEW</h2>
		
		<div class="input-group mb-3">
		  <input type="text" class="form-control" placeholder="Search..." aria-label="" aria-describedby="basic-addon2" v-model = "searchQuery">
		  <div class="input-group-append">
		    <button class="btn btn-success" type="button" @click="searchUsersByName">Search By Name</button>
		    <button class="btn btn-success" type="button" @click="searchUsersByUsername">Search By Username</button>
		    <button class="btn btn-success" type="button" @click="searchUsersBySurname">Search By Surname</button>

		  </div>
		    </div>
		    
		     <div class="row mb-5">
				  <div class="col-3">
				  <h6>User role: </h6>
				  <select class="custom-select"  @change="filtrateByRole" v-model = "filterParam">    
				    <option value="" disabled selected>Select user role...</option>
				    <option value="CUSTOMER">Customer</option>
				    <option value="MENAGER">Manager</option>
				    <option value="SUPPLIER">Deliverer</option>
				  </select></div>
				  <div class="col-3">
				    <h6>User type: </h6>
				  <select class="custom-select" @change="filtrateByType" v-model = "filterParam">
				    <option value="" disabled selected>Select user type...</option>
				    <option value="BRONZE">Bronze</option>
				    <option value="SILVER">Silver</option>
				    <option value="GOLD">Gold</option>
				  </select></div>
				</div>
		  
		     <table class="table sortable table-striped table-hover table-dark"
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
		        <th
		          :class="sortedClass('role')"
		          @click="sortBy('role')"
		        >
		          Role
		        </th>
		        <th
		          :class="sortedClass('points')"
		          @click="sortBy('points')"
		        >
		          Points
		        </th>
				<th
		         
		        >
		         Delete
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
		        <td>{{ item.role }}</td>
		        <td>{{ item.points }}</td>
		        <td><button type="button" class="btn btn-danger" @click="isDeleted(item)">Delete</button></td>
		        
		      </tr>
		    </tbody>
		  </table>
		  <div>
		  </div>
		  </div>
`,
	   computed: {   
		   sortedItems () {
            const list = this.users.slice();
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
		getAll(){
			axios.get("/getAllUsers")
	           .then(response => {
	               console.log(response.data)
	               this.users = response.data;               
	           });

		},		
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
	        searchUsersByName() {
	        	if(this.filterParam === null){
	            axios.get("/usersSearchByName/" + this.searchQuery)
	                .then(response => {
	                    console.log(response.data)
	                    if(response.data.length !== 0) {
	                        this.users = response.data
	                    }
	                    else
	                        alert("No results!")
	                })
	        	}else {
	        		axios.get("/usersSearchByName/" + this.searchQuery)
	                .then(response => {
	                    console.log(response.data)
	                    if(response.data.length !== 0) {
	                        this.items = response.data
	                    }
	                    else
	                        alert("No results!")
	                })
	        	}
	        	
	        },
	        searchUsersByUsername(){
	                
	            axios.get("/usersSearchByUserName/" + this.searchQuery)
                .then(response => {
                    console.log(response.data)
                    if(response.data.length !== 0) {
                        this.users = response.data
                    }
                    else
                        alert("No results!")
                })
	        },
	        
	        searchUsersBySurname(){
	            axios.get("/usersSearchBySurname/" + this.searchQuery)
                .then(response => {
                    console.log(response.data)
                    if(response.data.length !== 0) {
                        this.users = response.data
                    }
                    else
                        alert("No results!")
                })
	                
	        },
	        filtrateByType(){
	            axios.get("/usersFiltrateByType/" + this.filterParam)
                .then(response => {
                    console.log(response.data)
                    if(response.data.length !== 0) {
                        this.users = response.data
                        this.items = this.users
                    }
                    else
                        alert("No results!")
                })
	                
	        },
		filtrateByRole(){
			axios.get("/usersFiltrateByRole/" + this.filterParam)
				.then(response => {
					console.log(response.data)
					if(response.data.length !== 0) {
						this.users = response.data
						this.items = this.users
					}
					else
						alert("No results!")
				})

		},
		isDeleted(item){
			axios.post("/delete", item)
				.then(response => {
					location.reload();
				})
		}
	      
	},
});	