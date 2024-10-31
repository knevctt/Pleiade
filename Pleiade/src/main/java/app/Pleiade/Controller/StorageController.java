package app.Pleiade.Controller;

import app.Pleiade.Entity.ImageData;
import app.Pleiade.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/image")
public class StorageController {
    @Autowired
    private StorageService service;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ImageData>> getAllImages() {
        List<ImageData> images = service.findAllImages();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<ImageData> getImageByName(@PathVariable String name) {
        Optional<ImageData> imageData = service.findByName(name);

        if (imageData.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(imageData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
