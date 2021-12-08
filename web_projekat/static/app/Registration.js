Vue.component("Registration", {

    data() {
        return {
            firstName: null,
            lastName: null,
            password: null,
            password2: null,
            gender: null,
            birthday: null,
        }
    },

    methods: {
       
    },

    template: `
<div class="container d-flex justify-content-center my-5">
    <div class="row my-2 mx-2 main">
        <!--left-column-->
        <div class="col-md-4 col-12 mycol">
            <!--image--> <img src="https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8&w=1000&q=80"  height="100%" width="100%"> </div>
        <!--right-column-->
        <div class="col-md-8 col-12 xcol">
            <h2 class="title pt-5 pb-3">Register</h2>
            <form class="myform">
                <div class="row rone">
                    <div class="form-group col-md-6 fone py-3"> <input type="text" class="form-control" placeholder="First name"> </div>
                    <div class="form-group col-md-6 ftwo py-3"> <input type="text" class="form-control" placeholder="Last name"> </div>
                </div>
                <div class="row rtwo">
                    <div class="form-group col-md-6 fthree py-3"> <input type="text" class="form-control jk" placeholder="Username"> </div>
                    <div class="form-group col-md-6 ffour py-3"> <input type="password" class="form-control lm" placeholder="Password"> </div>
                </div>
                <div class="row rthree">
                    <div class="form-group col-md-6 ffive py-3"> 
                    <select>
						<option value="none" selected>Gender</option>
						<option value="male">Male</option>
						<option value="female">Female</option>
					</select> 
					</div>
                    <div class="form-group col-md-6 fsix py-3"> <input type="date" class="form-control" placeholder="Date Of Birth"> </div>
                </div>
                <div class="row rfour">
                    <div class="form-group col-md-6 fseven py-3"> <button type="submit" class="btn btn-primary"><span>Create account</span></button> </div>
                    <div class="form-group col-md-6 feight py-3">
                        <p class="text-muted">Already have an account?<br><a href="#">Sign in</a></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
        `
    });