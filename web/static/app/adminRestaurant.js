Vue.component("adminRestaurant", {
	data: function () {
		return {
				role: "",
				articles: [],
				commentsRejected: [],
				commentsApproved: [],
				commentsProcessing: [],
				selectedComment: null,
				restaurant: {},
				status: 'items',
				newArticle:{
					image: '',
				},
				imageFile: '',
				name: null,
				price: null,
				description: null,
				type: null,
				quantity: null,
				editedArticle: {},
				approvedComment: {},
				status: null,
				articleName: null,
				selectedItem: null,
				quantitySC: null,
				shoppingCartItems: [],
				itemsPrice: 0,
				shoppingCartt: {}
				}
	},
	mounted() {
        this.role = window.localStorage.getItem('role');
		 axios.get("/getRestaurantByName/" + JSON.parse(localStorage.getItem('restaurant')))
        .then(response => {
            console.log(response.data)
            this.restaurant = response.data;               
        });
		
		axios.get("/getArticlesByRestaurantName/" + JSON.parse(localStorage.getItem('restaurant')))
	       .then(response => {
	           console.log(response.data)
	           this.articles = response.data;               
	       });
		
		axios.get("/getCommentsWithStatusRejected")
			.then(response => {
				console.log(response.data)
				this.commentsRejected = response.data;
			});
		
		axios.get("/getCommentsWithStatusApproved")
		.then(response => {
			console.log(response.data)
			this.commentsApproved = response.data;
		});
	
		axios.get("/getAllCommentsByRestaurant/" +  JSON.parse(localStorage.getItem('restaurant')))
		.then(response => {
			console.log(response.data)
			let customer = response.data.customer;
			console.log(customer)
			this.commentsProcessing = response.data;
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
		<div @click="changeDisplay('reviews')" v-bind:class="{ selected: status=='reviews' }">Reviews</div>
	</div>
	
	
	<div class="items-list" v-if="status =='items'">
	<div style="width: 888px; margin: auto" class="headr">
			<h3>Articles</h3>
		<button data-toggle="modal" class="modal-button" data-target="#exampleModalCenter"><span class="headr-new-item" v-if="accessControlManager"> <i class="fas fa-plus"></i> New Article </span></button>
		<button class="btn btn-success" @click="createShoppingCart" v-if="role == 'CUSTOMER'"><span class="headr-new-item" v-if="accessControlCustomer"> Shopping Cart </span></button>

		</div>
	<div :key="index" v-for="(article, index) in articles" class="item-wrapper">
		<div class="item" >
			<div class="item-image-container">
				<img class="sc-item-image" v-bind:src="'../'+article.image" alt="" />
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
					<button class="btn btn-success" data-toggle="modal" data-target="#addToCartDialog" @click="setEditableArticle(article)"
						>Add to Shopping Cart<b></b></button
					>
				</div>
				<div class="item-pricing">
					<label class="item-price" for="username"
						>Quantity: <b>{{ article.quantity }} gr</b></label
					>
				</div>
				<div class="item-pricing">
					<label class="item-price" for="username"
						>Price: <b>{{ article.price }} RSD </b></label
					>
				</div>
			</div>
		</div>
		
	
			<div class="item-options" v-if="accessControlManager">
				<div class="space"> Edit </div>
				<div class="item-options-panel">
					<div class="single-option" data-toggle="modal" data-target="#editModal" @click=setEditableArticle(article)  style="color: #edf5e1">
						<i class="fas fa-edit fa-1x"> Edit</i>
					</div>
					<div class="single-option align" style="color: #edf5e1">
						<i class="far fa-trash-alt" style="color: #edf5e1"></i>
					</div>
				</div>
				</div>
		</div>
			</div>
	
		<!-- COMMENTS-->
			
		<div class="comment-list" v-if="status=='reviews'">
		<div ref="comments" id="commentClass" class="comment" :key=comment.customer v-for="comment in commentsProcessing" @click= "select($event)" data-toggle="modal" data-target="#approveModal">
		<div class="comment-icon"  v-if="role == 'CUSTOMER' && comment.status == 'Approved' || role == 'MENAGER'  || role == 'ADMIN' && comment.status=='Approved' || comment.status== 'Rejected' ">
		<i class="fas fa-user fa-3x"></i>
		<label>{{comment.status}}</label>
		</div>
		<div class="comment-section"  v-if="role == 'CUSTOMER' && comment.status == 'Approved' || role == 'MENAGER' || role == 'ADMIN' && comment.status=='Approved' || comment.status== 'Rejected'">
			<div class="comment-top">
				<h4>{{comment.customer}}</h4>
				<div class="grade">
            	<i class="fas fa-star" style="color: #FAE480"></i>
            	<label>{{comment.grade}}</label>	
        	</div>
			</div>
			<label for="">{{comment.text}}</label>
		</div>
	</div>
	</div>
	<div class="modal fade" id="approveModal" tabindex="-1" role="dialog" aria-labelledby="approveModal" aria-hidden="true" v-if="accessControlManager">
		<div class="modal-dialog modal-dialog-centered" role="document">
		  <div class="modal-content">
			<div class="modal-header">
			  <h5 class="modal-title" id="exampleModalLongTitle">Approve customer review</h5>
			  <button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				<i class="fas fa-times"></i>
			  </button>
			</div>
			<div class="modal-body">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			  <button type="button" class="btn btn-primary" data-dismiss="modal" @click= "approveComment">Approve</button>
			  <button type="button" class="btn btn-primary" data-dismiss="modal" @click= "rejectComment">Reject</button>
		  </div>
		</div>
	  </div>
	  </div>	
			
	
	<!-- ADD NEW ARTICLE DIALOG -->		
			
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
		  <div class="article-image mb-2" @click="onPickFile()" v-if="!newArticle.image" >
			<i class="far fa-image fa-3x"></i>
			  <input type="file" @change="uploadPhoto" accept="image/*" style="display:none" ref="fileInput"/>
		  </div>
			<div class="article-image mb-2" v-if="!!newArticle.image">
							<img class="item-image" :src="getImage(newArticle.image)" alt="Image" />
						    <div class="remove-image" @click="removeImage"><i class="fas fa-times"></i> Remove image</div>
		</div>
		<div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Name"  ref="name" v-model="name">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Price</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Price"  ref="price" v-model="price">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Type</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Type"  ref="type" v-model="type">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Description</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Optional*" ref="description" v-model="description">
			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Quantity</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Optional*" ref="quantity" v-model="quantity">
			</div>
		  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary"  data-dismiss="modal" @click= "addNewArticle">Save changes</button>
      </div>
	</form>
    </div>	
		
		</div>
  </div>
  </div>


<!-- ADD TO CART DIALOG -->

<div class="modal fade" id="addToCartDialog" tabindex="-1" role="dialog" aria-labelledby="addToCartDialogTitle" aria-hidden="true" v-if="accessControlCustomer">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalLongTitle">Add to Shopping Cart</h5>
        	<button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
				<i class="fas fa-times"></i>
		  	</button>
		</div>
	<div class="modal-body">
		  <form name='new-item-form'>
		<div class="form-group row mb-2" >
			<label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-10">
				<label type="text">{{editedArticle.name}}</label>

			</div>
		  </div>
		  <div class="form-group row mb-2">
			<label for="staticEmail" class="col-sm-2 col-form-label">Quantity</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" placeholder="Quantity"  ref="name" v-model="quantitySC">
				<small type="text" style="color:red">Quantity must be greater than 1!</small>
			</div>
		  </div>
		
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary"  data-dismiss="modal" @click="addArticleToSC(editedArticle)">Add</button>
      </div>
	</form>
    </div>	
		</div>
  </div>
  </div>

<!-- EDIT ARTICLE DIALOG -->

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="exampleModalLongTitle">Edit article</h5>
		  <button type="button" class="close comment-button" data-dismiss="modal" aria-label="Close">
			<i class="fas fa-times"></i>
		  </button>
		</div>
		<div class="modal-body">
			<form name='new-item-form'>
			<div class="article-image mb-2" @click="onPickFile()" v-if="!newArticle.image">
			  <i class="far fa-image fa-3x"></i>
			  <input type="file" @change="uploadPhoto" accept="image/*" style="display:none" ref="fileInput" />
			</div>
			<div class="article-image mb-2" v-if="!!newArticle.image">
							  <img class="item-image" :src="getImage(newArticle.image)" alt="Image" />
							  <div class="remove-image" @click="removeImage"><i class="fas fa-times"></i> Remove image</div>
		  </div>
		  <div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Name"  ref="name" v-model="editedArticle.name">
			  </div>
			</div>
			<div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Price</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Price"  ref="price" v-model="editedArticle.price">
			  </div>
			</div>
			<div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Type</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Type"  ref="type" v-model="editedArticle.type">
			  </div>
			</div>
			<div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Quantity</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Optional*" ref="description" v-model="editedArticle.quantity">
			  </div>
			</div>
			<div class="form-group row mb-2">
			  <label for="staticEmail" class="col-sm-2 col-form-label">Description</label>
			  <div class="col-sm-10">
				  <input type="text" class="form-control" placeholder="Optional*" ref="description" v-model="editedArticle.description">
			  </div>
			</div>
		<div class="modal-footer">
		  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		  <button type="button" class="btn btn-primary" @click="updateArticle" data-dismiss="modal">Save changes</button>
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
		}

	},
	methods: {
		changeDisplay(status){
			console.log(status);
			this.status=status
		},
		getImage(image) {
			if(image.includes("data")){
                return image;
            }
			if (image.includes(baseUrl))
			return !!image ? `${baseUrl}/${image}` : '';
		},
		uploadPhoto(event) {
			const files = event.target.files;
			const fileReader = new FileReader();
			fileReader.addEventListener('load', () => {
				this.newArticle.image = fileReader.result;
			});
			fileReader.readAsDataURL(files[0]);
			this.imageFile = files[0];
		},
		onPickFile() {
			console.log('Open images');
			this.$refs.fileInput.click();
		},
		removeImage() {
			this.newArticle.image = '';
		},
		
		addNewArticle(e){
			 	e.preventDefault();
	            e.preventDefault();

	            this.errors = null;
	            if(!this.name || !this.price || !this.type){
	                alert("Fill out all the fields")
	                e.preventDefault();
	            }else{
	                axios.post('/addArticleToRestaurant', { name: this.name,
	                        price: this.price,
	                        description : this.description,
	                        quantity: this.quantity,
	                        type: this.type,
	                        restaurant: JSON.parse(localStorage.getItem('restaurant')),
	                        image: this.newArticle.image
	                    })
	                    .then(response => {
	                        if(response.data){
	                            alert("You have successfully added new article!")
	                            location.reload()
	                        }else
	                            alert("That article already exists!")
	                    });
	            }
		},
		setEditableArticle(article) {
			console.log(article);
			this.editedArticle=article;
		},
		
		updateArticle(e){
			e.preventDefault();
            e.preventDefault();
			this.errors = null;
                axios.post('/updateArticle', this.editedArticle, {})
                    .then(response => {
                        if(response.data){
                            alert("You have successfully update article!")
                            location.reload()
                        }else
                            alert("That article already exists!")
                    });   
		},
		approveComment(){
			axios.post('/approveComment', {status: 'Approved', restaurant: JSON.parse(localStorage.getItem('restaurant')), customer:  JSON.parse(localStorage.getItem('commentCustomer'))})
            .then(response => {
                if(response.data){
                    alert("Your comment is approved now!")
                    location.reload()
                }else
                    alert("That article already exists!")
            });   
		},
		rejectComment(){
			axios.post('/rejectComment', {status: 'Rejected', restaurant: JSON.parse(localStorage.getItem('restaurant')), customer:  JSON.parse(localStorage.getItem('commentCustomer'))})
            .then(response => {
                if(response.data){
                    alert("Your comment is rejected now!")
                    location.reload()
                }else
                    alert("That article already exists!")
            });   
		},
		select(event){
			let divId= event.currentTarget.dataset;
			let data = JSON.parse(JSON.stringify(divId));
		},
		addArticleToSC(){
			let items = {
				articleName: this.editedArticle.name,
				quantity: this.quantitySC,
				image: this.editedArticle.image,
				price: this.editedArticle.price
			}
			if(this.quantitySC ==  0){
				alert("Quantity must be greater than 1!")
			}else {
				let itemsPrice = this.quantitySC * this.editedArticle.price
				this.itemsPrice += itemsPrice
				this.shoppingCartItems.push(items)
			}
		},
		createShoppingCart(event){
			if(this.shoppingCartItems !== null){

				var shoppingCart = {
					id: new Date().getSeconds(),
					items: this.shoppingCartItems,
					customer: localStorage.getItem('username'),
					price: this.itemsPrice,
					restaurantName: JSON.parse(localStorage.getItem('restaurant'))
				};

				axios.post('/createShoppingCart', shoppingCart)
					.then(response => {
						if(response.data){
							alert("Your shopping cart is created!")
							localStorage.setItem("shoppingCartID", shoppingCart.id.valueOf())
							this.$router.push('shoppingCart')
						}else
							alert("That shopping cart already exists!")
					});
			}


		},
		handlerFunc(event){
			console.log(event.target)
			this.selectedItem = event.name
			console.log(this.selectedItem)
		}
	}
});