Vue.component("shoppingCart", {
	data: function () {
		    return {
		      shoppingCart: {},
			  shoppingCartItems: [],
			  customerType: {}
		    }
	},
	mounted() {
			axios.get("/getShoppingChartById/" + localStorage.getItem("shoppingCartID"))
				.then(response => {
					console.log(response.data)
					this.shoppingCart = response.data;
					this.shoppingCartItems = response.data.items;
				});
		},
	template: ` 
	<div class="sc-items-list">

		<div style="width: 800px; margin: auto" class="headr">
	
			<h2 class="text-center">Shopping Cart</h2>
			<button class="btn btn-primary" @click = "createOrder"><span class="headr-new-item">Create Order </span></button>

		</div>		
			<div class="item-wrapper">
		<div class="item" :key="index" v-for="(shoppingCartItems, index) in shoppingCart.items">
			<div class="item-image-container">
				<img class="sc-item-image"  v-bind:src="shoppingCartItems.image"/> 
			</div>
			<div class="sc-item-body">
				<div class="item-name">
					{{shoppingCartItems.articleName}}
				</div>
				<div class="sc-item-body">
					<button class="btn btn-danger" @click="updateArticleAdd(shoppingCartItems)"
						>+ <b></b></button
					>
				</div>
				<div class="sc-item-body"><b>
				{{shoppingCartItems.quantity}}x
				</b></div>
				
				<div class="sc-item-body">
					<button class="btn btn-success" @click="updateArticleDelete(shoppingCartItems)"
						>- <b></b></button
					>
				</div>
				<div class="sc-item-body">
					<label class="item-price" for="username"
						><b>Price: {{shoppingCartItems.price}} RSD</b></label
					>
				</div>
				<div class="sc-item-body">
					<button class="btn btn-light" @click="removeItem(shoppingCartItems)"
						><b>X</b></button
					>
				</div>
			</div>
		</div>
		<div class="sc-subtotal-items">
			<label class="sc-subtotal-items-label"><b><u>Subtotal: {{shoppingCart.price}} RSD</u></b></label>
		</div>
	</div>
	</div>	  
`
	, 
	methods : {
			removeItem(shoppingCartItems) {
				axios.post('/deleteArticlesFromCart/' + localStorage.getItem("shoppingCartID"), shoppingCartItems)
					.then(response => {
						if(response.data){
							alert("You have successfully delete article from cart!")
							location.reload()
						}else
							alert("That article already exists!")
					});
			},
			updateArticleAdd(shoppingCartItems) {
				shoppingCartItems.quantity += 1;
				axios.post('/updateArticleFromCart/' + localStorage.getItem("shoppingCartID"), shoppingCartItems)
					.then(response => {
						if(response.data){
							alert("You have successfully update article from cart!")
							location.reload()
						}else
							alert("That article already exists!")
					});
			},
			updateArticleDelete(shoppingCartItems) {
				shoppingCartItems.quantity -= 1;
				axios.post('/updateArticleFromCart/' + localStorage.getItem("shoppingCartID"), shoppingCartItems)
					.then(response => {
						if(response.data){
							alert("You have successfully update article from cart!")
							location.reload()
						}else
							alert("That article already exists!")
					});
			},
			createOrder(){
				let restaurant = JSON.parse(localStorage.getItem("restaurant"));

				axios.post('/addOrder/' + localStorage.getItem("username"), this.shoppingCart)
					.then(response => {
						if(response.data){
							alert("Your order is created!")
							axios.post('/updateCustomerType', this.customerType)
							localStorage.removeItem("restaurant")
							location.reload()
						}else
							alert("That article already exists!")
					});

			}
	}
});
