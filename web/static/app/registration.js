Vue.component("registration", {
	data: function () {
		return {
			password_confirmed:null,
			pomocna:null,
			isValid:null,
			patientDTO: {
				id:null,
				name: null,
				surname: null,
				address: null,
				city: null,
				country: null,
				email: null,
				password: null,
				phoneNumber: null,
				emailComfirmed: null,
				firstTimeLogin: null,
				points: null,
				penalty: null,
				drugAllargies:[],
				username:null
			},
		}
	},
	mounted() {
	},
	template: 
	`
	<div id="Registration">
    <div class="container">
        <br/><h2 class="text1">Registration</h2>
		<br><br><br>
		<table class="t">
			<colgroup>
                 <col style="width: 50%;">
                 <col style="width: 50%;">
            </colgroup>
			<tr>
				<td><label>Name</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input" v-model="patientDTO.name"/></td><br/>
			<tr>
			<tr><td>&nbsp;</td>
				 <td align="left" style="color: red;font-size:12px">{{nameValidation}}</td>
			</tr>
			<tr>
				<td><label>Surname</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input" v-model="patientDTO.surname"/></td><br/>
			</tr>
			<tr><td>&nbsp;</td>
				 <td align="left" style="color: red;font-size:12px">{{surnameValidation}}</td>
			</tr>
			<tr>
				<td><label>Address</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input" v-model="patientDTO.address"/></td><br/>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left" style="color: red;font-size:12px">{{addressValidation}}</td>
				<td></td>
			</tr>
			<tr>
				<td><label>City</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input" v-model="patientDTO.city"/></td><br/>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left" style="color: red;font-size:12px">{{cityValidation}}</td>
			</tr>
			<tr>
				<td><label>State</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input" v-model="patientDTO.country"/></td><br/>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left" style="color: red;font-size:12px">{{stateValidation}}</td>
			</tr>
		</table>
			<table class="t">
			<tr>
			<tr>
				<td><label>Contact number</label><a class="star">*</a></td>
				<td><input type="number" class = "form-control input" v-model="patientDTO.phoneNumber"/></td><br/>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left" style="color: red;font-size:12px">{{numberValidation}}</td>

			</tr>
			<tr>
				<td><label>Drug allergies</label><a class="star"></a></td>
				<td><input type="text" class = "form-control input" v-model="pomocna"/></td><br/>
			</tr>
			<tr>
				<td>&nbsp;</td>
				
				<td align="left" style="color: red;font-size:12px">{{pomocnaValidation}}</td>

				<td></td>
			</tr>
			<tr>
				<td><label>Email</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input"  v-model="patientDTO.email"/></td><br/>
			</tr>
			<tr><td>&nbsp;</td>
				 <td align="left" style="color: red;font-size:12px">{{mailValidation}}</td>
			</tr>
			<tr>
				<td><label>Username</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input"  v-model="patientDTO.username"/></td><br/>
			</tr>
			<tr><td>&nbsp;</td>
				 <td align="left" style="color: red;font-size:12px">{{usernameValidation}}</td>
			</tr>
			<tr>
				<td><label>Password</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input"  v-model="patientDTO.password"/></td><br/>
			</tr>
			<tr><td>&nbsp;</td>
			</tr>
			<tr>
				<td><label>Password</label><a class="star">*</a></td>
				<td><input type="text" class = "form-control input"  v-model="password_confirmed"/></td><br/>
			</tr>
			<tr><td>&nbsp;</td>

			</tr>
			
			
			
			</table>
			<button  type="button" class="btn2 btn-info btn-lg margin1" data-toggle="modal" v-on:click="AddPatient(patientDTO)">Submit</button>
			<button id="Close" type="button" class="btn1 btn-info btn-lg margin form-control" data-toggle="modal" v-on:click="close()" >Go back</button>

			<br/>
			<br/>
    </div>
    </div>
	`,
	computed : {
		nameValidation: function () {
			if (this.patientDTO.name != undefined && this.patientDTO.name.length > 0) {
				let nameMatch = this.patientDTO.name.match('[A-Za-z ]*');
				if (nameMatch != this.patientDTO.name) return 'The name may contain only letters';
				else if (this.patientDTO.name[0].match('[A-Z]') === null) return 'The name must begin with a capital letter';
			}
			else if (this.patientDTO.name === '') return 'Name is a required field';
			else return null;
		},
		surnameValidation: function () {
			if (this.patientDTO.surname != undefined && this.patientDTO.surname.length > 0) {
				let surnameMatch = this.patientDTO.surname.match('[A-Za-z ]*');
				if (surnameMatch != this.patientDTO.surname) return 'The surname may contain only letters';
				else if (this.patientDTO.surname[0].match('[A-Z]') === null) return 'The surname must begin with a capital letter';
			}
			else if (this.patientDTO.surname === '') return 'Surname is a required field';
			else return null;
		},pomocnaValidation: function () {
			return 'if you have more than one allergy, enter with a comma between: e.g .: panicillin, panclav'
		;
		},
		mailValidation: function () {
			if (this.patientDTO.email != undefined && this.patientDTO.email.length > 0) {
				let mailMatch = this.patientDTO.email.match("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$");
				if (mailMatch != this.patientDTO.email) return 'Please insert correct email form';
			}
			else if (this.patientDTO.email === '') return 'Mail name is a required field';
			else return null;
		},numberValidation: function () {
			if (this.patientDTO.phoneNumber != undefined && this.patientDTO.phoneNumber.length > 0) {
				if ( this.patientDTO.phoneNumber.length==0 || this.patientDTO.phoneNumber.length<9 || this.patientDTO.phoneNumber.length>11) return 'The number may contain at least 9 digits and max 11 digits';
			}
			else if (this.patientDTO.name === '') return 'Number is a required field';
			else return null;
		},
		cityValidation: function () {
			if (this.patientDTO.city != undefined && this.patientDTO.city.length > 0) {
				let cityMatch = this.patientDTO.city.match('[A-Za-z ]*');
				if (cityMatch != this.patientDTO.city) return 'The city may contain only letters';
				else if (this.patientDTO.city[0].match('[A-Z]') === null) return 'The city must begin with a capital letter';
			}
			else if (this.patientDTO.city === '') return 'City is a required field';
			else return null;
		},
		usernameValidation: function () {
			if (this.patientDTO.username != undefined && this.patientDTO.username.length > 0) {
				let usernameMatch = this.patientDTO.username.match('[A-Za-z1-9 ]*');
				if (usernameMatch != this.patientDTO.username) return 'The username may contain only letters and numbers';
			}
			else if (this.patientDTO.username === '') return 'Username is a required field';
			else return null;
		},	
		stateValidation: function () {
			if (this.patientDTO.country != undefined && this.patientDTO.country.length > 0) {
				let countryMatch = this.patientDTO.country.match('[A-Za-z ]*');
				if (countryMatch != this.patientDTO.country) return 'The country may contain only letters';
				else if (this.patientDTO.country[0].match('[A-Z]') === null) return 'The country must begin with a capital letter';
			}
			else if (this.patientDTO.country === '') return 'Country is a required field';
			else return null;
		},
		addressValidation: function () {
			if (this.patientDTO.address != undefined && this.patientDTO.address.length > 0) {
				let addressMatch = this.patientDTO.address.match('[A-Za-z1-9 ]*');
				if (addressMatch != this.patientDTO.address) return 'The address may contain only letters and numbers';
				else if (this.patientDTO.address[0].match('[A-Z]') === null) return 'The address must begin with a capital letter';
			}
			else if (this.patientDTO.address === '') return 'Address is a required field';
			else return null;
		}
		
    },
	methods: {
		close:function(){
			this.$router.push('systemAdminHomaPage');
		  },
		AddPatient: function (patientDTO) {
			if(this.password_confirmed!=this.patientDTO.password){
					alert( 'Passwords did not match!');	
					return	
			}else if(this.patientDTO.name==null || this.patientDTO.surname==null || this.patientDTO.address==null || 
				this.patientDTO.city==null || this.patientDTO.country==null || this.patientDTO.phoneNumber==null || 
				this.patientDTO.email==null || this.patientDTO.password==null){
				alert('All fields must be filled!')
				return
			}else{

				axios
				.get('/patient/isUsernameValid/'+patientDTO.username,{
					headers: {
						'Authorization': 'Bearer' + " " + localStorage.getItem('token')
					}
				})
				.then(response => {
					this.isValid=response.data;
					if(this.isValid==false){
					   alert('username already exists, please choose another one!')
					   return
					}else{

					patientDTO.emailComfirmed=false
					patientDTO.firstTimeLogin=false
					patientDTO.points=0
					patientDTO.penalty=null
					patientDTO.description="/"



					if(this.pomocna==""){
						patientDTO.drugAllargies="non"
					}else{
						patientDTO.drugAllargies=this.pomocna
					}

					
				
					axios
					.get('/patient/savePatientDrugAllergies/' + patientDTO.drugAllargies,{
						headers: {
							'Authorization': 'Bearer' + " " + localStorage.getItem('token')
						}
					})
					.then(response => {
						var pom = response.data			
				
				
						patientDTO.drugAllargies=pom
						
				

						axios
						.post('/api/saveUser' , patientDTO,{
							headers: {
								'Authorization': 'Bearer' + " " + localStorage.getItem('token')
							}
						})
						.then(response => {
						
							axios
							.post('/patient/savePatient' , patientDTO,{
								headers: {
									'Authorization': 'Bearer' + " " + localStorage.getItem('token')
								}
							})
							.then(response => {
								alert("Successfully registered user, please log in to continue");
								localStorage.setItem('userId', "");
								localStorage.setItem('token', "");
								localStorage.setItem('role', "");
								this.$router.push('/');

							})
	
							.catch(error => {
								
								alert("Something went wrong. Please try again later");
								this.$router.push('/');

							})
						})

						.catch(error => {
							
							alert("	Something went wrong. Please try again later");
							this.$router.push('/');

						})})

					.catch(error => {
					})
					
					
					}
				})

				.catch(error => {
					
					alert("	Something went wrong. Please try again later");
					this.$router.push('/');				})

			}				
		}
	},
});