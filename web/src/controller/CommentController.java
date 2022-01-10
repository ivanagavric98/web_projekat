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

    public Boolean addComment(Comment comment) throws JsonSyntaxException, IOException {
        try {
            return commentService.addComment(comment);
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
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
}
