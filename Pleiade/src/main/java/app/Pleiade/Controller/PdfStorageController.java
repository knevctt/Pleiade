package app.Pleiade.Controller;

import app.Pleiade.Entity.PdfData;
import app.Pleiade.Service.PdfStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfStorageController {

    @Autowired
    private PdfStorageService pdfStorageService;

    // Endpoint para upload de PDF
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDF(@RequestParam("file") MultipartFile file) {
        try {
            String response = pdfStorageService.uploadPDF(file);
            if (response != null) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to upload PDF");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading PDF: " + e.getMessage());
        }
    }
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPDF(@RequestParam("name") String name) {
        PdfData pdfData = pdfStorageService.getPDF(name);
        if (pdfData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", pdfData.getName());

            return new ResponseEntity<>(pdfData.getPdfData(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
