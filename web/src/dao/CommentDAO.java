package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import javaxt.http.Request;
import model.RequestStatus;
import model.SupplierRequest;

import java.lang.reflect.Type;

import model.Comment;

public class CommentDAO implements IDAO<Comment, String> {

    private String path;
    private ArrayList<Comment> comments;

    public CommentDAO(String path) {
        super();
        this.path = path;

        try {
            getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Comment> getAll() throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        Type token = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        BufferedReader br = new BufferedReader(new FileReader("data/comments.json"));
        this.comments = gson.fromJson(br, token);
        return comments;
    }

    @Override
    public Comment getByID(String name) throws JsonSyntaxException, IOException {
        // Comment wantedComment = null;
        // ArrayList<Comment> comments = (ArrayList<Comment>) getAll();
        // if (articles.size() != 0) {
        // for (Comment comment : comments) {
        // if (comment.getCustomer().equals(name)) {
        // wantedArticle = article;
        // break;
        // }
        // }
        // }
        // return wantedArticle;
        return null;
    }

    @Override
    public void create(Comment entity) throws JsonSyntaxException, IOException {
        ArrayList<Comment> comments = getAll();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(entity);
        saveAll(comments);
    }

    @Override
    public void update(Comment entity) throws JsonSyntaxException, IOException {
        ArrayList<Comment> comments = getAll();
        for (Comment res : comments) {
            if (res.getCustomer().equals(entity.getCustomer()) && res.getRestaurant().equals(entity.getRestaurant())) {
                comments.set(comments.indexOf(res), entity);
                break;
            }
        }
        saveAll(comments);
    }

    @Override
    public void delete(Comment entity) throws JsonSyntaxException, IOException {
        return;
    }

    @Override
    public void save(Comment entity) throws JsonSyntaxException, IOException {
        ArrayList<Comment> comments = getAll();
        comments.add(entity);
        saveAll(comments);
    }

    @Override
    public void saveAll(ArrayList<Comment> entities) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        String allEntities = new Gson().toJson(entities, new TypeToken<List<Comment>>() {
        }.getType());
        writer.println(allEntities);
        writer.close();

    }

    @Override
    public ArrayList<Comment> getAllNonDeleted() throws JsonSyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Comment getByRestaurantAndCustomerName(String restaurant, String customer) throws JsonSyntaxException, IOException {
        ArrayList<Comment> comments = getAll();
        Comment result = null;
        for (Comment c : comments) {
            if (c.getCustomer().equals(customer) && c.getRestaurant().equals(restaurant)) {
                result = c;
            }
        }
        return result;
    }
}
