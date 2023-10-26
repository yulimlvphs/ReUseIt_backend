package com.example.reuseit_project.Product;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "category")
    private Integer category;

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
