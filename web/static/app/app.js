const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const Registration = { template: '<registration></registration>' }
const LogIn = { template: '<login></login>' }
const EditPersonalInfo = { template: '<editPersonalInfo></editPersonalInfo>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/editPersonalInfo', component: EditPersonalInfo},
		{ path: '/registration', component: Registration },
		{ path: '/login', component: LogIn },
		]
});

var app = new Vue({
	router, 
	el: '#webShop'
});
 
