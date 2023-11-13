package com.example.reuseit_project.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class productDTO {
    private Long id;
    private String productName;
    private String description;
    private Integer price;
    private Integer category;
    private String location;
    private Boolean isUsed;
    private Boolean isExchange;
    private Integer quantity;

    public Product toEntity() {
        Product entity = new Product();
        entity.setProductName(productName);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setCategory(category);
        entity.setLocation(location);
        entity.setIsExchange(isExchange);
        entity.setIsUsed(isUsed);
        entity.setQuantity(quantity);
        return entity;
    }
}
