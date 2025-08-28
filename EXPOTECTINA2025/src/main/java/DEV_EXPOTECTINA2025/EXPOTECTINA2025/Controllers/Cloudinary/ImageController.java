package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Cloudinary;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Cloudinary.CloudinaryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final CloudinaryServices Cservice;

    public ImageController(CloudinaryServices cservice) {
        Cservice = cservice;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage (@RequestParam("image") MultipartFile file) throws IOException {
        try {
            String imageURL = Cservice.uploadImage(file);
            return  ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageURL
            ));

        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen" + e.getMessage()
            );

        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImageToFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder
    ){
        try{
            String imageUrl = Cservice.uploadImage(file, folder);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageUrl
            ));

        }catch (IOException e){
            return  ResponseEntity.internalServerError().body("Error al subir la imagen");

        }

    }
}
