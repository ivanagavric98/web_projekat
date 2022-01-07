package controller;

import java.io.IOException;
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
}
