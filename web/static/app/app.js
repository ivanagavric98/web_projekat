const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const Registration = { template: '<registration></registration>' }
const LogIn = { template: '<login></login>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/sc', component: ShoppingCart},
		{ path: '/registration', component: Registration },
		{ path: '/login', component: LogIn }
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});

