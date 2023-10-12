package com.example.reuseit_project.Product;

import com.example.reuseit_project.Image.Image;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName; //상품명

    @Column(name = "description")
    private String description; //상품설명

    @Column(name = "price")
    private Integer price; //상품가격

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_featured")
    private boolean isFeatured; // isFeatured가 true로 설정된 상품은 웹 사이트에서 주목받는 상품으로 강조

    public Product() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }


    public void updateProduct(String productName, String description, Integer price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }
}
