package service;

import java.io.IOException;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayList;
import com.google.gson.JsonSyntaxException;
import dao.CommentDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import model.Comment;
import model.CommentStatus;
import model.Customer;
import model.Order;
import model.OrderStatus;

public class CommentService {
    private CommentDAO commentDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    
    public CommentService(CommentDAO commentDAO, OrderDAO orderDAO, CustomerDAO customerDAO) {
        this.commentDAO = commentDAO;
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
    }

    public Boolean addComment(String username, String restaurant, Comment comment) throws JsonSyntaxException, IOException {        
    	Customer customer = customerDAO.getByID(username);
        ArrayList<Order> customersOrders = customer.getOrders();
        Boolean result = false;
       
    	for(Order order: customersOrders) {
    		if(order.getRestaurant().equals(restaurant) && customer.getUsername().equals(order.getCustomer())) {
	        	if(order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
	        		commentDAO.create(comment);
	                result = true;
	                break;	
	        	}
    		}
    	}
       
       return result;
    }

    public Comment approveComment(Comment comment) throws JsonSyntaxException, IOException {
    	ArrayList<Comment> comments = commentDAO.getAll();
        
        for(Comment c : comments) {
        	if(c.getId().equals(comment.getId())) {
                 c.setStatus(CommentStatus.Approved);
                 c.setText(comment.getText());
                 c.setCustomer(comment.getCustomer());
                 c.setGrade(comment.getGrade());
                 break;
        	}
        }
        commentDAO.saveAll(comments);
        return comment;
    }

    public Comment rejectComment(Comment comment) throws JsonSyntaxException, IOException {
    	ArrayList<Comment> comments = commentDAO.getAll();
        
        for(Comment c : comments) {
        	if(c.getId().equals(comment.getId())) {
                 c.setStatus(CommentStatus.Rejected);
                 c.setText(comment.getText());
                 c.setCustomer(comment.getCustomer());
                 c.setGrade(comment.getGrade());
                 break;
        	}
        }
        commentDAO.saveAll(comments);
        return comment;
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