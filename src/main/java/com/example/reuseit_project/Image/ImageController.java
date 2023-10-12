package com.example.reuseit_project.Image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

@Controller
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String listImages(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "image-list";
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<Image> image = imageService.getImageById(id);
        if (image.isPresent()) {
            String base64Data = image.get().getBase64Data();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                    .body(decodedBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/




   /* @PostMapping("/upload")
    public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] data = file.getBytes();
            String base64Data = Base64.getEncoder().encodeToString(data);
            imageService.uploadImage(name, contentType, base64Data);
        }
        return "success";
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
