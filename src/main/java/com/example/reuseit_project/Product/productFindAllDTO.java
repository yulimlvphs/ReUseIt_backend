package com.example.reuseit_project.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class productFindAllDTO { //상품 전체 리스트를 불러올 때의 DTO
    private Product products;
    private String Image;
    private Integer category;

    public productFindAllDTO() {

    }
}
