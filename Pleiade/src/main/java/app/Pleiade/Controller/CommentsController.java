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
        try {
            String response = commentsService.save(comments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar comentário: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestBody Comments comments) {
        try {
            String response = commentsService.delete(comments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao deletar comentário: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comments>> getAllComments() {
        try {
            List<Comments> commentsList = commentsService.getAllComments();
            return ResponseEntity.ok(commentsList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody Comments comments) {
        try {
            String response = commentsService.UpdateComment(comments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar comentário: " + e.getMessage());
        }
    }
}
