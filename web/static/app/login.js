Vue.component("login", {
	data: function () {
		return {
				password: null,
				username: null
				}
	},
	mounted() {},
	template: 
	`
	<div id="Login">
		<section class="h-100 h-custom" >
		  <div class="container py-5 h-100">
		    <div class="row d-flex justify-content-center h-100">
		      <div class="col-lg-8 col-xl-6">
		        <div class="card rounded-3">
		          <img src="images/food.jpg" class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;" alt="Sample photo">
		          <div class="card-body p-4 p-md-2">
		            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">LOG IN</h3>
	
		            <form class="px-md-2" @submit="login">
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Username</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="username"/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Password</label>
		                <input type="password" id="form3Example1q" class="form-control" v-model="password"/>
		              </div>    
					
					<div class="text-center">
		              <button type="submit" class="btn btn-success btn-lg mb-1" >Log In</button>
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
		  login(e) {
	            e.preventDefault();
	            e.preventDefault();

	            this.errors = null;
	            if(!this.username || !this.password){
	                alert("Please, fill out all the fields.")
	                e.preventDefault();
	            }else{
	                axios.post('/login', { 
	                        username : this.username,
	                        password: this.password
	                    })
	                    .then(response => {
	                    	user = response.data;
	                        if (response.data == "") {
	                        	console.log(user.username);
	                            alert("Wrong username or password");
	                        } else {
	                        	console.log(user.username);
	                            localStorage.setItem("username", user.username);
	                            console.log(user.username);
	                            localStorage.setItem("role", user.role);
	                            this.$router.push("editPersonalInfo")
	                        }
	                    }).catch(err => {
	                        console.log(err);
	                        this.showFailedLogin = true;
	                    });
	            }
	        }
	
	},
});