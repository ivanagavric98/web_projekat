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
            <a class="btn mr-1 btn-dark" href="/#/restaurants">Home page</a>
          </li>
          <li v-if="role =='admin'" class="nav-item active">
            <a class="btn btn-dark mr-1" href="/#/salesmanRegister">Register a salesman</a>
          </li>
        </ul>
        <ul class="nav navbar-nav ml-auto" style="left: auto !important;
        right: 0px;">
            <li v-if="role == 'admin' | role == 'menager' | role == 'customer' | role == 'supplier'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/profile">Profile</a>
          </li>
          <li v-if="role == 'admin' | role == 'menager' | role == 'customer' | role == 'supplier'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/changePassword">Change password</a>
          </li>
            <li v-if="role != 'admin' && role != 'menager' && role != 'customer' | role == 'supplier'" class="nav-item active right">
                <a class="btn btn-dark mr-1" href="/#/registration">Register</a>
            </li>
            <li v-if="role != 'admin' && role != 'menager' && role != 'customer' | role == 'supplier'" class="nav-item active">
                <a class="btn btn-dark mr-1" href="/#/login">Login</a>
            </li>
            <li v-if="role == 'admin' | role == 'menager' | role == 'customer' | role == 'supplier'"  class="nav-item active">
                <button @click="logout" type="button" class="btn btn-dark mr-1">Logout</button>
            </li>
            
        </ul>
        
      
  </nav>
    `,
});