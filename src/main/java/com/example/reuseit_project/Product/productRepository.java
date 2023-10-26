package com.example.reuseit_project.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface productRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Integer category);
}
