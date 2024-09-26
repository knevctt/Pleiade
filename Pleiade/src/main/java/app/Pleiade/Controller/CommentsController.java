package app.Pleiade.Controller;

import app.Pleiade.Entity.Comments;
import app.Pleiade.Service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/save")
    public ResponseEntity<String> saveComment(@RequestBody Comments comments) {
        String response = commentsService.save(comments);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestBody Comments comments) {
        String response = commentsService.delete(comments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comments>> getAllComments() {
        List<Comments> commentsList = commentsService.getAllComments();
        return ResponseEntity.ok(commentsList);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody Comments comments) {
        String response = commentsService.UpdateComment(comments);
        return ResponseEntity.ok(response);
    }
}
