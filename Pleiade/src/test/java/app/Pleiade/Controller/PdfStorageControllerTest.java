package app.Pleiade.Controller;

import app.Pleiade.Entity.PdfData;
import app.Pleiade.Service.PdfStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PdfStorageControllerTest {

    @Autowired
    private PdfStorageController pdfStorageController;

    @MockBean
    private PdfStorageService pdfStorageService;

    @Test
    void testUploadPDF_Success() throws IOException {
        // Simula o arquivo e o ID do livro
        MultipartFile file = new MockMultipartFile("file", "test.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF Content".getBytes());
        Long bookId = 1L;

        // Define o comportamento do mock service
        when(pdfStorageService.uploadPDF(file, bookId)).thenReturn("PDF uploaded successfully");

        // Chama o método do controller
        ResponseEntity<String> response = pdfStorageController.uploadPDF(file, bookId);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("PDF uploaded successfully", response.getBody());
    }

    @Test
    void testUploadPDF_BookIdNull() throws IOException {
        // Simular um arquivo PDF
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());

        // Teste quando bookId é null
        ResponseEntity<String> response = pdfStorageController.uploadPDF(file, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid book ID", response.getBody());
    }

    @Test
    void testUploadPDF_Failure() throws IOException {
        // Simula o arquivo e o ID do livro
        MultipartFile file = new MockMultipartFile("file", "test.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF Content".getBytes());
        Long bookId = 1L;

        // Define o comportamento do mock service
        when(pdfStorageService.uploadPDF(file, bookId)).thenReturn(null);

        // Chama o método do controller
        ResponseEntity<String> response = pdfStorageController.uploadPDF(file, bookId);

        // Verifica o resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload PDF", response.getBody());
    }

    @Test
    void testUploadPDF_IOException() throws IOException {
        // Simula o arquivo e o ID do livro
        MultipartFile file = new MockMultipartFile("file", "test.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF Content".getBytes());
        Long bookId = 1L;

        // Define o comportamento do mock service para lançar IOException
        when(pdfStorageService.uploadPDF(file, bookId)).thenThrow(new IOException("File error"));

        // Chama o método do controller
        ResponseEntity<String> response = pdfStorageController.uploadPDF(file, bookId);

        // Verifica o resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error uploading PDF: File error", response.getBody());
    }

    @Test
    void testDownloadPDF_Success() {
        // Simula o PDF a ser retornado
        String pdfName = "test.pdf";
        byte[] pdfContent = "PDF Content".getBytes();
        PdfData pdfData = new PdfData(pdfName, pdfContent);

        // Define o comportamento do mock service
        when(pdfStorageService.getPDF(pdfName)).thenReturn(pdfData);

        // Chama o método do controller
        ResponseEntity<byte[]> response = pdfStorageController.downloadPDF(pdfName);

        // Verifica o resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals("form-data; name=\"attachment\"; filename=\"test.pdf\"", response.getHeaders().getContentDisposition().toString());
        assertArrayEquals(pdfContent, response.getBody());
    }

    @Test
    void testDownloadPDF_NotFound() {
        // Simula a situação onde o PDF não é encontrado
        String pdfName = "nonexistent.pdf";

        // Define o comportamento do mock service
        when(pdfStorageService.getPDF(pdfName)).thenReturn(null);

        // Chama o método do controller
        ResponseEntity<byte[]> response = pdfStorageController.downloadPDF(pdfName);

        // Verifica o resultado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
