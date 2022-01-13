Vue.component("navbar", {
    data: function () {
        return {
            role:"",
            username:"",
        };
    },
    methods: {
        refresh() {
            this.$forceUpdate()
        },
        logout: function (event) {
            event.preventDefault();
            localStorage.removeItem("role");
            localStorage.removeItem("username");
            router.replace({ path: `/login` })

        },
    },
    mounted: function () {
        this.username = window.localStorage.getItem('username');
        this.role = window.localStorage.getItem('role');
        console.log(this.role);
    },
    template: ` 
    <nav style="min-height:10vh;" class="container-fluid navbar navbar-dark bg-dark navbar-expand-md mb-4">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
          <li class="nav-item active">
            <a class="btn mr-1 btn-dark" href="/#/restaurants">Food Delivery</a>
          </li>
          <li v-if="role =='ADMIN' " class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/adminUsers">View users</a>
          </li>
          <li v-if="role =='ADMIN'" class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/registerSupplier">Register Deliverer</a>
          </li>
          <li v-if="role =='ADMIN'" class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/registerManager">Register Manager</a>
          </li>
          <li v-if="role =='ADMIN'"  class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/createRestaurant">Create Restaurant</a>
          </li>
          <li v-if="role =='admin' | role =='prodavac'"  class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/tickets">Tickets</a>
          </li>
          <li v-if="role =='CUSTOMER' || role =='SUPPLIER' || role =='MENAGER'" class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/orders">View orders</a>
          </li>
          <li v-if="role =='salesman' " class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/approveComments">Comment approval</a>
        </li>
        <li v-if="role =='customer'"  class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/ticketUser">Tickets</a>
          </li>
          <li v-if="role == 'salesman'"  class="nav-item active">
                <a type="button" class="btn btn-dark mr-1" href="/#/addEvent">Add event</a>
            </li>
            <li v-if="role == 'salesman'"  class="nav-item active">
                <a type="button" class="btn btn-dark mr-1" href="/#/eventSalesman">View events</a>
            </li>
        </ul>
        <ul class="nav navbar-nav ml-auto" style="left: auto !important;
        right: 0px;">
            <li v-if="role == 'ADMIN' || role == 'SUPPLIER' || role == 'CUSTOMER' || role == 'MENAGER'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/profile">Profile</a>
          </li>
          <li v-if="role == 'admin' | role == 'salesman' | role == 'customer'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/changePassword">Change password</a>
          </li>
            <li v-if="role != 'ADMIN' && role != 'MENAGER' && role != 'CUSTOMER' && role != 'SUPPLIER'" class="nav-item active right">
                <a class="btn btn-dark mr-1" href="/#/registration">Register</a>
            </li>
            <li v-if="role != 'ADMIN' && role != 'MENAGER' && role != 'CUSTOMER' && role != 'SUPPLIER'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/login">Login</a>
            </li>
            <li v-if="role == 'CUSTOMER' || role == 'MENAGER' || role == 'SUPPLIER' || role == 'ADMIN'"  class="nav-item active">
                <button @click="logout" type="button" class="btn btn-dark mr-1">Logout</button>
            </li>
            
        </ul>
        
      
  </nav>
    `,
});