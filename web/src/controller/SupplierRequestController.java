package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import model.SupplierRequest;
import service.SupplierRequestService;

public class SupplierRequestController {
    private SupplierRequestService supplierRequestService;

    public SupplierRequestController(SupplierRequestService supplierRequestService) {
        this.supplierRequestService = supplierRequestService;
    }

    public Boolean addRequest(String params, String params2) throws JsonSyntaxException, IOException {
        return supplierRequestService.sendRequest(params, params2);
    }

    public Boolean processSupplierRequetst(String params, String params3, String params2)
            throws JsonSyntaxException, IOException {
        return supplierRequestService.processSupplierRequetst(params, params3, params2);
    }

	public ArrayList<SupplierRequest> getAllRequestsForDelivering() throws JsonSyntaxException, IOException {
		return supplierRequestService.getAllSupplierRequests();
	}
}
