const WebShop = { template: '<web-shop></web-shop>' }
const ShoppingCart = { template: '<shopping-cart></shopping-cart>' }
const Registration = { template: '<registration></registration>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/sc', component: ShoppingCart ,},
		{ path: '/registration', component: Registration }
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});

