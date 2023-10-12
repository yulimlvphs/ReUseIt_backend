package com.example.reuseit_project.Image;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImage(Long productId, String name, String contentType, String base64Data) {
        Image image = new Image();
        image.setName(name);
        image.setContentType(contentType);
        image.setBase64Data(base64Data);
        image.setProductId(productId);
        imageRepository.save(image);
    }

    public String getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        String base64Data = image.map(Image::getBase64Data).orElse("이미지가 없음");
        return base64Data;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new ImageNotFoundException("Image not found with ID: " + id);
        }
        imageRepository.deleteById(id);
    }
}

