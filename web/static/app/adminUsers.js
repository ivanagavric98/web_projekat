Vue.component("adminUsers", {
	data: function () {
		return {
            sort: {
                key: '',
                isAsc: false,
                selectedItem: -1
            },
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
		  <input type="text" class="form-control" placeholder="Search..." aria-label="" aria-describedby="basic-addon2">
		  <div class="input-group-append">
		    <button class="btn btn-primary" type="button">Search</button>
		  </div>
		    </div>
		    
		     <div class="row mb-5">
				  <div class="col-3">
				  <h6>User role: </h6>
				  <select class="custom-select" >    
				    <option value="" disabled selected>Select user role...</option>
				    <option value="All">All roles</option>
				    <option value="Customer">Customer</option>
				    <option value="Manager">Manager</option>
				    <option value="Supplier">Deliverer</option>
				  </select></div>
				  <div class="col-3">
				    <h6>User type: </h6>
				  <select class="custom-select">
				    <option value="" disabled selected>Select user type...</option>
				    <option value="All">All types</option>
				    <option value="Regular">Regular</option>
				    <option value="Bronze">Bronze</option>
				    <option value="Silver">Silver</option>
				    <option value="Gold">Gold</option>
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
		 sortedClass (key) {
	            return this.sort.key === key ? `sorted ${this.sort.isAsc ? 'asc' : 'desc' }` : '';
	        },
	        sortBy (key) {
	            this.sort.isAsc = this.sort.key === key ? !this.sort.isAsc : false;
	            this.sort.key = key;
	        },
	        selectRow(item){
	            this.selectedItem = item;
	        }
	      
	},
});	