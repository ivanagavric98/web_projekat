Vue.component("createRestaurant", {
	data: function () {
		return {
				name: null,
				type: null,
				logo: null,
				street: null,
				number: null,
				city: null,
				country: null,
				selectedManager: null,
				longitude: null,
				latitude: null,
				managers: []
				}
	},
	mounted() {
		
		axios.get('/getAllMenagersWithoutRestaurant', {
		})
		.then(response => (
				this.managers = response.data
		));
		
	},
	template: 
	`
	<div id="CreateRestaurant">
		<section class="h-100 h-custom" >
		  <div class="container py-5 h-100">
		    <div class="row d-flex justify-content-center align-items-center h-100">
		      <div class="col-lg-8 col-xl-6">
		        <div class="card rounded-3">
		          <img src="images/food.jpg" class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;" alt="Sample photo">
		          <div class="card-body p-4 p-md-3">
		            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2 text-center">Create Restaurant</h3>
	
		            <form class="px-md-2" @submit="createRestaurant">
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Name</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="name"/>
		              </div>
		
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Type</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="type"/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Street Name</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="street"/>
		              </div>
		              
		              <div class="form-outline mb-2">
		                <label class="form-label" for="form3Example1q">Street Number</label>
		                <input type="text" id="form3Example1q" class="form-control" v-model="number"/>
		              </div>    
		              
		              <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">City</label>		
		                 <input type="text" id="form3Example1q" class="form-control" v-model="city"/>
		            </div>
		            
		             <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Country</label>		
		                 <input type="text" id="form3Example1q" class="form-control" v-model="country"/>
		            </div>
					
					 <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Choose manager</label>		
				            <select id="form3Example1q"  class="form-control"  v-model="selectedManager">
							  	<option v-for="manager in managers" :value="manager">
							  	 {{ manager.name }}  {{ manager.surname }}
							  	</option>
						    </select>
				    </div>
					
					 <div class="form-outline mb-2">
		                 <label for="exampleDatepicker1" class="form-label">Logo image</label>		
		                 <input type="file" @change="addedLogo" id="img" name= "img" accept = "image/*" class="form-control"/>
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
		addedLogo(e){
			const file = e.target.files[0];
            this.createBase64Image(file);
            this.logo = URL.createObjectURL(file);
		},
		
		createBase64Image(file){
            const reader= new FileReader();

            reader.onload = (e) =>{
            	let img = e.target.result;
            	this.logo = img;
            }
            reader.readAsDataURL(file);
        },

		createRestaurant(e) {
	            e.preventDefault();
	            e.preventDefault();

	            let restaurant = {
            			name: this.name,
                        type: this.type,
                        status: 'OPEN',
                        logo: this.logo,
						averageGrade: 5,
						grade: [],
                        location: {
                        	latitude: this.latitude,
                        	longitude: this.longitude,
	                        address: {
		                        street : this.street,
		                        number: this.number,
		                        city : this.city,
		                        country  : this.country
	                       	}
                        }
            	}
	            
	            this.errors = null;
	            if(!this.name || !this.type || !this.street || !this.number || !this.city || !this.country){
	                alert("Fill out all the fields")
	                e.preventDefault();
	            }else{
	            	if(!this.selectedManager){  
    	            	  localStorage.setItem('restaurant', restaurant.name);
    	            	  alert("There is no available manager. You have to create new manager for Your restaurant.");
                          this.$router.push("registerManager")
    	            	  
    	              }
	            	
	                axios.post('/registerRestaurant', JSON.stringify(restaurant))
	                    .then(response => {
	                		let username = this.selectedManager.username	            	
	                    	axios
	                        	.post('/addRestaurantToManager/' + username, JSON.stringify(restaurant))
	                        	.then( response => {
										alert("That restaurant is created!")
										this.$router.push('restaurants')});
	                    	
	                    });
	                
	                
	            }
	        }
	},
});