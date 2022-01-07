package service;

import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import dao.CommentDAO;
import model.Comment;

public class CommentService {
    private CommentDAO commentDAO;

    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public Boolean addComment(Comment comment) throws JsonSyntaxException, IOException {
        ArrayList<Comment> comments = commentDAO.getAll();
        Boolean result = false;

        if (comments == null) {
            commentDAO.create(comment);
            result = true;
        } else {
            for (Comment u : comments) {
                if (u.customer.equals(comment.getCustomer()) && u.restaurant.equals(comment.getRestaurant()))
                    result = false;
            }
            commentDAO.create(comment);
            result = true;

        }
        return result;
    }
}