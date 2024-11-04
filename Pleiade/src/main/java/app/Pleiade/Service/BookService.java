package app.Pleiade.Service;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.ImageData;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Enum.Genero;
import app.Pleiade.Repository.BookRepository;
import app.Pleiade.Repository.PdfStorageRepository;
import app.Pleiade.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private PdfStorageRepository pdfStorageRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private BookRepository bookRepository;

    public String update(Book book, Long id){
        book.setId(id);
        this.bookRepository.save(book);
        return "Book updated sucessfully";
    }

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

    public Book saveBookWithImageAndPdf(Book book, MultipartFile imageFile, MultipartFile pdfFile) throws IOException {
        try {
            // Save image data
            ImageData imageData = new ImageData();
            imageData.setName(imageFile.getOriginalFilename());
            imageData.setType(imageFile.getContentType());
            imageData.setImageData(imageFile.getBytes());

            imageData = storageRepository.save(imageData);

            // Save PDF data
            PdfData pdfData = new PdfData();
            pdfData.setName(pdfFile.getOriginalFilename());
            pdfData.setType(pdfFile.getContentType());
            pdfData.setPdfData(pdfFile.getBytes());

            pdfData = pdfStorageRepository.save(pdfData);

            // Associate image and PDF with book
            book.setImageData(imageData);
            book.setPdfData(pdfData);

            return bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar livro com imagem e PDF", e);
        }
    }

    public List<Book> findByGenre(Genero genero) { return bookRepository.findByGenero(genero); }

}
