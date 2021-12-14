package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Address;
import service.AddressService;

public class AddressController {
    private AddressService addressService;
	private static Gson gson = new Gson();

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

		public Boolean register(Address address) throws JsonSyntaxException, IOException{			
			return addressService.register(address);
	   }
	
}
