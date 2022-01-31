package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import javaxt.http.Request;
import model.RequestStatus;
import model.SupplierRequest;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SupplierRequestDAO implements IDAO<SupplierRequest, String> {

    private String path;
    private ArrayList<SupplierRequest> supplierRequests;

    public SupplierRequestDAO(String path) {
        super();
        this.path = path;

        try {
            getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SupplierRequest> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<SupplierRequest>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/supplierRequest.json"));
        this.supplierRequests = gson.fromJson(br, token);
        return supplierRequests;
    }

    @Override
    public SupplierRequest getByID(String id) throws JsonSyntaxException, IOException {
        return null;
    }

    public SupplierRequest getByOrderAndSupplier(String orderId, String supplierUsername)
            throws JsonSyntaxException, IOException {
        SupplierRequest wantedRequest = null;
        ArrayList<SupplierRequest> requests = (ArrayList<SupplierRequest>) getAll();
        if (requests != null) {
        	System.out.println("aa");
            for (SupplierRequest request : requests) {
                if (request.getOrderId().equals(orderId) && request.getSupplier().equals(supplierUsername)) {
                    wantedRequest = request;
                    break;
                }
            }
        }
        System.out.println(wantedRequest);
        return wantedRequest;
    }

    @Override
    public void create(SupplierRequest entity) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> supplierRequests = getAll();
        if (supplierRequests == null) {
        	supplierRequests = new ArrayList<>();
        }
        supplierRequests.add(entity);
        saveAll(supplierRequests);
    }

    @Override
    public void update(SupplierRequest entity) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> articles = getAll();
        for (SupplierRequest res : articles) {
            if (res.getOrderId().equals(entity.getOrderId()) && res.getSupplier().equals(entity.getSupplier())) {
                articles.set(articles.indexOf(res), entity);
                break;
            }
        }
        saveAll(articles);
    }

    @Override
    public void delete(SupplierRequest entity) throws JsonSyntaxException, IOException {
        return;
    }

    @Override
    public void save(SupplierRequest entity) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> supplierRequests = getAll();
        supplierRequests.add(entity);
        saveAll(supplierRequests);
    }

    @Override
    public void saveAll(ArrayList<SupplierRequest> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<SupplierRequest>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    @Override
    public ArrayList<SupplierRequest> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Boolean processSupplierRequetst(String requestId, String supplierUsername, String par)
            throws JsonSyntaxException, IOException {
        SupplierRequest supplierRequest = getByOrderAndSupplier(requestId, supplierUsername);
        if (par.equals("cancel")) {
            supplierRequest.setStatus(RequestStatus.DENIED);
            update(supplierRequest);
            return false;
        } else {
            supplierRequest.setStatus(RequestStatus.ACCEPTED);
            update(supplierRequest);
            return true;
        }
    }
}