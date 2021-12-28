const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const Registration = { template: '<registration></registration>' }
const LogIn = { template: '<login></login>' }
const EditPersonalInfo = { template: '<editPersonalInfo></editPersonalInfo>' }
const RegisterManager = { template: '<registerManager></registerManager>' }
const RegisterSupplier = { template: '<registerSupplier></registerSupplier>' }
const AdminUsers = { template: '<adminUsers></adminUsers>' }
const Profile = { template: '<profile></profile>' }
const CreateRestaurant = { template: '<createRestaurant></createRestaurant>' }
const RestaurantsOverview = { template: '<restaurants></restaurants>' }
const AdminRestaurant = { template: '<adminRestaurant></adminRestaurant>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/editPersonalInfo', component: EditPersonalInfo},
		{ path: '/registration', component: Registration },
		{ path: '/login', component: LogIn },
		{ path: '/registerManager', component: RegisterManager },
		{ path: '/registerSupplier', component: RegisterSupplier },
		{ path: '/adminUsers', component: AdminUsers },
		{ path: '/profile', component: Profile },
		{ path: '/createRestaurant', component: CreateRestaurant },
		{ path: '/restaurants', component: RestaurantsOverview },
		{ path: '/adminRestaurant', component: AdminRestaurant }
		]
});

var app = new Vue({
	router, 
	el: '#webShop'
});
 
