package app.Pleiade.Service;

import app.Pleiade.Entity.Book;
import app.Pleiade.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    void setup() {
        // Configuração inicial para os testes
    }

    @Test
    void testSave() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        String response = bookService.save(book);
        assertEquals("Book saved sucessfully", response);
        // Verifica se o método save retorna a mensagem correta
    }

    @Test
    void testUpdate() {
        Book book = new Book();
        book.setTitle("Updated Book");
        book.setAuthor("Updated Author");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        String response = bookService.update(book, 1L);
        assertEquals("Book updated sucessfully", response);
        // Verifica se o método update retorna a mensagem correta
    }

    @Test
    void testFindAll() {
        List<Book> booksList = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("First Book");
        book1.setAuthor("Author 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Second Book");
        book2.setAuthor("Author 2");

        booksList.add(book1);
        booksList.add(book2);

        when(bookRepository.findAll()).thenReturn(booksList);

        List<Book> result = bookService.findAll();
        assertEquals(2, result.size());
        // Verifica se o método findAll retorna a lista correta
    }

    @Test
    void testFindById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.findById(1L);
        assertEquals("Test Book", result.getTitle());
        // Verifica se o método findById retorna o livro correto
    }

    @Test
    void testDelete() {
        long bookId = 1L;

        String response = bookService.delete(bookId);
        assertEquals("Book deleted sucessfully", response);
        // Verifica se o método delete retorna a mensagem correta
    }
}
