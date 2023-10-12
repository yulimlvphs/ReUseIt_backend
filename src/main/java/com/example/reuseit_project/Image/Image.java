package com.example.reuseit_project.Image;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contentType;

    @Column(length = 10000)
    private String base64Data;

    @Column(name = "product_id")
    private Long productId;
}

