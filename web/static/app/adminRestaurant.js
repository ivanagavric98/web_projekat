Vue.component("adminRestaurant", {
	data: function () {
		return {
				restaurant: {}
				}
	},
	mounted() {
		
		
		 axios.get("/getRestaurantByName/" + JSON.parse(localStorage.getItem('restaurant')))
        .then(response => {
            console.log(response.data)
            this.restaurant = response.data;               
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
		<h3>Items</h3>
		<button data-toggle="modal" class="modal-button" data-target="#exampleModalCenter"><span class="headr-new-item"> <i class="fas fa-plus"></i> New Item </span></button>
	</div>
<div :key="index" v-for="(item, index) in restaurant.items" class="item-wrapper">
	<div class="item" >
		<div class="item-image-container">
			<img class="item-image" v-bind:src="'../'+restaurant.logo" alt="" />
		</div>
		<div class="item-body">
			<div class="item-name">
				{{ item.name }}
			</div>
			<div class="item-details">
				<label class="item-description">{{ item.description }}</label>
			</div>
			<div class="item-pricing">
				<label class="item-price" for="username"
					>Price: <b>{{ item.price }} RSD </b></label
				>
			</div>
		</div>
	</div>
		<div class="item-options">
			<div class="space"></div>
			<div class="item-options-panel">
				<div class="single-option" data-toggle="modal" data-target="#editModal" @click=setEditableArticle(item) style="color: #edf5e1">
					<i class="fas fa-edit fa-1x"></i>
				</div>
				<div class="single-option align" style="color: #edf5e1">
					<i class="far fa-trash-alt" style="color: #edf5e1"></i>
				</div>
			</div>
	</div>
	</div>
	</div>
	
	</div>
	
	
	`,
	computed : {
		
    },
	methods: {
	
	},
});