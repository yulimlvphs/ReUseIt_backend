package com.example.reuseit_project.Product;
import com.example.reuseit_project.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class productService {
    private final productRepository productRepository;
    private final ImageService imageService;
    @Autowired
    public productService(productRepository repository, ImageService imageService) {
        this.productRepository = repository;
        this.imageService = imageService;
    }

    @Transactional
    public Product createProduct(MultipartFile file, productDTO productDTO) {
        try { //여기부터
            Product product = productDTO.toEntity();
            Product result = productRepository.save(product);

            if (!file.isEmpty()) {
                String name = file.getOriginalFilename();
                String contentType = file.getContentType();
                byte[] data = file.getBytes();
                String base64Data = Base64.getEncoder().encodeToString(data);
                imageService.uploadImage(result.getId(), name, contentType, base64Data); //여기까지 image코드
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    // 모든 상품을 반환
    @Transactional(readOnly = true)
    public List<productFindAllDTO> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            List<productFindAllDTO> dtos = new ArrayList<>();

            for (Product product : products) {
                productFindAllDTO dto = new productFindAllDTO();
                dto.setProducts(product);

                // Assuming there's a method to find the image by product ID
                Optional<String> imageOptional = Optional.ofNullable(imageService.getImageById(product.getId()));
                String image = imageOptional.orElse("이미지가 없음");

                dto.setImage(image);;

                dtos.add(dto);
            }

            return dtos;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
        }
    }

    // 하나의 상품을 반환
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve product by ID: " + e.getMessage(), e);
        }
    }

    // 상품수정
    @Transactional
    public Product updateProduct(Long id, productDTO dto) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setProductName(dto.getProductName());
                product.setDescription(dto.getDescription());
                product.setPrice(dto.getPrice());

                // updatedAt 필드를 현재 시간으로 업데이트
                product.setUpdatedAt(LocalDateTime.now());

                // 엔티티를 저장하면 자동으로 업데이트됨
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    public List<Product> getProductByCategory(Integer categoryId) { // 카테고리 별 상품제공 매소드
        Assert.notNull(categoryId, "categoryId cannot be null");

        List<Product> products = productRepository.findAllByCategory(categoryId);

        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found for categoryId: " + categoryId);
        }

        return products;
    }

    // 해당 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }
}
