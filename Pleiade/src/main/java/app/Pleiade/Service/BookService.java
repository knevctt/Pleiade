package app.Pleiade.Service;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.User;
import app.Pleiade.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String save(Book book){
        this.bookRepository.save(book);
        return "Book saved sucessfully";
    }
}
