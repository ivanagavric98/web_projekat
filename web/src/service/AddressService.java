package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.AddressDAO;
import model.Address;

public class AddressService {
    private AddressDAO addressDAO;
	
	public AddressService(AddressDAO addressDAO) {
		this.addressDAO=addressDAO;
	}
	
	public Boolean register(Address address) throws JsonSyntaxException, IOException {
		ArrayList<Address>addresses=getAllAddresses();
		Boolean result=false;
		if(addresses.size()==0){
			addressDAO.create(address);
			result=true;
		}else{
			for(Address u : addresses){
				if(u.street.equals(address.street) && u.number.equals(address.number)){
				result= false;
				}else{
					addressDAO.create(address);
					result=true;
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Address> getAllAddresses() throws JsonSyntaxException, IOException{
		return addressDAO.getAll();
	}
}
