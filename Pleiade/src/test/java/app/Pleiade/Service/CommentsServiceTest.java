package app.Pleiade.Service;

import app.Pleiade.Entity.Comments;
import app.Pleiade.Repository.CommentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentsServiceTest {

    @Autowired
    CommentsService commentsService;

    @MockBean
    CommentsRepository commentsRepository;

    @BeforeEach
    void setup() {
        // Configuração inicial para os testes
    }

    @Test
    void testSave() {
        Comments comment = new Comments();
        comment.setComment("This is a test comment");
        comment.setDateHour(LocalDateTime.now());

        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        String response = commentsService.save(comment);
        assertEquals("comments saved sucessfully", response);
        // Verifica se o método save retorna a mensagem correta
    }

    @Test
    void testDelete() {
        Comments comment = new Comments();
        comment.setId(1L);
        comment.setComment("This is a test comment");

        String response = commentsService.delete(comment);
        assertEquals("comments deleted sucessfully", response);
        // Verifica se o método delete retorna a mensagem correta
    }

    @Test
    void testGetAllComments() {
        List<Comments> commentsList = new ArrayList<>();

        Comments comment1 = new Comments();
        comment1.setId(1L);
        comment1.setComment("First comment");
        comment1.setDateHour(LocalDateTime.now());

        Comments comment2 = new Comments();
        comment2.setId(2L);
        comment2.setComment("Second comment");
        comment2.setDateHour(LocalDateTime.now());

        commentsList.add(comment1);
        commentsList.add(comment2);

        when(commentsRepository.findAll()).thenReturn(commentsList);

        List<Comments> result = commentsService.getAllComments();
        assertEquals(2, result.size());
        // Verifica se o método getAllComments retorna a lista correta
    }

    @Test
    void testUpdateComment() {
        Comments comment = new Comments();
        comment.setId(1L);
        comment.setComment("Updated comment");
        comment.setDateHour(LocalDateTime.now());

        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        String response = commentsService.UpdateComment(comment);
        assertEquals("comments updated sucessfully", response);
        // Verifica se o método UpdateComment retorna a mensagem correta
    }
}
