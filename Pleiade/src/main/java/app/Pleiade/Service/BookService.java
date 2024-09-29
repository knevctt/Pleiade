package app.Pleiade.Service;

import app.Pleiade.Entity.Book;
import app.Pleiade.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    public String update(Book book, Long id){
        book.setId(id);
        this.bookRepository.save(book);
        return "Book updated sucessfully";
    }

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll(){
        List<Book> list = this.bookRepository.findAll();
        return list;
    }

    public Book findById(long id){
        Book book = this.bookRepository.findById(id).get();
        return book;
    }

    public String save(Book book){
        this.bookRepository.save(book);
        return "Book saved sucessfully";
    }

    public String delete(long id){
        this.bookRepository.deleteById(id);
        return "Book deleted sucessfully";
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
