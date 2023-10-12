package com.example.reuseit_project.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class productFindAllDTO {
    private Product products;
    private String Image;

    public productFindAllDTO() {

    }
}
