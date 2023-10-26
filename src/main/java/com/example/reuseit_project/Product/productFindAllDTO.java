package com.example.reuseit_project.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class productFindAllDTO {
    private Product products;
    private String Image;
    private Integer category;

    public productFindAllDTO() {

    }
}
