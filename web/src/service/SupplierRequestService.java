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

    public Boolean register(String supplier, String orderId) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> users = getAllSupplierRequests();
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.orderId = orderId;
        supplierRequest.status = RequestStatus.PROCESSING;
        supplierRequest.supplier = supplier;

        Boolean result = false;
        if (users == null) {
            supplierRequestDAO.create(supplierRequest);
            result = true;
        } else {
            for (SupplierRequest u : users) {
                if (u.supplier.equals(supplierRequest.supplier) && u.orderId.equals(supplierRequest.orderId)) {
                    result = false;
                } else {
                    supplierRequestDAO.create(u);
                    result = true;
                }
            }
        }
        return result;
    }

    public ArrayList<SupplierRequest> getAllSupplierRequests() throws JsonSyntaxException, IOException {
        return supplierRequestDAO.getAll();
    }

    public Boolean processSupplierRequetst(String params, String params3, String params2)
            throws JsonSyntaxException, IOException {
        return supplierRequestDAO.processSupplierRequetst(params, params3, params2);
    }

}
