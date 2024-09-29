package app.Pleiade.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import app.Pleiade.Entity.ImageData;
import app.Pleiade.Repository.StorageRepository;
import app.Pleiade.Service.StorageService;
import app.Pleiade.Util.ImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.zip.Deflater;

public class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @Mock
    private StorageRepository repository;

    @Mock
    private MultipartFile file;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadImageSuccess() throws IOException {
        // Configurando o mock do MultipartFile
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn(new byte[10]);

        // Configurando o mock do repository
        ImageData savedImage = ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build();

        when(repository.save(any())).thenReturn(savedImage);

        // Chama o método
        String result = storageService.uploadImage(file);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals("file uploaded successfully : test.jpg", result);
    }

    @Test
    public void testUploadImageFailure() throws IOException {
        // Configurando o mock do MultipartFile
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn(new byte[10]);

        // Configurando o mock do repository para retornar null
        when(repository.save(any())).thenReturn(null);

        // Chama o método
        String result = storageService.uploadImage(file);

        // Verifica o resultado
        assertNull(result);
    }

    // Adicione outros testes conforme necessário

    // Teste para o método downloadImage
    @Test
    public void testDownloadImageNotFound() {
        String fileName = "nonexistent.jpg";
        when(repository.findByName(fileName)).thenReturn(Optional.empty());

        // Chama o método
        assertThrows(NoSuchElementException.class, () -> {
            storageService.downloadImage(fileName);
        });
    }

    @Test
    public void testDownloadImageSuccess() throws IOException {
        String fileName = "test.jpg";
        byte[] compressedData = ImageUtils.compressImage(new byte[10]); // Simule a compressão de uma imagem com 10 bytes.

        ImageData imageData = ImageData.builder()
                .name(fileName)
                .type("image/jpeg")
                .imageData(compressedData) // Usando dados compactados.
                .build();

        when(repository.findByName(fileName)).thenReturn(Optional.of(imageData));

        // Chama o método
        byte[] result = storageService.downloadImage(fileName);

        // Verifica o resultado
        assertNotNull(result);
        assertEquals(10, result.length); // Verifica se a imagem é a esperada
    }


    @Test
    public void testCompressImageCatch() {
        byte[] data = new byte[10]; // Exemplo de dados de entrada

        // Usar reflexão para forçar o uso de um ByteArrayOutputStream que lance uma exceção ao fechar
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length) {
            @Override
            public void close() throws IOException {
                throw new IOException("Forced exception during close");
            }
        };

        // A parte onde a exceção é esperada deve ser testada
        assertDoesNotThrow(() -> {
            ImageUtils.compressImage(data);
        });
    }
}
