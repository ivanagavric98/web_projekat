Vue.component("profile", {
	data: function () {
		return {
				user: null,
		}
	},
	mounted() {
		axios.get('/getUser', {
		})
		.then(response => (
				this.user = response.data
		));
		console.log(user.username);
    },
	template: 
	`
	<div id="Edit">
		<section class="h-100 h-custom" >
		  <div class="container py-5 h-100">
		    <div class="row d-flex justify-content-center align-items-center h-100">
		      <div class="col-lg-8 col-xl-6">
		        <div class="card rounded-3">
		          <img src="images/food.jpg" class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;" alt="Sample photo">
		          <div class="card-body p-4 p-md-3">
		            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2 text-center">{{user.name}}'s Profile</h3>
	
		            <form class="px-md-2">
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Name</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model = "user.name" readonly />
		              </div>
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Surname</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model = "user.surname" readonly/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Username</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model = "user.username" readonly/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Password</label>
		                <input type="password" id="form3Example1q" class="form-control" v-model = "user.password" readonly/>
		              </div>    
		              
		              <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Date of Birth</label>		
		                 <input type="date" id="form3Example1q" class="form-control" v-model = "user.dateOfBirth" readonly/>
		            </div>
		            
		              <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Gender</label>		
				            <select id="form3Example1q"  class="form-control" v-model = "user.gender" readonly>
							  	<option value="MALE" >Male</option>
							  	<option value="FEMALE">Female</option>
						    </select>
				  </div>
					
					<div class="text-center">
		              <button type="submit" class="btn btn-success btn-lg mb-1" @click="$router.push('editPersonalInfo')">Edit</button>
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
	methods: {
		
	},
});