package app.Pleiade.Controller;

import app.Pleiade.Service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageControllerTest {

    @InjectMocks
    private StorageController storageController;

    @Mock
    private StorageService storageService;

    private MultipartFile file;

    @BeforeEach
    void setup() {
        file = new MockMultipartFile("image", "test.png", "image/png", "test image content".getBytes());
    }

    @Test
    void testUploadImage() throws IOException {
        when(storageService.uploadImage(file)).thenReturn("Image uploaded successfully");

        ResponseEntity<?> response = storageController.uploadImage(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Image uploaded successfully", response.getBody());
    }

    @Test
    void testDownloadImage() {
        byte[] imageData = "test image content".getBytes();
        when(storageService.downloadImage("test.png")).thenReturn(imageData);

        ResponseEntity<?> response = storageController.downloadImage("test.png");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.valueOf("image/png"), response.getHeaders().getContentType());
        assertEquals(imageData, response.getBody());
    }
}