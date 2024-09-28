package app.Pleiade.Service;
import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Repository.BookRepository;
import app.Pleiade.Repository.PdfStorageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PdfStorageService {

    @Autowired
    private PdfStorageRepository repository;

    @Autowired
    private BookRepository bookRepository;

    public String uploadPDF(MultipartFile file, Long bookId) throws IOException {
        // Verifica se o arquivo é um PDF
        if (!file.getContentType().equals("application/pdf")) {
            return "Invalid file type. Only PDF files are allowed.";
        }

        // Verifica se o livro existe
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }

        Book book = optionalBook.get();

        System.out.println(file.getBytes() + "ok");

        // Salva os dados do PDF no repositório
        PdfData pdfData = repository.save(PdfData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .pdfData(file.getBytes())
                .build());

        // Associa o PDF ao livro
        book.setPdfData(pdfData);
        bookRepository.save(book); // Salva as alterações no livro

        return "PDF uploaded successfully: " + file.getOriginalFilename();
    }

    public PdfData getPDF(String name) {
        Optional<PdfData> pdfData = repository.findByName(name);
        return pdfData.orElse(null);
    }

}
