const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shoppingCart></shoppingCart>' }
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
const Orders = { template: '<orders></orders>' }
const ManagerRestaurant = { template: '<managerRestaurant></managerRestaurant>' }
const DeliveryRequests = { template: '<deliveryRequests></deliveryRequests>' }


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
		{ path: '/adminRestaurant', component: AdminRestaurant },
		{ path: '/orders', component: Orders },
		{ path: '/shoppingCart', component: ShoppingCart },
		{ path: '/managerRestaurant', component: ManagerRestaurant },
		{ path: '/deliveryRequests', component: DeliveryRequests }
	  ]
});

var app = new Vue({
	router, 
	el: '#webShop'
});
 
