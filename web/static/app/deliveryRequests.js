Vue.component("deliveryRequests", {
    data: function () {
        return {
            requests:[]
        }
    },
    mounted() {
        axios.get("/getAllRequestsForDelivering")
            .then(response => {
                console.log(response.data)
                this.requests = response.data;


            });
    },

    template:
        `
	
    <div class="restaurant-list">

		<h2 class="text-center">Requests for delivering</h2>
		
        <div :key="request.orderId" v-for="request in requests">
		    <div class="container" name="rest" style= "margin-top:10px;
			    color: #42405F;
			    display: flex;
			    flex-direction: row;
			    background-color: white;
			    padding: 17px;
			    border-radius: 20px;
			    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
			    cursor: pointer;
			    min-width: 800px;
			    width: 80%;">
	    
		    <div class="restaurant-info" data-toggle="modal" data-target="#requestModal">
                <div class="restaurant-location">
		            <p>📍:Deliverer: {{request.supplier}}</p>
		        </div>
		        <div class="restaurant-status">
		           		    <p>Order ID: {{request.orderId}}</p>
		        </div>
		        <div class="restaurant-status">
		           		    <p>Status: {{request.status}}</p>
		        </div>		    </div>
		  
		    <div class="restaurant-details">
		        <button class="btn btn-success" @click="acceptRequest(request)"
						><b>ACCEPT</b></button
					>
					<label></label>
					<label></label>
					<label></label>

			    <button class="btn btn-danger" @click="denyRequest(request)"
						><b>DENY</b></button
					>
		    </div>
		    </div>		  	
  </div>
  </div>

	
	`,
    computed : {},
    methods: {
        denyRequest(request) {
            axios.post('/processSupplierRequetst/'+ request.orderId + "/" + request.supplier + "/" + "cancel")
                .then(response => {
                        alert("You have successfully deny request!")
                        location.reload()
                    });
        },
        acceptRequest(request) {
            axios.post('/processSupplierRequetst/'+ request.orderId + "/" + request.supplier + "/" + "accept")
                .then(response => {
                    if(response.data){
                        alert("You have successfully accepted request!")
                        location.reload()
                    }else
                        alert("That article already exists!")
                });
        }
    },
});