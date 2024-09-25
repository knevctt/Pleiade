package app.Pleiade.Controller;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Entity.User;
import app.Pleiade.Service.BookService;
import app.Pleiade.Service.PdfStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PdfStorageService pdfStorageService;



    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Book book, PdfData file ) {
        try {
//            pdfStorageService.uploadPDF(file).getBytes();
            String mesage = this.bookService.save(book);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
