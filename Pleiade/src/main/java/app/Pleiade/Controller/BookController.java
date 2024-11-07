package app.Pleiade.Controller;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.ImageData;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Entity.User;
import app.Pleiade.Enum.Genero;
import app.Pleiade.Repository.PdfStorageRepository;
import app.Pleiade.Repository.StorageRepository;
import app.Pleiade.Service.BookService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private PdfStorageRepository pdfStorageRepository;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("synopsis") String synopsis,
            @RequestParam("image") MultipartFile image,
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("generos") String generosJson) { // Adicionando o parâmetro gênero

        Map<String, String> response = new HashMap<>();

        try {
            String base64ImageData = Base64.getEncoder().encodeToString(image.getBytes());
            String base64PdfData = Base64.getEncoder().encodeToString(pdf.getBytes());

            ImageData imageData = new ImageData();
            imageData.setName(image.getOriginalFilename());
            imageData.setType(image.getContentType());
            imageData.setImageData(image.getBytes());
            imageData = storageRepository.save(imageData);

            PdfData pdfData = new PdfData();
            pdfData.setName(pdf.getOriginalFilename());
            pdfData.setType(pdf.getContentType());
            pdfData.setPdfData(pdf.getBytes());
            pdfData = pdfStorageRepository.save(pdfData);

            List<Genero> generos = new Gson().fromJson(generosJson, new TypeToken<List<Genero>>() {}.getType());

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setSynopsis(synopsis);
            book.setImageDatas(base64ImageData);
            book.setPdfDatas(base64PdfData);
            book.setGeneros(generos); // Definindo o gênero
            book.setImageData(imageData);
            book.setPdfData(pdfData);

            bookService.save(book);

            response.put("message", "Upload bem-sucedido");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("message", "Erro no upload do arquivo");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping("/addWithImageAndPdf")
    public ResponseEntity<Book> addBookWithImageAndPdf(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("pdfFile") MultipartFile pdfFile,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("synopsis") String synopsis) {
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setSynopsis(synopsis);

            Book savedBook = bookService.saveBookWithImageAndPdf(book, imageFile, pdfFile);
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Book>> findAll() {
        try {
            List<Book> lista = this.bookService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Book> findById(@PathVariable long id) {
        try {
            Book book = this.bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Book book) {
        try {
            String mesage = this.bookService.save(book);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Book book, @PathVariable long id) {
        try {
            String mesage = this.bookService.update(book, id);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mesage = this.bookService.delete(id);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<Book> findByName(@PathVariable String title) {
        try {
            Optional<Book> book = bookService.findByTitle(title);
            if (book.isPresent()) {
                return new ResponseEntity<>(book.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genero") public List<Book> getBooksByGenero(@RequestParam Genero genero) {
        return bookService.findByGenero(genero);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        try {
            List<Book> books = bookService.searchBooks(query);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
