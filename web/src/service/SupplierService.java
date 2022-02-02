package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dao.SupplierDAO;
import model.Supplier;

public class SupplierService {
	private SupplierDAO supplierDao;

	public SupplierService(SupplierDAO supplierDAO) {
		this.supplierDao = supplierDAO;
	}

	public Boolean register(Supplier supplier) throws JsonSyntaxException, IOException {
		ArrayList<Supplier> users = getAllSuppliers();
		Boolean result = false;
		if (users == null) {
			supplierDao.create(supplier);
			result = true;
		} else {
			for (Supplier u : users) {
				if (u.username.equals(supplier.username)) 
					return result = false;
			}
		}
		supplierDao.create(supplier);
		return result;
	}

	public ArrayList<Supplier> getAllSuppliers() throws JsonSyntaxException, IOException {
		return supplierDao.getAllNonDeleted();
	}

	public void update(Supplier user) throws JsonSyntaxException, IOException {
		supplierDao.update(user);
	}

	public Supplier getByUsername(String params) throws JsonSyntaxException, IOException {
		return supplierDao.getByID(params);
	}

}
