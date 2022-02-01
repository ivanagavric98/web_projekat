package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import model.Comment;
import service.CommentService;

public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public Boolean addComment(String username, String restaurant, Comment comment) throws JsonSyntaxException, IOException {
        return commentService.addComment(username, restaurant, comment);    
    }

    public Comment approveComment(Comment comment) throws JsonSyntaxException, IOException {
        return commentService.approveComment(comment);
    }

    public Comment rejectComment(Comment comment) throws JsonSyntaxException, IOException {
        return commentService.rejectComment(comment);
    }

    public ArrayList<Comment> getCommentsWithStatusApproved() throws JsonSyntaxException, IOException {
        return commentService.getCommentsWithStatusApproved();
    }

    public ArrayList<Comment> getCommentsWithStatusRejected() throws JsonSyntaxException, IOException {
        return commentService.getCommentsWithStatusRejected();
    }

    public ArrayList<Comment> getCommentsWithStatusProcessing() throws JsonSyntaxException, IOException {
        return commentService.getCommentsWithStatusProcessing();
    }

	public ArrayList<Comment> getAllCommentsByRestaurant(String restaurantName) throws JsonSyntaxException, IOException {
		return commentService.getAllCommentsByRestaurant(restaurantName);
	}
}
