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
    private ArrayList<SupplierRequest> articles;

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
        BufferedReader br = new BufferedReader(new FileReader("web/data/supplierRequest.json"));
        this.articles = gson.fromJson(br, token);
        return articles;
    }

    @Override
    public SupplierRequest getByID(String id) throws JsonSyntaxException, IOException {
        // SupplierRequest wantedArticle = null;
        // ArrayList<SupplierRequest> articles = (ArrayList<SupplierRequest>) getAll();
        // if (articles.size() != 0) {
        // for (SupplierRequest article : articles) {
        // if (article.getOrderId().equals(g)) {
        // wantedArticle = article;
        // break;
        // }
        // }
        // }
        // return wantedArticle;
        return null;
    }

    public SupplierRequest getByOrderAndSupplier(String orderId, String supplierUsername)
            throws JsonSyntaxException, IOException {
        SupplierRequest wantedRequest = null;
        ArrayList<SupplierRequest> requests = (ArrayList<SupplierRequest>) getAll();
        if (requests.size() != 0) {
            for (SupplierRequest request : requests) {
                if (request.getOrderId().equals(orderId) && request.getSupplier().equals(supplierUsername)) {
                    wantedRequest = request;
                    break;
                }
            }
        }
        return wantedRequest;
    }

    @Override
    public void create(SupplierRequest entity) throws JsonSyntaxException, IOException {
        ArrayList<SupplierRequest> articles = getAll();
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(entity);
        saveAll(articles);
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
        ArrayList<SupplierRequest> articles = getAll();
        articles.add(entity);
        saveAll(articles);
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