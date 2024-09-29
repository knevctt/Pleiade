package app.Pleiade.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import app.Pleiade.Entity.Book;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Repository.BookRepository;
import app.Pleiade.Repository.PdfStorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PdfStorageServiceTest {

    @InjectMocks
    private PdfStorageService pdfStorageService;

    @Mock
    private PdfStorageRepository pdfStorageRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MultipartFile file;

    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }

    @Test
    public void testUploadPDFSuccess() throws IOException {
        when(file.getOriginalFilename()).thenReturn("test.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getBytes()).thenReturn("PDF data".getBytes());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(pdfStorageRepository.save(any(PdfData.class))).thenReturn(new PdfData());

        String result = pdfStorageService.uploadPDF(file, 1L);
        assertEquals("PDF uploaded successfully: test.pdf", result);
        verify(bookRepository).save(book); // Verifica se o livro foi salvo após a associação
    }

    @Test
    public void testGetPDFSuccess() {
        PdfData pdfData = new PdfData();
        pdfData.setName("test.pdf");
        when(pdfStorageRepository.findByName("test.pdf")).thenReturn(Optional.of(pdfData));

        PdfData result = pdfStorageService.getPDF("test.pdf");
        assertNotNull(result);
        assertEquals("test.pdf", result.getName());
    }

    @Test
    public void testGetPDFNotFound() {
        when(pdfStorageRepository.findByName("nonexistent.pdf")).thenReturn(Optional.empty());

        PdfData result = pdfStorageService.getPDF("nonexistent.pdf");
        assertNull(result);
    }
}
