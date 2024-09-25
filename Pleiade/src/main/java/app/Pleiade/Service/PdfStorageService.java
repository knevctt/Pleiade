package app.Pleiade.Service;
import app.Pleiade.Entity.PdfData;
import app.Pleiade.Repository.PdfStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PdfStorageService {

    @Autowired
    private PdfStorageRepository repository;

    public String uploadPDF(MultipartFile file) throws IOException {
        // Verifica se o arquivo é um PDF
        if (!file.getContentType().equals("application/pdf")) {
            return "Invalid file type. Only PDF files are allowed.";
        }

        System.out.println(file.getBytes() + "ok");

        // Salva os dados do PDF no repositório
        PdfData pdfData = repository.save(PdfData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .pdfData(file.getBytes()) // Não precisa de compressão para PDF
                .build());

        return "PDF uploaded successfully: " + file.getOriginalFilename();
    }

    public PdfData getPDF(String name) {
        Optional<PdfData> pdfData = repository.findByName(name);
        return pdfData.orElse(null);
    }

}
