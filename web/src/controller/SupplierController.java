package controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.Supplier;
import service.SupplierService;

public class SupplierController {
    private SupplierService supplierService;
	private static Gson gson = new Gson();

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

		public Boolean register(Supplier supplier) throws JsonSyntaxException, IOException{			
			return supplierService.register(supplier);
	   }
	
}
