package app.Pleiade.Controller;

import app.Pleiade.Entity.Book;
import app.Pleiade.Repository.BookRepository;
import app.Pleiade.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {

    @Autowired
    BookController bookController;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    BookService bookService;

    @BeforeEach
    void setup() {
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setAuthor("Author One");

        books.add(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setAuthor("Author Two");

        books.add(book2);

        Book book3 = new Book();
        book3.setId(3L);
        book3.setTitle("Book Three");
        book3.setAuthor("Author Three");

        books.add(book3);

        when(bookService.findAll()).thenReturn(books);
        when(bookRepository.findAll()).thenReturn(books);
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book3));
        when(bookService.delete(3L)).thenReturn("Book deleted successfully");
        when(bookService.save(any(Book.class))).thenReturn("Book saved successfully");
        when(bookService.update(any(Book.class), Mockito.eq(3L))).thenReturn("Book updated successfully");
        when(bookService.findById(3L)).thenReturn(book3);
    }

    @Test
    void testFindAll() {
        ResponseEntity<List<Book>> response = bookController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testFindAllWithException() {
        when(bookService.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<Book>> response = bookController.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindById() {
        ResponseEntity<Book> response = bookController.findById(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Three", response.getBody().getTitle());
    }

    @Test
    void testFindByIdWithException() {
        when(bookService.findById(3L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Book> response = bookController.findById(3L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSave() {
        Book newBook = new Book();
        newBook.setTitle("New Book");

        ResponseEntity<String> response = bookController.save(newBook);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book saved successfully", response.getBody());
    }

    @Test
    void testSaveException() {
        when(bookService.save(any(Book.class))).thenThrow(new RuntimeException("Database error"));

        Book newBook = new Book();
        newBook.setTitle("New Book");

        ResponseEntity<String> response = bookController.save(newBook);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdate() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");

        ResponseEntity<String> response = bookController.update(updatedBook, 3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book updated successfully", response.getBody());
    }

    @Test
    void testUpdateException() {
        when(bookService.update(any(Book.class), Mockito.eq(3L))).thenThrow(new RuntimeException("Database error"));

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");

        ResponseEntity<String> response = bookController.update(updatedBook, 3L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDelete() {
        ResponseEntity<String> response = bookController.delete(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted successfully", response.getBody());
    }

    @Test
    void testDeleteException() {
        when(bookService.delete(3L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = bookController.delete(3L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}

