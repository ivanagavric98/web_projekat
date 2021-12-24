Vue.component("registerManager", {
	data: function () {
		return {
				name: null,
				surname: null,
				password: null,
				password2: null,
				username: null,
				gender: null,
				role: null,
				dateOfBirth: null
		}
	},
	mounted() {},
	template: 
	`
	<div id="Registration">
		<section class="h-100 h-custom" >
		  <div class="container py-5 h-100">
		    <div class="row d-flex justify-content-center align-items-center h-100">
		      <div class="col-lg-8 col-xl-6">
		        <div class="card rounded-3">
		          <img src="images/food.jpg" class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;" alt="Sample photo">
		          <div class="card-body p-4 p-md-3">
		            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Register Manager</h3>
	
		            <form class="px-md-2" @submit="register">
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Name</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="name"/>
		              </div>
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Surname</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="surname"/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Username</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="username"/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Password</label>
		                <input type="password" id="form3Example1q" class="form-control" v-model="password"/>
		              </div>    
		              
		              <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Date of Birth</label>		
		                 <input type="date" id="form3Example1q" class="form-control" v-model="dateOfBirth"/>
		            </div>
		            
		              <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Gender</label>		
				            <select id="form3Example1q"  class="form-control"  v-model="gender">
							  	<option value="MALE" >Male</option>
							  	<option value="FEMALE">Female</option>
						    </select>
				  </div>
					
					<div class="text-center">
		              <button type="submit" class="btn btn-success btn-lg mb-1" >Register</button>
					</div>
		            </form>
		
		          </div>
		        </div>
		      </div>
		    </div>
		  </div>
		</section>
    </div>
	`,
	computed : {
		
    },
	methods: {
		  register(e) {
	            e.preventDefault();
	            e.preventDefault();

	            this.errors = null;
	            if(!this.name || !this.surname || !this.username || !this.password || !this.dateOfBirth || !this.gender){
	                alert("Fill out all the fields")
	                e.preventDefault();
	            }else{
	                axios.post('/registerManager', { name: this.name,
	                        surname: this.surname,
	                        username : this.username,
	                        password: this.password,
	                        gender : this.gender,
	                        dateOfBirth : this.dateOfBirth,
	                        role: "MENAGER"
	                    })
	                    .then(response => {
	                        if(response.data)
	                            alert("You have successfully registered!")
	                        else
	                            alert("That username already exists!")
	                    });
	            }
	        }
	
	},
});