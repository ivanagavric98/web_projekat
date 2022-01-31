package service;

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import dao.SupplierRequestDAO;
import model.RequestStatus;
import model.SupplierRequest;

public class SupplierRequestService {
    private SupplierRequestDAO supplierRequestDAO;

    public SupplierRequestService(SupplierRequestDAO supplierRequestDAO) {
        this.supplierRequestDAO = supplierRequestDAO;
    }

    public Boolean sendRequest(String supplier, String orderId) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> requests = getAllSupplierRequests();
        
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.orderId = orderId;
        supplierRequest.status = RequestStatus.PROCESSING;
        supplierRequest.supplier = supplier;

        Boolean result = false;
        if (requests == null) {
            supplierRequestDAO.create(supplierRequest);
            result = true;
        } else {
        	for (SupplierRequest r : requests) {
                if (r.getSupplier().equals(supplierRequest.supplier) && r.getOrderId().equals(supplierRequest.orderId)) {
                    result = false;
                } else {
                    supplierRequestDAO.create(r);
                    result = true;
                }
            }
        }
        return result;
    }

    public ArrayList<SupplierRequest> getAllSupplierRequests() throws JsonSyntaxException, IOException {
    	ArrayList<SupplierRequest> requests = supplierRequestDAO.getAll();
    	ArrayList<SupplierRequest> result = new ArrayList<SupplierRequest>();
    	
    	for(SupplierRequest r : requests) {
    		if(r.getStatus().equals(RequestStatus.PROCESSING)) 
    			result.add(r);
    	}
        return result;
    }

    public Boolean processSupplierRequetst(String params, String params3, String params2)
            throws JsonSyntaxException, IOException {
        return supplierRequestDAO.processSupplierRequetst(params, params3, params2);
    }

}
