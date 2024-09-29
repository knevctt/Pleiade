package app.Pleiade.Controller;

import app.Pleiade.Entity.Comments;
import app.Pleiade.Service.CommentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentsControllerTest {

    @Autowired
    CommentsController commentsController;

    @MockBean
    CommentsService commentsService;

    @BeforeEach
    void setup() {
        List<Comments> commentsList = new ArrayList<>();

        Comments comment1 = new Comments();
        comment1.setId(1L);
        comment1.setComment("This is the first comment");
        comment1.setDateHour(LocalDateTime.now());

        commentsList.add(comment1);

        Comments comment2 = new Comments();
        comment2.setId(2L);
        comment2.setComment("This is the second comment");
        comment2.setDateHour(LocalDateTime.now());

        commentsList.add(comment2);

        when(commentsService.getAllComments()).thenReturn(commentsList);
        when(commentsService.save(any(Comments.class))).thenReturn("Comment saved successfully");
        when(commentsService.delete(any(Comments.class))).thenReturn("Comment deleted successfully");
        when(commentsService.UpdateComment(any(Comments.class))).thenReturn("Comment updated successfully");
    }

    @Test
    void testGetAllComments() {
        ResponseEntity<List<Comments>> response = commentsController.getAllComments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        // Verifica se o método getAllComments retorna 2 comentários, como definido no setup
    }

    @Test
    void testGetAllCommentsException() {
        when(commentsService.getAllComments()).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<List<Comments>> response = commentsController.getAllComments();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        // Verifica se ocorre uma exceção ao tentar buscar todos os comentários
    }

    @Test
    void testSaveComment() {
        Comments newComment = new Comments();
        newComment.setComment("This is a new comment");
        newComment.setDateHour(LocalDateTime.now());

        ResponseEntity<String> response = commentsController.saveComment(newComment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment saved successfully", response.getBody());
        // Verifica se o comentário é salvo com sucesso
    }

    @Test
    void testSaveCommentException() {
        when(commentsService.save(any(Comments.class))).thenThrow(new RuntimeException("Database error"));

        Comments newComment = new Comments();
        newComment.setComment("This is a new comment");

        ResponseEntity<String> response = commentsController.saveComment(newComment);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao salvar comentário: Database error", response.getBody());
        // Verifica se ocorre uma exceção ao tentar salvar um comentário
    }

    @Test
    void testDeleteComment() {
        Comments commentToDelete = new Comments();
        commentToDelete.setId(1L);

        ResponseEntity<String> response = commentsController.deleteComment(commentToDelete);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment deleted successfully", response.getBody());
        // Verifica se o comentário é deletado com sucesso
    }

    @Test
    void testDeleteCommentException() {
        when(commentsService.delete(any(Comments.class))).thenThrow(new RuntimeException("Database error"));

        Comments commentToDelete = new Comments();
        commentToDelete.setId(1L);

        ResponseEntity<String> response = commentsController.deleteComment(commentToDelete);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao deletar comentário: Database error", response.getBody());
        // Verifica se ocorre uma exceção ao tentar deletar um comentário
    }

    @Test
    void testUpdateComment() {
        Comments updatedComment = new Comments();
        updatedComment.setId(1L);
        updatedComment.setComment("Updated comment");

        ResponseEntity<String> response = commentsController.updateComment(updatedComment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment updated successfully", response.getBody());
        // Verifica se o comentário é atualizado com sucesso
    }

    @Test
    void testUpdateCommentException() {
        when(commentsService.UpdateComment(any(Comments.class))).thenThrow(new RuntimeException("Database error"));

        Comments updatedComment = new Comments();
        updatedComment.setId(1L);
        updatedComment.setComment("Updated comment");

        ResponseEntity<String> response = commentsController.updateComment(updatedComment);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar comentário: Database error", response.getBody());
        // Verifica se ocorre uma exceção ao tentar atualizar um comentário
    }
}
