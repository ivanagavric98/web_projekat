const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const Registration = { template: '<registration></registration>' }
const LogIn = { template: '<login></login>' }
const EditPersonalInfo = { template: '<editPersonalInfo></editPersonalInfo>' }
const RegisterManager = { template: '<registerManager></registerManager>' }
const RegisterSupplier = { template: '<registerSupplier></registerSupplier>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/editPersonalInfo', component: EditPersonalInfo},
		{ path: '/registration', component: Registration },
		{ path: '/login', component: LogIn },
		{ path: '/registerManager', component: RegisterManager },
		{ path: '/registerSupplier', component: RegisterSupplier }
		]
});

var app = new Vue({
	router, 
	el: '#webShop'
});
 
