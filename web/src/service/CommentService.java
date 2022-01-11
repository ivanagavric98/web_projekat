package service;

import java.io.IOException;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import dao.CommentDAO;
import model.Comment;
import model.CommentStatus;

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

    public Comment approveComment(Comment comment) throws JsonSyntaxException, IOException {
        Comment commentToUpdate = commentDAO.getByRestaurantAndCustomerName(comment.getRestaurant(), comment.getCustomer());
        commentToUpdate.setStatus(CommentStatus.Approved);
        commentDAO.update(commentToUpdate);
        return commentToUpdate;
    }

    public Comment rejectComment(Comment comment) throws JsonSyntaxException, IOException {
        Comment commentToUpdate = commentDAO.getByRestaurantAndCustomerName(comment.restaurant, comment.customer);
        commentToUpdate.setStatus(CommentStatus.Rejected);
        commentDAO.update(commentToUpdate);
        return commentToUpdate;
    }

    public ArrayList<Comment> getCommentsWithStatusApproved() throws JsonSyntaxException, IOException {
        ArrayList<Comment> allComments = commentDAO.getAll();
        ArrayList<Comment> resultList = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.status.equals(CommentStatus.Approved)) {
                resultList.add(comment);
            }
        }
        return resultList;
    }

    public ArrayList<Comment> getCommentsWithStatusRejected() throws JsonSyntaxException, IOException {
        ArrayList<Comment> allComments = commentDAO.getAll();
        ArrayList<Comment> resultList = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.status.equals(CommentStatus.Rejected)) {
                resultList.add(comment);
            }
         }
        return resultList;
    }

    public ArrayList<Comment> getCommentsWithStatusProcessing() throws JsonSyntaxException, IOException {
        ArrayList<Comment> allComments = commentDAO.getAll();
        ArrayList<Comment> resultList = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.status.equals(CommentStatus.Processing)) {
                resultList.add(comment);
            }
        }
        
        return resultList;
    }

	public ArrayList<Comment> getAllCommentsByRestaurant(String restaurantName) throws JsonSyntaxException, IOException {
		ArrayList<Comment> allComments = commentDAO.getAll();
        ArrayList<Comment> resultList = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.getRestaurant().equals(restaurantName)) {
                resultList.add(comment);
            }
        }
        return resultList;

	}
}