Vue.component("adminRestaurant", {
	data: function () {
		return {
				articles: [],
				restaurant: {},
				status: 'items'
				}
	},
	mounted() {
		
		 axios.get("/getRestaurantByName/" + JSON.parse(localStorage.getItem('restaurant')))
        .then(response => {
            console.log(response.data)
            this.restaurant = response.data;               
        });
		
		axios.get("/getAllArticles")
	       .then(response => {
	           console.log(response.data)
	           this.articles = response.data;               
	       });
			 
		 
		 
	},
	
	template: 
	`
    <div class="restaurant-list">
        <div  >
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
	    </div>
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
	
		<div class="navigation">
		<div @click="changeDisplay('items')"  v-bind:class="{ selected: status=='items' }">Items</div>
		<div @click="changeDisplay('reviews')" v-bind:class="{ selected: status=='reviews' }">Rewievs</div>
	</div>
	
	
	<div class="items-list" v-if="status=='items'">
	<div style="width: 888px; margin: auto" class="headr">
			<h3>Articles</h3>
		<button data-toggle="modal" class="modal-button" data-target="#exampleModalCenter"><span class="headr-new-item"> <i class="fas fa-plus"></i> New Item </span></button>
		</div>
	<div :key="index" v-for="(article, index) in articles" class="item-wrapper">
		<div class="item" >
			<div class="item-image-container">
				<img class="item-image" v-bind:src="'../'+restaurant.logo" alt="" />
			</div>
			<div class="item-body">
				<div class="item-name">
					{{ article.name }}
				</div>
				<div class="item-details">
					<label class="item-description">{{ article.type }}</label>
				</div>
				<div class="item-details">
					<label class="item-description">{{ article.description }}</label>
				</div>
				<div class="item-pricing">
					<label class="item-price" for="username"
						>Price: <b>{{ article.price }} RSD </b></label
					>
				</div>
			</div>
		</div>
		
	
			<div class="item-options">
				<div class="space"> </div>
				<div class="item-options-panel">
					<div class="single-option" data-toggle="modal" data-target="#editModal"  style="color: #edf5e1">
						<i class="fas fa-edit fa-1x"></i>
					</div>
					<div class="single-option align" style="color: #edf5e1">
						<i class="far fa-trash-alt" style="color: #edf5e1"></i>
					</div>
				</div>
				</div>
		</div>
	
			</div>
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalLongTitle">New article</h5>
        	<button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				<i class="fas fa-times"></i>
		  	</button>
		</div>
	<div class="modal-body">
		  <form name='new-item-form'>
		  <div class="article-image mb-2"  >
			<i class="far fa-image fa-3x"></i>
			<input type="file"  accept="image/*" style="display:none" ref="fileInput" />
		  </div>
		  <div class="article-image mb-2" >
							<img class="item-image"  alt="Image" />
							<div class="remove-image" ><i class="fas fa-times"></i> Remove image</div>
		</div>
		<div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Name"  ref="name">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Price</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Price"  ref="price">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Description</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Optional*" ref="description">
			</div>
		  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary"  data-dismiss="modal">Save changes</button>
      </div>
	</form>
    </div>	
		
		</div>
  </div>

		</div>
	
		</div>
		
	
	`,
	computed : {
		
    },
	methods: {
		changeDisplay(status){
			console.log(status);
			this.status=status
		}
	},
});