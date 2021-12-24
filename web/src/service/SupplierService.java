package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.SupplierDAO;
import model.Supplier;

public class SupplierService {
    private SupplierDAO supplierDao;
    public SupplierService(SupplierDAO supplierDAO) {
      this.supplierDao=supplierDAO;
    }
    
    public Boolean register(Supplier supplier) throws JsonSyntaxException, IOException {
		ArrayList<Supplier>users=getAllSuppliers();
		Boolean result=false;
		if(users == null){
			supplierDao.create(supplier);
			result=true;
		}else{
			for(Supplier u : users){
				if(u.username.equals(supplier.username)){
				result= false;
				}else{
					supplierDao.create(u);
					result=true;
				}
			}
		}
		
		return result;
	}
 	
	public ArrayList<Supplier> getAllSuppliers() throws JsonSyntaxException, IOException{
		return supplierDao.getAll();
	}
	
}
